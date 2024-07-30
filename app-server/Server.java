package server;

import common.BaseballGame;
import java.util.Random;

public class Server {
    private BaseballGame game;

    public Server() {
        this.game = new BaseballGame(generateRandomNumber());
    }

    private String generateRandomNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 3) {
            int digit = rand.nextInt(10);
            if (sb.indexOf(String.valueOf(digit)) == -1) {
                sb.append(digit);
            }
        }
        return sb.toString();
    }

    public String checkGuess(String guess) {
        return game.checkGuess(guess);
    }

    public String getTargetNumber() {
        return game.getTargetNumber();
    }
}
