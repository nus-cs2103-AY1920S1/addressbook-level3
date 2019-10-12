package seedu.flashcard.flashcardmodel.flashcard;

/**
 * Represent the question on each flashcardmodel
 */
public abstract class Question {

    public static final String MESSAGE_CONSTRAINTS = "The question can take in any value, but it should not be empty.";
    private String question;

    public Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    /**
     * Modify the question on the flashcardmodel
     * @param newQuestion The updated question of the flashcardmodel
     */
    public void setQuestion(String newQuestion) {
        question = newQuestion;
    }

    /**
     * While searching for a flashcardmodel, check if the question contains the target message
     * @param s the target message
     * @return true if the question contains target message, false otherwise
     */
    public boolean contains(String s) {
        return question.contains(s);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Question)) {
            return false;
        }
        Question otherQuestion = (Question) other;
        return otherQuestion.getQuestion().equals(this.getQuestion());
    }

    @Override
    public String toString() {
        return this.question;
    }

    @Override
    public int hashCode() {
        return question.hashCode();
    }
}
