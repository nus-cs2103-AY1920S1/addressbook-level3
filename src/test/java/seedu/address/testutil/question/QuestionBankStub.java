package seedu.address.testutil.question;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBank;

/**
 * A stub QuestionBank whose questions list can violate interface constraints.
 */
public class QuestionBankStub extends QuestionBank {

    private final ObservableList<Question> questions = FXCollections.observableArrayList();

    @Override
    public void setQuestions(List<Question> questions) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addQuestion(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Question deleteQuestion(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteQuestion(Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Question getQuestion(Index index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuestion(Index index, Question question) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getAllQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getSearchQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getMcqQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> getOpenEndedQuestions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String searchQuestions(String textToFind) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getQuestionsSummary() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean contains(Question toCheck) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Question> asUnmodifiableObservableList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Iterator<Question> iterator() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean equals(Object other) {
        throw new AssertionError("This method should not be called.");
    }
}
