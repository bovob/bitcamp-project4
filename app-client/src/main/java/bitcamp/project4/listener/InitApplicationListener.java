package bitcamp.project4.listener;

import bitcamp.project4.command.quiz.QuizAddCommand;
import bitcamp.project4.command.quiz.QuizDeleteCommand;
import bitcamp.project4.command.quiz.QuizListCommand;
import bitcamp.project4.command.quiz.QuizUpdateCommand;
import bitcamp.project4.command.quiz.QuizViewCommand;
import bitcamp.project4.command.user.UserAddCommand;
import bitcamp.project4.command.user.UserDeleteCommand;
import bitcamp.project4.command.user.UserListCommand;
import bitcamp.project4.command.user.UserUpdateCommand;
import bitcamp.project4.command.user.UserViewCommand;
import bitcamp.project4.context.ApplicationContext;
import bitcamp.project4.menu.MenuGroup;
import bitcamp.project4.menu.MenuItem;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.dao.UserDao;
import bitcamp.project4.myapp.dao.stub.QuizDaoStub;
import bitcamp.project4.myapp.dao.stub.UserDaoStub;

public class InitApplicationListener implements ApplicationListener {

  UserDao userDao;
  QuizDao quizDao;

  @Override
  public void onStart(ApplicationContext ctx) throws Exception {

    String host = (String) ctx.getAttribute("host");
    int port = (int) ctx.getAttribute("port");

    userDao = new UserDaoStub(host, port, "users");

    MenuGroup mainMenu = ctx.getMainMenu();

    MenuGroup userMenu = new MenuGroup("회원");
    userMenu.add(new MenuItem("등록", new UserAddCommand(userDao)));
    userMenu.add(new MenuItem("목록", new UserListCommand(userDao)));
    userMenu.add(new MenuItem("조회", new UserViewCommand(userDao)));
    userMenu.add(new MenuItem("변경", new UserUpdateCommand(userDao)));
    userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userDao)));
    mainMenu.add(userMenu);

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
