package seedu.flashcard.flashcardmodel.flashcard;

import java.util.List;

/**
 * The question used for MCQ classes
 * It is by itself an MCQ question containing question statement and choices
 */
public class McqQuestion extends Question {

    private List<String> options;

    /**
     * Both question and options must be specified
     */
    public McqQuestion(String question, List<String> options) {
        super(question);
        this.options = options;
    }

    /**
     * edit the options of this question
     * @param newOptions the updated options for this question
     */
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
