package com.dukeacademy.logic.question;

import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class QuestionsLogicManager implements QuestionsLogic {
    private QuestionBank questionBank;
    private FilteredList<Question> filteredList;

    public QuestionsLogicManager(QuestionBank questionBank) {
        this.questionBank = questionBank;
        this.filteredList = new FilteredList<>(questionBank.getReadOnlyQuestionListObservable());
    }

    @Override
    public ObservableList<Question> getFilteredQuestionsList() {
        return FXCollections.unmodifiableObservableList(this.filteredList);
    }

    @Override
    public void filterQuestionsList(Predicate<Question> predicate) {
        this.filteredList.setPredicate(predicate);
    }

    @Override
    public Question getQuestion(int index) {
        return this.filteredList.get(index);
    }

    @Override
    public void setQuestion(int index, Question newQuestion) {
        this.questionBank.replaceQuestion(index, newQuestion);
    }

    @Override
    public void deleteQuestion(int index) {
        this.questionBank.removeQuestion(index);
    }

    @Override
    public void deleteAllQuestions() {
        this.questionBank.resetQuestions();
    }
}
