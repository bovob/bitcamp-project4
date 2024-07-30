package bitcamp.project4.command.quiz;

import bitcamp.project4.command.Command;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import bitcamp.project4.util.Prompt;

public class QuizDeleteCommand implements Command {

  private QuizDao quizDao;

  public QuizDeleteCommand(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);
    int quizNo = Prompt.inputInt("정답번호?");

    try {
      Quiz deletedQuiz = quizDao.findBy(quizNo);
      if (deletedQuiz == null) {
        System.out.println("없는 정답입니다.");
        return;
      }

      quizDao.delete(quizNo);
      System.out.printf("'%s'을 삭제 했습니다.\n", deletedQuiz.getAnswer());

    } catch (Exception e) {
      System.out.println("삭제 중 오류 발생!");
    }
  }
}
