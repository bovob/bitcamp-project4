package bitcamp.project4.command.quiz;

import bitcamp.project4.command.Command;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import bitcamp.project4.util.Prompt;

public class QuizViewCommand implements Command {

  private QuizDao quizDao;

  public QuizViewCommand(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);
    int quizNo = Prompt.inputInt("퀴즈번호?");

    try {
      Quiz quiz = quizDao.findBy(quizNo);
      if (quiz == null) {
        System.out.println("없는 번호입니다.");
        return;
      }

      System.out.printf("글자수: %s\n", quiz.getNumber());
      System.out.printf("정답: %s\n", quiz.getAnswer());

    } catch (Exception e) {
      System.out.println("조회 중 오류 발생!");
    }
  }
}
