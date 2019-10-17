package seedu.address.model.quiz;

import seedu.address.model.question.Answer;
import seedu.address.model.question.QuestionBody;

/**
 * Basic question model derived from quiz results.
 * This class is temporary.
 */
public class TempQnsModel {
    private QuestionBody question;
    private Answer answer;

    public TempQnsModel(QuestionBody question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question.toString();
    }

    public String getAnswer() {
        return answer.toString();
    }
}
