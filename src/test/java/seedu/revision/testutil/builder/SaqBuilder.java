package seedu.revision.testutil.builder;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        correctAnswerList.add(new Answer("not true"));
        correctAnswerList.add(new Answer("Greenfield and brownfield projects are equally difficult"));
        this.withCorrectAnswerList(correctAnswerList);
    }

    public SaqBuilder(Answerable answerableToCopy) {
        super(answerableToCopy);
        assertTrue(answerableToCopy instanceof Saq);
        wrongAnswerList = new ArrayList<>();
    }

    /**
     * Does nothing with the wrong answerList, because Saq does not have a wrong answer list.
     * @param wrongAnswerList
     * @return SaqBuilder object with an empty wrong answer list.
     */
    public SaqBuilder withWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
        this.wrongAnswerList = new ArrayList<>();
        return this;
    }

    /** Builds a {@code TrueFalse} object with the updated parameters.**/
    public Saq build() {
        return new Saq(question, correctAnswerList, difficulty, categories);
    }
}

