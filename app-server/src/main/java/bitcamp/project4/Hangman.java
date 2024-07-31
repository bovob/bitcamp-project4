// Hangman.java
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
        guessedLetters.add(guess);
        if (currentQuiz.getAnswer().indexOf(guess) == -1) {
            turnsLeft--;
            return false;
        } else {
            return true;
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
        return turnsLeft == 0 || getDisplayWord().replace(" ", "").equals(currentQuiz.getAnswer());
    }

    public boolean isWin() {
        return getDisplayWord().replace(" ", "").equals(currentQuiz.getAnswer());
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }
}