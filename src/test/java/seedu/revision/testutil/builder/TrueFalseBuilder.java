package seedu.revision.testutil.builder;

import java.util.ArrayList;

import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.TrueFalse;

/** Factory class to create {@code TrueFalse} objects. **/
public class TrueFalseBuilder extends AnswerableBuilder<TrueFalse> {

    public TrueFalseBuilder() {
        super();
        correctAnswerList = new ArrayList<>();
        correctAnswerList.add(new Answer("True"));
        this.withCorrectAnswerList(correctAnswerList);
    }

    public TrueFalseBuilder(Answerable answerableToCopy) {
        super(answerableToCopy);
        wrongAnswerList = new ArrayList<>(answerableToCopy.getWrongAnswerList());
    }

    /**
     * Does nothing with the wrongAnswerList.
     * @param wrongAnswerList
     * @return TrueFalseBuilder object with an empty wrong answer list.
     */
    public TrueFalseBuilder withWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
        this.wrongAnswerList = new ArrayList<>();
        return this;
    }



    /** Builds a {@code TrueFalse} object with the updated parameters.**/
    public TrueFalse build() {
        return new TrueFalse(question, correctAnswerList, difficulty, categories);
    }
}
