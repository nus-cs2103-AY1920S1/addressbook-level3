package seedu.address.model.answerable;

public class McqAnswer implements Answer {

    //TODO: Might change to multiple response
    public static final String MESSAGE_CONSTRAINTS = "MCQ answers should only be a, b, c or d";

    public final String value;

    public McqAnswer(String answer) {
        this.value = answer;
    }

    @Override
    public boolean isCorrect(String answer) {
        //TODO: Implement isCorrect method
        return true;
    }

}
