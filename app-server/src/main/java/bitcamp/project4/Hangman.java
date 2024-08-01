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

        guessedLetters.add(guess);

        if (currentQuiz.getAnswer().toLowerCase().indexOf(guess) == -1) {
            if (!alreadyGuessed) {
                // 첫 추측이라면 틀린 경우에만 턴 수를 줄인다.
                wrongGuesses++;
            }
            turnsLeft--; // 이미 틀린 단어를 중복 입력해도 턴 수를 깎는다.
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

        return hangmanStages[wrongGuesses];
    }

    public String getGameState() {
        StringBuilder state = new StringBuilder();
        state.append(getHangmanImage()).append("\n\n");
        state.append("Word: ").append(getDisplayWord()).append("\n");
        state.append("Turns left: ").append(turnsLeft).append("\n");
        state.append("Topic: ").append(topic).append("\n");
        if (shouldShowHint()) {
            state.append("Hint: ").append(hint).append("\n");
        }
        return state.toString();
    }
}
