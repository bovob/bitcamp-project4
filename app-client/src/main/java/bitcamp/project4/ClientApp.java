package bitcamp.project4;

import bitcamp.project4.context.ApplicationContext;
import bitcamp.project4.listener.ApplicationListener;
import bitcamp.project4.listener.InitApplicationListener;
import bitcamp.project4.util.Prompt;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientApp {

  List<ApplicationListener> listeners = new ArrayList<>();
  ApplicationContext appCtx = new ApplicationContext();
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private Set<Character> guessedLetters;

  public ClientApp() {
    this.guessedLetters = new HashSet<>();
  }

  public static void main(String[] args) {
    ClientApp app = new ClientApp();
    app.addApplicationListener(new InitApplicationListener());
    app.execute();
  }

  private void addApplicationListener(ApplicationListener listener) {
    listeners.add(listener);
  }

  void execute() {
    try {
      System.out.println("Default [ localhost / 8888 ]");
      appCtx.setAttribute("host", Prompt.input("서버 주소?"));
      appCtx.setAttribute("port", Prompt.inputInt("포트 번호?"));

      for (ApplicationListener listener : listeners) {
        try {
          listener.onStart(appCtx);
        } catch (Exception e) {
          System.out.println("리스너 실행 중 오류 발생!");
        }
      }

      System.out.println("---------------------------------");
      System.out.println("[Welcome to Hang Man Game! \uD83C\uDFAE]");
      System.out.println("---------------------------------");

      while (true) {
        System.out.println("---------------------------------");
        System.out.println("Hang Man Game ◉o◉)⊃━🎮");
        System.out.println("---------------------------------");
        String command = Prompt.input("1) 게임 시작 2) 종료 > ");
        if (command.equals("1")) {
          connectToServer();
          playHangman();
          closeConnection();
        } else if (command.equals("2")) {
          break;
        }
      }

    } catch (Exception ex) {
      System.out.println("실행 오류!");
      ex.printStackTrace();
    } finally {
      closeConnection();
    }

    System.out.println("종료합니다.");
    Prompt.close();

    for (ApplicationListener listener : listeners) {
      try {
        listener.onShutdown(appCtx);
      } catch (Exception e) {
        System.out.println("리스너 실행 중 오류 발생!");
      }
    }
  }

  private void connectToServer() throws Exception {
    String host = (String) appCtx.getAttribute("host");
    int port = (int) appCtx.getAttribute("port");
    socket = new Socket(host, port);
    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(socket.getInputStream());
  }

  private void playHangman() throws Exception {
    try {
      if (!isConnected()) {
        System.out.println("서버와 연결되어 있지 않습니다. 다시 연결을 시도합니다.");
        connectToServer();
      }

      out.writeUTF("hangman");
      out.flush();

      int wordLength = (int) in.readObject();
      int turnsLeft = in.readInt();
      String topic = (String) in.readObject();
      String gameState = (String) in.readObject();

      System.out.println("행맨 게임을 시작합니다!");
      System.out.println("주제: " + topic);
      System.out.println("단어 길이: " + wordLength);
      System.out.println(gameState);

      guessedLetters.clear();

      while (true) {
        char guess = getGuessFromUser();

        out.writeChar(guess);
        out.flush();

        try {
          boolean correctGuess = in.readBoolean();
          turnsLeft = in.readInt();
          String currentWord = (String) in.readObject();
          boolean gameOver = in.readBoolean();
          gameState = (String) in.readObject();

          System.out.println(gameState);

          if (gameOver) {
            String answer = (String) in.readObject();
            boolean win = in.readBoolean();
            if (win) {
              System.out.println("                               ……….");
              System.out.println("                          ………………....... ");
              System.out.println("                       ……       ✨           …");
              System.out.println("                  …    ✨                ✨     ….");
              System.out.println("             ……              𝐜𝐨𝐧𝐠𝐫𝐚𝐭𝐳             ……");
              System.out.println("        ………            👏    🎉   👍           ………");
              System.out.println("             ……✨          ◝(ᵔᵕᵔ)◜     ✨   ……");
              System.out.println("                  ……                             …….");
              System.out.println("                       ……        ✨        …. ");
              System.out.println("                             …………….... ");
              System.out.println("                                  …….");

              System.out.println("----------------------------------");
              System.out.println("축하합니다! 정답을 맞추셨습니다.");
              System.out.println("----------------------------------");
            } else {
              System.out.println("   ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠀");
              System.out.println("    ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠀⠀⠀⣶⡆⠀⣰⣿⠇⣾⡿⠛⠉⠁");
              System.out.println("  ⠀⣠⣴⠾⠿⠿⠀⢀⣾⣿⣆⣀⣸⣿⣷⣾⣿⡿⢸⣿⠟⢓⠀⠀");
              System.out.println("  ⣴⡟⠁⣀⣠⣤⠀⣼⣿⠾⣿⣻⣿⠃⠙⢫⣿⠃⣿⡿⠟⠛⠁⠀");
              System.out.println("  ⢿⣝⣻⣿⡿⠋⠾⠟⠁⠀⠹⠟⠛⠀⠀⠈⠉⠀⠉⠀⠀⠀⠀⠀");
              System.out.println("  ⠀⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⣀⢀⣠⣤⣴⣤⣄⠀");
              System.out.println("  ⠀⠀⠀⠀⣀⣤⣤⢶⣤⠀⠀⢀⣴⢃⣿⠟⠋⢹⣿⣣⣴⡿⠋⠀");
              System.out.println("  ⠀⠀⣰⣾⠟⠉⣿⡜⣿⡆⣴⡿⠁⣼⡿⠛⢃⣾⡿⠋⢻⣇⠀⠀");
              System.out.println("  ⠀⠐⣿⡁⢀⣠⣿⡇⢹⣿⡿⠁⢠⣿⠷⠟⠻⠟⠀⠀⠈⠛⠀⠀");
              System.out.println("  ⠀⠀⠙⠻⠿⠟⠋⠀⠀⠙⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");

              System.out.println("----------------------------------");
              System.out.println("아쉽네요. 다음에 다시 도전해보세요!");
              System.out.println("----------------------------------");
            }
            System.out.println("정답은 '" + answer + "' 였습니다.");
            break;
          }
        } catch (EOFException e) {
          System.out.println("서버와의 연결이 끊어졌습니다. 게임을 종료합니다.");
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("게임 진행 중 오류가 발생했습니다: " + e.getMessage());
      closeConnection();
    }
  }

  private boolean isConnected() {
    return socket != null && socket.isConnected() && !socket.isClosed();
  }

  private void closeConnection() {
    try {
      if (in != null) in.close();
      if (out != null) out.close();
      if (socket != null) socket.close();
    } catch (Exception e) {
      System.out.println("연결 종료 중 오류 발생: " + e.getMessage());
    } finally {
      in = null;
      out = null;
      socket = null;
    }
  }

  private char getGuessFromUser() {
    while (true) {
      System.out.print("글자를 추측하세요: ");
      String input = Prompt.input("").trim().toLowerCase();

      if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
        char guess = input.charAt(0);

        if (guessedLetters.contains(guess)) {
          System.out.println("이전에 입력한 글자입니다. 다른 글자를 입력해주세요.");
          continue;
        }
        guessedLetters.add(guess);
        return guess;
      } else {
        System.out.println("잘못된 입력입니다. 알파벳 하나만 입력해주세요.");
      }
    }
  }
}