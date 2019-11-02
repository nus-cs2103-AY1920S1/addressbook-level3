package seedu.revision.testutil;

import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;

import java.util.ArrayList;

public class McqBuilder extends AnswerableBuilder {

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
     */
    public AnswerableBuilder withWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
        this.wrongAnswerList = wrongAnswerList;
        return this;
    }

    public Mcq build() {
        return new Mcq(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }
}
