package bitcamp.project4.listener;

import bitcamp.project4.context.ApplicationContext;
import bitcamp.project4.dao.ListQuizDao;
import bitcamp.project4.dao.skel.QuizDaoSkel;
import bitcamp.project4.myapp.dao.QuizDao;

public class InitApplicationListener implements ApplicationListener {

  QuizDao quizDao;

  @Override
  public void onStart(ApplicationContext ctx) throws Exception {
    quizDao = new ListQuizDao("data.xlsx");

    QuizDaoSkel quizDaoSkel = new QuizDaoSkel(quizDao);

    ctx.setAttribute("quizDaoSkel", quizDaoSkel);
  }

  @Override
  public void onShutdown(ApplicationContext ctx) throws Exception {
    try {
      ((ListQuizDao) quizDao).save();
    } catch (Exception e) {
      System.out.println("퀴즈 데이터 저장 중 오류 발생!");
      e.printStackTrace();
      System.out.println();
    }
  }
}
