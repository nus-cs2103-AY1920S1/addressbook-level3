package seedu.address.model.statistics;

import seedu.address.model.question.Answer;
import seedu.address.model.question.QuestionBody;

/**
 * Basic question model derived from quiz results.
 * This class is temporary.
 */
public class TempStatsQnsModel {
    private QuestionBody question;
    private Answer answer;

    public TempStatsQnsModel(QuestionBody question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question.toString();
    }

    public String getAnswer() {
        return answer.toString();
    }

    @Override
    public String toString() {
        return question + "\n   " + answer + "\n";
    }
}
