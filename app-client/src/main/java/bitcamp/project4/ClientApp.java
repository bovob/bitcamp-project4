package bitcamp.project4;

import bitcamp.project4.context.ApplicationContext;
import bitcamp.project4.listener.ApplicationListener;
import bitcamp.project4.listener.InitApplicationListener;
import bitcamp.project4.util.Prompt;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientApp {

  List<ApplicationListener> listeners = new ArrayList<>();
  ApplicationContext appCtx = new ApplicationContext();

  public static void main(String[] args) {
    ClientApp app = new ClientApp();

    // 애플리케이션이 시작되거나 종료될 때 알림 받을 객체의 연락처를 등록한다.
    app.addApplicationListener(new InitApplicationListener());

    app.execute();
  }

  private void addApplicationListener(ApplicationListener listener) {
    listeners.add(listener);
  }

  private void removeApplicationListener(ApplicationListener listener) {
    listeners.remove(listener);
  }

  void execute() {

    try {
      System.out.println("Default [ localhost / 8888 ]");
      appCtx.setAttribute("host", Prompt.input("서버 주소?"));
      appCtx.setAttribute("port", Prompt.inputInt("포트 번호?"));

      // 애플리케이션이 시작될 때 리스너에게 알린다.
      for (ApplicationListener listener : listeners) {
        try {
          listener.onStart(appCtx);
        } catch (Exception e) {
          System.out.println("리스너 실행 중 오류 발생!");
        }
      }

      while (true) {
        String command = Prompt.input("1)게임시작 2)종료 > ");
        if (command.equals("1")) {
          playHangman();
        } else if (command.equals("2")) {
          break;
        }
      }


      //System.out.println("[프로젝트 관리 시스템]");
      //
      //appCtx.getMainMenu().execute();

    } catch (Exception ex) {
      System.out.println("실행 오류!");
      ex.printStackTrace();
    }

    System.out.println("종료합니다.");

    Prompt.close();

    // 애플리케이션이 종료될 때 리스너에게 알린다.
    for (ApplicationListener listener : listeners) {
      try {
        listener.onShutdown(appCtx);
      } catch (Exception e) {
        System.out.println("리스너 실행 중 오류 발생!");
      }
    }
  }

  private void playHangman() {
    try (
        Socket socket = new Socket((String) appCtx.getAttribute("host"), (int) appCtx.getAttribute("port"));
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
    ) {
      out.writeUTF("hangman");
      out.flush();

      int wordLength = (int) in.readObject();
      int turnsLeft = in.readInt();
      System.out.println("단어 길이: " + wordLength);
      System.out.println("남은 턴 수: " + turnsLeft);

      while (true) {
        System.out.print("알파벳을 입력하세요: ");
        char guess = Prompt.input("").toLowerCase().charAt(0);
        out.writeChar(guess);
        out.flush();

        boolean correctGuess = in.readBoolean();
        turnsLeft = in.readInt();
        String displayWord = (String) in.readObject();
        boolean gameOver = in.readBoolean();

        System.out.println(correctGuess ? "정답!" : "오답!");
        System.out.println("현재 상태: " + displayWord);
        System.out.println("남은 턴 수: " + turnsLeft);

        if (gameOver) {
          String answer = (String) in.readObject();
          boolean isWin = in.readBoolean();
          if (isWin) {
            System.out.println("축하합니다! 정답을 맞추셨습니다: " + answer);
          } else {
            System.out.println("게임 오버! 정답은 '" + answer + "' 였습니다.");
          }
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("게임 진행 중 오류 발생: " + e.getMessage());
    }
  }
}
