package bitcamp.project4.command.quiz;

import bitcamp.project4.command.Command;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import bitcamp.project4.util.Prompt;

public class QuizUpdateCommand implements Command {

  private QuizDao quizDao;

  public QuizUpdateCommand(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);
    int quizNo = Prompt.inputInt("번호?");

    try {
      Quiz quiz = quizDao.findBy(quizNo);
      if (quiz == null) {
        System.out.println("없는 번호입니다.");
        return;
      }

      quiz.setNumber(Prompt.inputInt("글자수(%s)?", quiz.getNumber()));
      quiz.setAnswer(Prompt.input("정답(%s)?", quiz.getAnswer()));

      quizDao.update(quiz);
      System.out.println("변경 했습니다.");

    } catch (Exception e) {
      System.out.println("변경 중 오류 발생!");
    }
  }

}
