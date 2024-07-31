package bitcamp.project4;

import bitcamp.project4.dao.ListQuizDao;
import bitcamp.project4.myapp.vo.Quiz;

import java.util.*;

public class Hangman {
    private ListQuizDao quizDao;
    private Quiz currentQuiz;
    private Set<Character> guessedLetters;
    private int turnsLeft;

    public Hangman(String excelFilePath) {
        quizDao = new ListQuizDao(excelFilePath);
        guessedLetters = new HashSet<>();
    }

    public void startNewGame() {
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
        turnsLeft = currentQuiz.getAnswer().length() + 4;
    }

    public boolean processGuess(char guess) {
        // 입력된 문자가 알파벳인지 확인합니다.
        if (!Character.isLetter(guess)) {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요!");
            return false;  // 잘못된 입력이므로 턴 수를 깎지 않습니다.
        }

        // 이미 추측한 문자인지 확인합니다.
        if (guessedLetters.contains(guess)) {
            System.out.println("이미 추측한 문자입니다. 다른 문자를 입력해주세요.");
            return false;  // 이미 추측한 문자이므로 턴 수를 깎지 않습니다.
        }

        guessedLetters.add(guess);
        if (currentQuiz.getAnswer().toLowerCase().indexOf(Character.toLowerCase(guess)) == -1) {
            turnsLeft--;
            return false;  // 추측이 틀린 경우
        } else {
            return true;  // 추측이 맞은 경우
        }
    }

    public String getDisplayWord() {
        StringBuilder display = new StringBuilder();
        for (char c : currentQuiz.getAnswer().toCharArray()) {
            if (guessedLetters.contains(Character.toLowerCase(c))) {
                display.append(c);
            } else {
                display.append("_");
            }
            display.append(" ");
        }
        return display.toString().trim();
    }

    public boolean isGameOver() {
        return turnsLeft == 0 || getDisplayWord().replace(" ", "").equalsIgnoreCase(currentQuiz.getAnswer());
    }

    public boolean isWin() {
        return getDisplayWord().replace(" ", "").equalsIgnoreCase(currentQuiz.getAnswer());
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }
}
