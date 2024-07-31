package bitcamp.project4.listener;

import bitcamp.project4.command.quiz.QuizAddCommand;
import bitcamp.project4.command.quiz.QuizDeleteCommand;
import bitcamp.project4.command.quiz.QuizListCommand;
import bitcamp.project4.command.quiz.QuizUpdateCommand;
import bitcamp.project4.command.quiz.QuizViewCommand;
import bitcamp.project4.context.ApplicationContext;
import bitcamp.project4.menu.MenuGroup;
import bitcamp.project4.menu.MenuItem;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.dao.stub.QuizDaoStub;

public class InitApplicationListener implements ApplicationListener {

  QuizDao quizDao;

  @Override
  public void onStart(ApplicationContext ctx) throws Exception {

    String host = (String) ctx.getAttribute("host");
    int port = (int) ctx.getAttribute("port");

    MenuGroup mainMenu = ctx.getMainMenu();

    quizDao = new QuizDaoStub(host, port, "quiz");

    MenuGroup quizMenu = new MenuGroup("퀴즈목록");
    quizMenu.add(new MenuItem("등록", new QuizAddCommand(quizDao)));
    quizMenu.add(new MenuItem("목록", new QuizListCommand(quizDao)));
    quizMenu.add(new MenuItem("조회", new QuizViewCommand(quizDao)));
    quizMenu.add(new MenuItem("변경", new QuizUpdateCommand(quizDao)));
    quizMenu.add(new MenuItem("삭제", new QuizDeleteCommand(quizDao)));
    mainMenu.add(quizMenu);


    mainMenu.setExitMenuTitle("종료");
  }
}
