package seedu.address.model.flashcard;

public class Question {

    private String fullQuestion;

    public Question(String fullQuestion) {
        this.fullQuestion = fullQuestion;
    }

    @Override
    public String toString() {
        return "Question";
    }
}
