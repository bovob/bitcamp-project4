package bitcamp.project4;

import bitcamp.project4.dao.ListQuizDao;
import bitcamp.project4.myapp.vo.Quiz;

import java.util.*;

public class Hangman {
    private static final int MAX_TRIES = 6;
    private ListQuizDao quizDao;
    private Quiz currentQuiz;
    private Set<Character> guessedLetters;
    private int turnsLeft;
    private String topic;
    private String hint;
    private int wrongGuesses;

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
        turnsLeft = MAX_TRIES;
        topic = currentQuiz.getTopic();
        hint = currentQuiz.getHint();
        wrongGuesses = 0;
    }

    public boolean processGuess(char guess) {
        if (!Character.isLetter(guess)) {
            return false;
        }

        guess = Character.toLowerCase(guess);
        boolean alreadyGuessed = guessedLetters.contains(guess);

        if (alreadyGuessed) {
            return false;  // 이미 추측한 글자는 false를 반환
        }

        guessedLetters.add(guess);

        if (currentQuiz.getAnswer().toLowerCase().indexOf(guess) == -1) {
            wrongGuesses++;
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

    public String getTopic() {
        return topic;
    }

    public String getHint() {
        return hint;
    }

    public boolean shouldShowHint() {
        return wrongGuesses >= 3;
    }

    public String getHangmanImage() {
        String[] hangmanStages = {
            "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
        };

        return hangmanStages[Math.min(wrongGuesses, MAX_TRIES)];
    }

    public String getGameState() {
        StringBuilder state = new StringBuilder();
        state.append(getHangmanImage()).append("\n\n");
        state.append("Word: ").append(getDisplayWord()).append("\n");
        state.append("Turns left: ").append(turnsLeft).append("\n");
        state.append("Topic: ").append(topic).append("\n");
        state.append("Guessed letters: ").append(String.join(", ", guessedLetters.stream().map(String::valueOf).sorted().toList())).append("\n");
        if (shouldShowHint()) {
            state.append("Hint: ").append(hint).append("\n");
        }
        return state.toString();
    }
}