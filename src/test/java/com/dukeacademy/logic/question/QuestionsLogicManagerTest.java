package com.dukeacademy.logic.question;

import com.dukeacademy.model.StandardQuestionBank;
import com.dukeacademy.model.program.UserProgram;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.question.Topic;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionsLogicManagerTest {
    private QuestionsLogicManager questionsLogicManager;
    private StandardQuestionBank questionBank;

    @BeforeEach
    void initializeTest() {
        questionBank = new StandardQuestionBank();
        questionsLogicManager = new QuestionsLogicManager(questionBank);
    }

    @Test
    void getFilteredQuestionsList() {
        // Ensure that the list in the question bank always corresponds to the list in the logic manager
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        assertEquals(0, questionsObservableList.size());

        List<Question> mockQuestionData = this.getMockQuestionData();
        questionBank.setQuestions(mockQuestionData);
        assertTrue(this.matchListData(questionsObservableList, mockQuestionData));

        Question newQuestion = this.getMockQuestion("Test4");
        questionBank.addQuestion(newQuestion);
        List<Question> newList = new ArrayList<>(questionBank.getReadOnlyQuestionListObservable());
        assertTrue(this.matchListData(questionsObservableList, newList));

        assertThrows(UnsupportedOperationException.class, () -> questionsObservableList
                .add(this.getMockQuestion("Test5")));
    }

    @Test
    void filterQuestionsList() {
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> mockQuestionData = this.getMockQuestionData();
        mockQuestionData.add(this.getMockQuestion("abc"));
        mockQuestionData.add(this.getMockQuestion("abc"));
        questionBank.setQuestions(mockQuestionData);

        // Assert that the filter works
        questionsLogicManager.filterQuestionsList(question -> question.getTitle().equals("abc"));
        assertEquals(2, questionsObservableList.size());
        assertTrue(questionsObservableList.stream().allMatch(question -> question.getTitle().equals("abc")));

        // Assert that the filter did not modify the original list data in the bank and in the logic manager
        assertTrue(this.matchListData(questionBank.getReadOnlyQuestionListObservable(), mockQuestionData));
        questionsLogicManager.filterQuestionsList(question -> true);
        assertTrue(this.matchListData(questionsObservableList, mockQuestionData));
    }

    @Test
    void getQuestion() {
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> mockQuestionData = this.getMockQuestionData();
        questionBank.setQuestions(mockQuestionData);

        Question question0 = questionsObservableList.get(0);
        Question question2 = questionsObservableList.get(2);

        assertEquals(questionsLogicManager.getQuestion(0), question0);
        assertEquals(question2, questionsLogicManager.getQuestion(2));
    }

    @Test
    void setQuestion() {
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> mockQuestionData = this.getMockQuestionData();
        questionBank.setQuestions(mockQuestionData);

        // Check the question is replaced both in the logic manager and in the question bank
        Question newQuestion = this.getMockQuestion("Test4");
        questionsLogicManager.setQuestion(1, newQuestion);
        mockQuestionData.remove(1);
        mockQuestionData.add(1, newQuestion);
        assertTrue(this.matchListData(questionsObservableList, mockQuestionData));
        assertTrue(this.matchListData(questionBank.getReadOnlyQuestionListObservable(), mockQuestionData));

        assertThrows(IndexOutOfBoundsException.class, () -> questionsLogicManager.setQuestion(100, newQuestion));
        assertThrows(IndexOutOfBoundsException.class, () -> questionsLogicManager.setQuestion(-1, newQuestion));
    }

    @Test
    void deleteQuestion() {
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> mockQuestionData = this.getMockQuestionData();
        questionBank.setQuestions(mockQuestionData);

        // Check that the questions are deleted in both the logic manager and in the question bank
        questionsLogicManager.deleteQuestion(1);
        mockQuestionData.remove(1);
        assertTrue(this.matchListData(questionsObservableList, mockQuestionData));
        assertTrue(this.matchListData(questionBank.getReadOnlyQuestionListObservable(), mockQuestionData));

        assertThrows(IndexOutOfBoundsException.class, () -> questionsLogicManager.deleteQuestion(100));
        assertThrows(IndexOutOfBoundsException.class, () -> questionsLogicManager.deleteQuestion(-1));
    }

    @Test
    void deleteAllQuestions() {
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> mockQuestionData = this.getMockQuestionData();
        questionBank.setQuestions(mockQuestionData);

        // Check that the questions are deleted in both the logic manager and in the question bank
        questionsLogicManager.deleteAllQuestions();
        assertEquals(0, questionsObservableList.size());
        assertEquals(0, questionBank.getReadOnlyQuestionListObservable().size());
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
        questions.add(this.getMockQuestion("Test1"));
        questions.add(this.getMockQuestion("Test2"));
        questions.add(this.getMockQuestion("Test3"));

        return questions;
    }

    private Question getMockQuestion(String name) {
        int random = (int) Math.round(Math.random());

        List<TestCase> testCases = new ArrayList<>();
        testCases.add(new TestCase("1", "1"));
        testCases.add(new TestCase("2", "2"));
        testCases.add(new TestCase("3", "3"));
        UserProgram userProgram = new UserProgram("Test", "public class Test { }");

        if (random == 0) {
            Set<Topic> topics = new HashSet<>();
            topics.add(Topic.TREE);
            topics.add(Topic.DYNAMIC_PROGRAMMING);

            return new Question(name, Status.NEW, Difficulty.HARD, topics, testCases, userProgram);
        } else {
            Set<Topic> topics = new HashSet<>();
            topics.add(Topic.LINKED_LIST);
            topics.add(Topic.RECURSION);

            return new Question(name, Status.ATTEMPTED, Difficulty.EASY, topics, testCases, userProgram);
        }
    }
}