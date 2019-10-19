package seedu.flashcard.model.flashcard;

public class Score {
    private int correctAnswers;
    private int wrongAnswers;

    public Score() {
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
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
}
