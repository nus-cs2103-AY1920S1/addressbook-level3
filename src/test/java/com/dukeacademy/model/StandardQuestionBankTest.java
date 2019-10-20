package com.dukeacademy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;
import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.Question;

import javafx.collections.ObservableList;

public class StandardQuestionBankTest {

    private final StandardQuestionBank standardQuestionBank = new StandardQuestionBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), standardQuestionBank.getReadOnlyQuestionListObservable());

        List<Question> mockQuestions = this.getMockQuestionData();
        StandardQuestionBank testBank = new StandardQuestionBank(mockQuestions);
        assertTrue(this.matchListData(testBank.getReadOnlyQuestionListObservable(), mockQuestions));

        StandardQuestionBank testBank2 = new StandardQuestionBank(testBank);
        assertTrue(this.matchListData(testBank2.getReadOnlyQuestionListObservable(), mockQuestions));
    }


    @Test
    void getReadOnlyQuestionListObservable() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();

        List<Question> mockQuestions = this.getMockQuestionData();
        mockQuestions.add(new Question(new Title("test1"), new Topic("test1"), new Status("test1"), new Difficulty("Test1"), new HashSet<Tag>()));
        this.standardQuestionBank.setQuestions(mockQuestions);
        assertTrue(this.matchListData(questionObservableList, mockQuestions));

        mockQuestions.clear();
        this.standardQuestionBank.setQuestions(mockQuestions);
        assertTrue(this.matchListData(questionObservableList, mockQuestions));

        assertThrows(UnsupportedOperationException.class, () -> questionObservableList
                .add(new Question(new Title("test1"), new Topic("test1"), new Status("test1"), new Difficulty("Test1"), new HashSet<Tag>())));
    }

    @Test
    void addQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);

        Question newQuestion = new Question(new Title("test1"), new Topic("test1"), new Status("test1"), new Difficulty("Test1"), new HashSet<Tag>());
        mockQuestions.add(newQuestion);
        this.standardQuestionBank.addQuestion(newQuestion);

        assertTrue(this.matchListData(questionObservableList, mockQuestions));
    }

    @Test
    void setQuestions() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        this.standardQuestionBank.setQuestions(new ArrayList<>());
        assertTrue(this.matchListData(questionObservableList, new ArrayList<>()));

        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);
        assertTrue(this.matchListData(questionObservableList, mockQuestions));
    }

    @Test
    void replaceQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);

        List<Question> originalBankList = new ArrayList<>(questionObservableList);

        Question newQuestion = new Question(new Title("test1"), new Topic("test1"), new Status("test1"), new Difficulty("Test1"), new HashSet<Tag>());
        this.standardQuestionBank.replaceQuestion(1, newQuestion);
        originalBankList.remove(1);
        originalBankList.add(1, newQuestion);
        assertTrue(this.matchListData(questionObservableList, originalBankList));
    }

    @Test
    void removeQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);

        List<Question> originalBankList = new ArrayList<>(questionObservableList);
        this.standardQuestionBank.removeQuestion(1);
        originalBankList.remove(1);
        assertTrue(this.matchListData(questionObservableList, originalBankList));
    }

    @Test
    void resetQuestions() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);
        this.standardQuestionBank.resetQuestions();

        assertEquals(0, questionObservableList.size());
    }

    private boolean matchListData(ObservableList<Question> observableList, List<Question> questionList) {
        if (observableList.size() != questionList.size()) {
            return false;
        }

        if (observableList.size() == 0) {
            return true;
        }

        return IntStream.range(0, observableList.size())
                .mapToObj(i -> observableList.get(i).equals(questionList.get(i)))
                .reduce((x, y) -> x && y).get();
    }

    private List<Question> getMockQuestionData() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(new Title("test"), new Topic("test"), new Status("test"), new Difficulty("Test"), new HashSet<Tag>()));
        questions.add(new Question(new Title("test"), new Topic("test"), new Status("test"), new Difficulty("Test"), new HashSet<Tag>()));

        return questions;
    }
}
