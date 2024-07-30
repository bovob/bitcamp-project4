package client;

import server.Server;
import java.util.Scanner;

public class Client {
    private Server server;

    public Client(Server server) {
        this.server = server;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String result;

        System.out.println("숫자야구 게임을 시작합니다.");

        do {
            System.out.print("숫자를 입력하세요: ");
            String guess = scanner.nextLine();
            result = server.checkGuess(guess);
            System.out.println("결과: " + result);
        } while (!result.equals("3S0B"));

        System.out.println("정답입니다! 게임을 종료합니다.");
    }
}
