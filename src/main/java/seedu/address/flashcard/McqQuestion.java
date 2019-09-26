package seedu.address.flashcard;

import java.util.List;

public class McqQuestion extends Question {

    List<String> options;

    public McqQuestion(String question) {
        super(question);
    }

    public McqQuestion(String question, List<String> options) {
        this(question);
        this.options = options;
    }

    public void setOptions(List<String> newOptions) {
        this.options = newOptions;
    }

    @Override
    public String toString() {
        String result = this.getQuestion();
        for (String s : options) {
            result += "\n";
            result += s;
        }
        return result;
    }
}
