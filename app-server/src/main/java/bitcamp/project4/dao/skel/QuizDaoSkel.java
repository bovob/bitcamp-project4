package bitcamp.project4.dao.skel;

import static bitcamp.project4.net.ResponseStatus.ERROR;
import static bitcamp.project4.net.ResponseStatus.FAILURE;
import static bitcamp.project4.net.ResponseStatus.SUCCESS;

import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class QuizDaoSkel {

  private QuizDao quizDao;

  public QuizDaoSkel(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    String command = in.readUTF();

    Quiz quiz = null;
    int no = 0;

    switch (command) {
      case "insert":
        quiz = (Quiz) in.readObject();
        quizDao.insert(quiz);
        out.writeUTF(SUCCESS);
        break;
      case "list":
        List<Quiz> list = quizDao.list();
        out.writeUTF(SUCCESS);
        out.writeObject(list);
        break;
      case "get":
        no = in.readInt();
        quiz = quizDao.findBy(no);
        if (quiz != null) {
          out.writeUTF(SUCCESS);
          out.writeObject(quiz);
        } else {
          out.writeUTF(FAILURE);
        }
        break;
      case "update":
        quiz = (Quiz) in.readObject();
        if (quizDao.update(quiz)) {
          out.writeUTF(SUCCESS);
        } else {
          out.writeUTF(FAILURE);
        }
        break;
      case "delete":
        no = in.readInt();
        if (quizDao.delete(no)) {
          out.writeUTF(SUCCESS);
        } else {
          out.writeUTF(FAILURE);
        }
        break;
      default:
        out.writeUTF(ERROR);
        out.writeUTF("무효한 명령입니다.");
    }

    out.flush();
  }

}
