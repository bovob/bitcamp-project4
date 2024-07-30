package bitcamp.project4;

import bitcamp.project4.dao.ListQuizDao;
import bitcamp.project4.myapp.vo.Quiz;

import java.util.*;

public class Hangman {
    private static final int MAX_TRIES = 6;
    private ListQuizDao quizDao;
    private Quiz currentQuiz;
    private Set<Character> guessedLetters;
    private int triesLeft;

    public Hangman(String excelFilePath) {
        quizDao = new ListQuizDao(excelFilePath);
        guessedLetters = new HashSet<>();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            startNewGame();
            while (!isGameOver()) {
                displayGameStatus();
                System.out.print("글자를 추측하세요: ");
                char guess = scanner.nextLine().toLowerCase().charAt(0);
                processGuess(guess);
            }
            displayGameResult();

            System.out.print("다시 플레이하시겠습니까? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                break;
            }
        }
        scanner.close();
    }

    private void startNewGame() {
        List<Quiz> quizzes;
        try {
            quizzes = quizDao.list();
            if (quizzes.isEmpty()) {
                throw new IllegalStateException("퀴즈 목록이 비어있습니다.");
            }
            currentQuiz = quizzes.get(new Random().nextInt(quizzes.size()));
        } catch (Exception e) {
            System.out.println("퀴즈를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            System.exit(1);
        }
        guessedLetters.clear();
        triesLeft = MAX_TRIES;
    }

    private void processGuess(char guess) {
        if (guessedLetters.contains(guess)) {
            System.out.println("이미 추측한 글자입니다.");
            return;
        }

        guessedLetters.add(guess);
        if (currentQuiz.getAnswer().indexOf(guess) == -1) {
            triesLeft--;
            System.out.println("틀렸습니다!");
        } else {
            System.out.println("맞았습니다!");
        }
    }

    private void displayGameStatus() {
        System.out.println("\n단어: " + getDisplayWord());
        System.out.println("남은 시도: " + triesLeft);
        System.out.println("추측한 글자: " + guessedLetters);
    }

    private String getDisplayWord() {
        StringBuilder display = new StringBuilder();
        for (char c : currentQuiz.getAnswer().toCharArray()) {
            if (guessedLetters.contains(Character.toLowerCase(c))) {
                display.append(c);
            } else {
                display.append("_");
            }
            display.append(" ");
        }
        return display.toString();
    }

    private boolean isGameOver() {
        return triesLeft == 0 || getDisplayWord().replace(" ", "").equals(currentQuiz.getAnswer());
    }

    private void displayGameResult() {
        if (triesLeft == 0) {
            System.out.println("게임 오버! 정답은 '" + currentQuiz.getAnswer() + "' 였습니다.");
        } else {
            System.out.println("축하합니다! 정답을 맞추셨습니다: " + currentQuiz.getAnswer());
        }
    }

    public static void main(String[] args) {
        // file path 써야됨 !!!!
        new Hangman("data.xlsx").play();
    }
}