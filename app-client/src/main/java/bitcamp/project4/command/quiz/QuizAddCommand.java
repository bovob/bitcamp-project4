package bitcamp.project4.command.quiz;

import bitcamp.project4.command.Command;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import bitcamp.project4.util.Prompt;

public class QuizAddCommand implements Command {

  private QuizDao quizDao;

  public QuizAddCommand(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);
    try {
      Quiz quiz = new Quiz();
      quiz.setNumber(Prompt.inputInt("글자 수는?"));
      quiz.setAnswer(Prompt.input("정답은?"));

      quizDao.insert(quiz);
    } catch (Exception e) {
      System.out.println("등록 중 오류 발생!");
    }
  }
}
