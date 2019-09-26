package seedu.address.flashcard;

import java.util.List;

public class McqQuestion extends Question {

    List<String> options;

    public McqQuestion(String question) {
        this.question = question;
    }

    public McqQuestion(String question, List<String> options) {
        this(question);
        this.options = options;
    }

    @Override
    public String toString() {
        String result = question;
        for (String s : options) {
            result += "\n";
            result += s;
        }
        return result;
    }
}
