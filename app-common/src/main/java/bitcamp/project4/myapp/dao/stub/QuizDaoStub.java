package bitcamp.project4.myapp.dao.stub;

import bitcamp.project4.myapp.dao.QuizDao;
import bitcamp.project4.myapp.vo.Quiz;
import bitcamp.project4.net.ResponseStatus;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class QuizDaoStub implements QuizDao {

  private String host;
  private int port;
  private String dataName;

  public QuizDaoStub(String host, int port, String dataName)
      throws Exception {
    this.host = host;
    this.port = port;
    this.dataName = dataName;
  }

  @Override
  public boolean insert(Quiz quiz) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF(dataName);
      out.writeUTF("insert");
      out.writeObject(quiz);
      out.flush();

      if (in.readUTF().equals(ResponseStatus.SUCCESS)) {
        return true;
      }

      return false;
    }
  }

  @Override
  public List<Quiz> list() throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
      
      out.writeUTF(dataName);
      out.writeUTF("list");
      out.flush();

      if (in.readUTF().equals(ResponseStatus.SUCCESS)) {
        return (List<Quiz>) in.readObject();
      }

      return null;
    }
  }

  @Override
  public Quiz findBy(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF(dataName);
      out.writeUTF("get");
      out.writeInt(no);
      out.flush();

      if (in.readUTF().equals(ResponseStatus.SUCCESS)) {
        return (Quiz) in.readObject();
      }

      return null;
    }
  }

  @Override
  public boolean update(Quiz quiz) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF(dataName);
      out.writeUTF("update");
      out.writeObject(quiz);
      out.flush();

      if (in.readUTF().equals(ResponseStatus.SUCCESS)) {
        return true;
      }

      return false;
    }
  }

  @Override
  public boolean delete(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF(dataName);
      out.writeUTF("delete");
      out.writeInt(no);
      out.flush();

      if (in.readUTF().equals(ResponseStatus.SUCCESS)) {
        return true;
      }

      return false;
    }
  }
}
