package com.dukeacademy.testutil;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Predicate;

import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * This is a non persistent implementation of QuestionsLogic for testing.
 */
public class MockQuestionsLogic implements QuestionsLogic {
    private ObservableList<Question> questions;
    private FilteredList<Question> filteredQuestions;
    private StandardObservable<Question> selectedQuestion;

    private MockQuestionsLogic() {

    }

    public static MockQuestionsLogic getMockQuestionsLogicWithTypicalQuestions() {
        MockQuestionsLogic logic = new MockQuestionsLogic();
        logic.questions = FXCollections.observableList(TypicalQuestions.getTypicalQuestions());
        logic.filteredQuestions = new FilteredList<>(logic.questions);
        logic.selectedQuestion = new StandardObservable<>();
        return logic;
    }

    @Override
    public ObservableList<Question> getAllQuestionsList() {
        return FXCollections.unmodifiableObservableList(this.questions);
    }

    @Override
    public ObservableList<Question> getFilteredQuestionsList() {
        return this.filteredQuestions;
    }

    @Override
    public void filterQuestionsList(Predicate<Question> predicate) {
        this.filteredQuestions.setPredicate(predicate);
    }

    @Override
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    @Override
    public void addQuestions(Collection<Question> questions) {
        this.questions.addAll(questions);
    }

    @Override
    public void addQuestionsFromPath(Path questionsFilePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Question getQuestion(int index) {
        return this.questions.stream().filter(question -> question.getId() == index)
                .findFirst().orElseThrow(QuestionNotFoundRuntimeException::new);
    }

    @Override
    public void setQuestion(int index, Question newQuestion) {
        Question oldQuestion = this.questions.stream().filter(question -> question.getId() == index)
                .findFirst().orElseThrow(QuestionNotFoundRuntimeException::new);
        this.replaceQuestion(oldQuestion, newQuestion);
    }

    @Override
    public void replaceQuestion(Question oldQuestion, Question newQuestion) {
        int index = this.questions.indexOf(oldQuestion);
        this.questions.set(index, newQuestion);
    }

    @Override
    public void deleteAllQuestions() {
        this.questions.clear();
    }

    @Override
    public Observable<Question> getSelectedQuestion() {
        return this.selectedQuestion;
    }

    @Override
    public void selectQuestion(int index) {
        Question selectedQuestion = this.filteredQuestions.get(index);
        this.selectedQuestion.setValue(selectedQuestion);
    }
}
