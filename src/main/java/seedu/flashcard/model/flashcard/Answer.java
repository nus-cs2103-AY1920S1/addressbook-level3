package seedu.flashcard.model.flashcard;

/**
 * Represent the answers of each model
 */
public class Answer {

    public static final String MESSAGE_CONSTRAINTS = "The answer can take in any value, but it should not be empty.";
    private String answer;

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    /**
     * Modify the answer on the model
     * @param newAnswer the updated answer of the model
     */
    public void setAnswer(String newAnswer) {
        answer = newAnswer;
    }

    /**
     * Compare with the user input to check if the answer is correct or not
     * Only applies to MCQ questions
     * @param input The answer input by the user
     * @return true if the answer matches, false otherwise
     */
    public boolean compareAnswer(String input) {
        return input.equals(answer);
    }

    /**
     * While searching for a model by keyword s, decide whether this answer matches the keyword or not
     * @param s The target keyword
     * @return true if the answer contains the keyword, false otherwise
     */
    public boolean contains(String s) {
        return answer.contains(s);
    }

    @Override
    public String toString() {
        return answer;
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }
}
