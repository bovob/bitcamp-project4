package bitcamp.project4.command.quiz;


import bitcamp.project4.command.Command;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;

public class QuizListCommand implements Command {

  private QuizDao quizDao;

  public QuizListCommand(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);

    try {
      System.out.println("번호 글자수 정답");

      for (Quiz quiz : quizDao.list()) {
        System.out.printf("%2d %4d %8s\n", quiz.getNo(), quiz.getNumber(), quiz.getAnswer());
      }

    } catch (Exception e) {
      System.out.println("목록 조회 중 오류 발생!");
    }
  }
}
