package seedu.revision.testutil;

import java.util.ArrayList;

import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;

/** Builder class to create {@code Mcq} objects. **/
public class McqBuilder extends AnswerableBuilder<Mcq> {

    public McqBuilder() {
        super();
        wrongAnswerList = new ArrayList<>();
        wrongAnswerList.add(new Answer("Wrong answer A"));
        wrongAnswerList.add(new Answer("Wrong answer B"));
        wrongAnswerList.add(new Answer("Wrong answer C"));
    }

    public McqBuilder(Answerable answerableToCopy) {
        super(answerableToCopy);
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

    public Mcq buildTest() {
        return new Mcq(correctAnswerList, wrongAnswerList);
    }
}
