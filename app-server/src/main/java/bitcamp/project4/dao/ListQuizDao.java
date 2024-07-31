package bitcamp.project4.dao;

import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ListQuizDao implements QuizDao {
  private static final String DEFAULT_DATANAME = "quizs";
  private int seqNo;
  private List<Quiz> quizList = new ArrayList<>();
  private String path;
  private String dataName;

  public ListQuizDao(String path) {
    this(path, DEFAULT_DATANAME);
  }

  public ListQuizDao(String path, String dataName) {
    this.path = path;
    this.dataName = dataName;
    try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
      XSSFSheet sheet = workbook.getSheet(dataName);
      for (int i = 1; i <= sheet.getLastRowNum() ; i++){
        Row row = sheet.getRow(i);
        try {
          Quiz quiz = new Quiz();
          quiz.setNo(getIntValue(row.getCell(0)));
          quiz.setNumber(getIntValue(row.getCell(1)));
          quiz.setAnswer(getStringValue(row.getCell(2)));
          quizList.add(quiz);
        } catch (Exception e) {
          System.out.printf("%d 번의 데이터 형식이 맞지 않습니다.\n", i);
        }
      }
      if (!quizList.isEmpty()) {
        seqNo = quizList.get(quizList.size() - 1).getNo();
      }
    } catch (Exception e) {
      System.out.println("퀴즈 데이터 로딩 중 오류 발생!");
      e.printStackTrace();
    }
  }

  private int getIntValue(Cell cell) {
    if (cell.getCellType() == CellType.NUMERIC) {
      return (int) cell.getNumericCellValue();
    } else {
      return Integer.parseInt(cell.getStringCellValue());
    }
  }

  private String getStringValue(Cell cell) {
    if (cell.getCellType() == CellType.STRING) {
      return cell.getStringCellValue();
    } else {
      return String.valueOf((int) cell.getNumericCellValue());
    }
  }

  public void save() throws Exception {
    try (FileInputStream in = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(in)) {
      int sheetIndex = workbook.getSheetIndex(dataName);
      if (sheetIndex != -1) {
        workbook.removeSheetAt(sheetIndex);
      }
      XSSFSheet sheet = workbook.createSheet(dataName);

      // 셀 이름 출력
      String[] cellHeaders = {"no", "number", "answer", "topic", "hint"};
      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < cellHeaders.length; i++) {
        headerRow.createCell(i).setCellValue(i);
      }

      // 데이터 저장
      int rowNo = 1;
      for (Quiz quiz : quizList) {
        Row dataRow = sheet.createRow(rowNo++);
        dataRow.createCell(0).setCellValue(quiz.getNo());
        dataRow.createCell(1).setCellValue(quiz.getNumber());
        dataRow.createCell(2).setCellValue(quiz.getAnswer());
      }

      // 엑셀 파일로 데이터를 출력하기 전에
      // workbook을 위해 연결한 입력 스트림을 먼저 종료한다.
      in.close();

      try (FileOutputStream out = new FileOutputStream(path)) {
        workbook.write(out);
      }
    }
  }

  @Override
  public boolean insert(Quiz quiz) throws Exception {
    quiz.setNo(++seqNo);
    quizList.add(quiz);
    return true;
  }

  @Override
  public List<Quiz> list() throws Exception {
    return new ArrayList<>(quizList);
  }

  @Override
  public Quiz findBy(int no) throws Exception {
    for (Quiz quiz : quizList) {
      if (quiz.getNo() == no) {
        return quiz;
      }
    }
    return null;
  }

  @Override
  public boolean update(Quiz quiz) throws Exception {
    int index = quizList.indexOf(quiz);
    if (index == -1) {
      return false;
    }
    quizList.set(index, quiz);
    return true;
  }

  @Override
  public boolean delete(int no) throws Exception {
    return quizList.removeIf(quiz -> quiz.getNo() == no);
  }
}
