package seedu.address.flashcard;

import java.util.ArrayList;

public class McqFlashcard extends Flashcard {

    public McqFlashcard(McqQuestion question, Answer answer) {
        super(question, answer);
    }

    public void setOptions(ArrayList<String> newOptions) {
        ((McqQuestion) getQuestion()).setOptions(newOptions);
    }
}
