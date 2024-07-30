package bitcamp.project4.myapp.dao;

import bitcamp.project4.myapp.vo.Quiz;
import java.util.List;

public interface QuizDao {

  boolean insert(Quiz quiz) throws Exception;

  List<Quiz> list() throws Exception;

  Quiz findBy(int no) throws Exception;

  boolean update(Quiz quiz) throws Exception;

  boolean delete(int no) throws Exception;
}
