package app-common;

public class BaseballGame {
    private String targetNumber;

    public BaseballGame(String targetNumber) {
        this.targetNumber = targetNumber;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public String checkGuess(String guess) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < targetNumber.length(); i++) {
            if (guess.charAt(i) == targetNumber.charAt(i)) {
                strikes++;
            } else if (targetNumber.contains(String.valueOf(guess.charAt(i)))) {
                balls++;
            }
        }

        return strikes + "S" + balls + "B";
    }
}
