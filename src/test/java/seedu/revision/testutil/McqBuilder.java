package seedu.revision.testutil;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.testutil.builder.AnswerableBuilder;

/** Builder class to create {@code Mcq} objects. **/
public class McqBuilder extends AnswerableBuilder<Mcq> {

    public McqBuilder() {
        super();
        wrongAnswerList = new ArrayList<>();
        wrongAnswerList.add(new Answer("Wrong answer B"));
        wrongAnswerList.add(new Answer("Wrong answer C"));
        wrongAnswerList.add(new Answer("Wrong answer D"));
    }

    public McqBuilder(Answerable answerableToCopy) {
        super(answerableToCopy);
        assertTrue(answerableToCopy instanceof Mcq);
        wrongAnswerList = new ArrayList<>(answerableToCopy.getWrongAnswerList());
    }

    /**
     * Sets the Wrong Answer Set of the {@code Answerable} that we are building.
     * @param wrongAnswerList
     * @return McqBuilder object with the updated wrong answer list.
     */
    public McqBuilder withWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
        this.wrongAnswerList = wrongAnswerList;
        return this;
    }

    /** Builds an {@code Mcq} object with the updated parameters.**/
    public Mcq build() {
        return new Mcq(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }

}
