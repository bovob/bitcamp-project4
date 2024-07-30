package bitcamp.project4.listener;

import bitcamp.project4.context.ApplicationContext;
import bitcamp.project4.dao.ListQuizDao;
import bitcamp.project4.dao.ListUserDao;
import bitcamp.project4.dao.skel.QuizDaoSkel;
import bitcamp.project4.dao.skel.UserDaoSkel;
import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.dao.UserDao;

public class InitApplicationListener implements ApplicationListener {

  UserDao userDao;
  QuizDao quizDao;

  @Override
  public void onStart(ApplicationContext ctx) throws Exception {
    userDao = new ListUserDao("data.xlsx");
    quizDao = new ListQuizDao("data.xlsx");

    UserDaoSkel userDaoSkel = new UserDaoSkel(userDao);
    QuizDaoSkel quizDaoSkel = new QuizDaoSkel(quizDao);

    ctx.setAttribute("userDaoSkel", userDaoSkel);
    ctx.setAttribute("quizDaoSkel", quizDaoSkel);
  }

  @Override
  public void onShutdown(ApplicationContext ctx) throws Exception {
    try {
      ((ListUserDao) userDao).save();
    } catch (Exception e) {
      System.out.println("회원 데이터 저장 중 오류 발생!");
      e.printStackTrace();
      System.out.println();
    }
    try {
      ((ListQuizDao) quizDao).save();
    } catch (Exception e) {
      System.out.println("퀴즈 데이터 저장 중 오류 발생!");
      e.printStackTrace();
      System.out.println();
    }
  }
}
