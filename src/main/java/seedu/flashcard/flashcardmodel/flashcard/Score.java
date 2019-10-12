package seedu.flashcard.flashcardmodel.flashcard;

/**
 * The record of how many correct and wrong answers has the user done.
 */
public class Score {

    private static final String MESSAGE_BEFORE_TOTAL = "Total number of practices: ";
    private static final String MESSAGE_BEFORE_CORRECT = "Number of correct answers: ";
    private static final String MESSAGE_BEFORE_WRONG = "Number of wrong answer: ";
    private static final String MESSAGE_BEFORE_PERCENTAGE = "Total Correct rate: ";
    private int correctAnswerNumber;
    private int wrongAnswerNumber;

    public Score() {
        correctAnswerNumber = 0;
        wrongAnswerNumber = 0;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    public void addCorrectAnswerNumber() {
        correctAnswerNumber++;
    }

    public void addWrongAnswerNumber() {
        wrongAnswerNumber++;
    }

    public int getTotalAnswerNumber() {
        return correctAnswerNumber + wrongAnswerNumber;
    }

    public float getCorrectRate() {
        return correctAnswerNumber / getCorrectAnswerNumber();
    }

    @Override
    public String toString() {
        String totalMessage = MESSAGE_BEFORE_TOTAL + Integer.toString(getTotalAnswerNumber()) + "\n";
        String correctMessage = MESSAGE_BEFORE_CORRECT + Integer.toString(getCorrectAnswerNumber()) + "\n";
        String wrongMessage = MESSAGE_BEFORE_WRONG + Integer.toString(getWrongAnswerNumber()) + "\n";
        String percentageMessage = MESSAGE_BEFORE_PERCENTAGE + Float.toString(getCorrectRate()) + "\n";
        return totalMessage + correctMessage + wrongMessage + percentageMessage;
    }
}
