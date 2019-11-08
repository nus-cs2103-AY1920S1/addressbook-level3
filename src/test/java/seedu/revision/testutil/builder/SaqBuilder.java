package seedu.revision.testutil.builder;

import java.util.ArrayList;

import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Saq;

public class SaqBuilder extends AnswerableBuilder<Saq> {
    public SaqBuilder() {
        super();
        correctAnswerList = new ArrayList<>();
        correctAnswerList.add(new Answer("The statement is untrue"));
        correctAnswerList.add(new Answer("incorrect"));
        correctAnswerList.add(new Answer("false"));
        correctAnswerList.add(new Answer("Greenfield and brownfield projects are equally difficult"));
        this.withCorrectAnswerList(correctAnswerList);
    }

    public SaqBuilder(Answerable answerableToCopy) {
        super(answerableToCopy);
    }

    /**
     * Does nothing with the wrong answerList
     * @param wrongAnswerList
     * @return SaqBuilder object with a null wrong answer list.
     */
    public SaqBuilder withWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
        return this;
    }

    /** Builds a {@code TrueFalse} object with the updated parameters.**/
    public Saq build() {
        return new Saq(question, correctAnswerList, difficulty, categories);
    }
}

