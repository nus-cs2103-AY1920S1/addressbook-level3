package seedu.flashcard.model.flashcard;

import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * Score of each Flashcard
 */
public class Score {

    public static final String MESSAGE_CONSTRAINTS =
        "The two numbers for the score, correct answers and wrong answers, must both be non-negative";

    private int correctAnswers;
    private int wrongAnswers;

    /**
     * Used when the user is creating a new flashcard.
     */
    public Score() {
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
    }

    /**
     * Used when loading score from Json file.
     */
    public Score(int correctAnswers, int wrongAnswers) {
        checkArgument(isValidScore(correctAnswers, wrongAnswers), MESSAGE_CONSTRAINTS);
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    /**
     * Used to check in the storage class to ensure the scores loaded are valid.
     */
    public static boolean isValidScore(String score) {
        String[] splitScore = score.split(" ");
        if (splitScore.length != 2) {
            return false;
        }
        return Integer.parseInt(splitScore[0]) >= 0 && Integer.parseInt(splitScore[1]) >= 0;
    }

    /**
     * Used to check for int values.
     */
    public static boolean isValidScore(int correctAnswers, int wrongAnswers) {
        return correctAnswers >= 0 && wrongAnswers >= 0;
    }



    /**
     * Returns the number of times the flashcard has been answered correctly.
     * @return Number of correct answers.
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Returns the number of times the flashcard has been answered wrongly.
     * @return Number of wrong answers.
     */
    public int getWrongAnswers() {
        return wrongAnswers;
    }

    /**
     * Returns the number of times the flashcard has been answered.
     * @return Total number of attempts of the flashcard.
     */
    public int getTotalAttempts() {
        return correctAnswers + wrongAnswers;
    }

    /**
     * Adds one to the number of times the flashcard has been correctly answered.
     */
    public void incrementCorrectAnswer() {
        this.correctAnswers++;
    }

    /**
     * Adds one to the number of times the flashcard has been wrongly answered.
     */
    public void incrementWrongAnswer() {
        this.wrongAnswers++;
    }

    @Override
    public boolean equals(Object other) {
        Score otherScore = (Score) other;
        return correctAnswers == otherScore.correctAnswers && wrongAnswers == otherScore.wrongAnswers;
    }
}
