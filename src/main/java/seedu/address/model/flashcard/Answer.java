package seedu.address.model.flashcard;

public class Answer {

    private String fullAnswer;

    public Answer(String fullAnswer) {
        this.fullAnswer = fullAnswer;
    }

    @Override
    public String toString() {
        return "Answer";
    }
}
