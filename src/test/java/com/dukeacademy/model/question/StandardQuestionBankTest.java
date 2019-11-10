package com.dukeacademy.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;
import com.dukeacademy.testutil.TypicalQuestions;

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
        mockQuestions.add(this.getMockQuestion("abc"));
        this.standardQuestionBank.setQuestions(mockQuestions);
        assertTrue(this.matchListData(questionObservableList, mockQuestions));

        mockQuestions.clear();
        this.standardQuestionBank.setQuestions(mockQuestions);
        assertTrue(this.matchListData(questionObservableList, mockQuestions));

        assertThrows(UnsupportedOperationException.class, () -> questionObservableList
                .add(this.getMockQuestion("abc")));
    }

    @Test
    void addQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);

        Question newQuestion = this.getMockQuestion("abc");
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
    void addQuestionBank() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        this.standardQuestionBank.setQuestions(new ArrayList<>());
        assertTrue(this.matchListData(questionObservableList, new ArrayList<>()));

        QuestionBank questionBank = TypicalQuestions.getTypicalQuestionBank();
        this.standardQuestionBank.addQuestionBank(questionBank);
        assertTrue(this.matchListData(questionObservableList, questionBank.getReadOnlyQuestionListObservable()));
    }

    @Test
    void replaceQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);

        List<Question> originalBankList = new ArrayList<>(questionObservableList);

        Question newQuestion = this.getMockQuestion("abc");
        this.standardQuestionBank.replaceQuestion(1, newQuestion);
        originalBankList.remove(1);
        originalBankList.add(1, newQuestion);
        assertTrue(this.matchListData(questionObservableList, originalBankList));
    }


    @Test
    void replaceQuestionWithOldQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);
        Question oldQuestion = questionObservableList.get(1);

        Question newQuestion = this.getMockQuestion("123");
        this.standardQuestionBank.replaceQuestion(oldQuestion, newQuestion);

        mockQuestions.remove(1);
        mockQuestions.add(1, newQuestion);
        assertTrue(this.matchListData(questionObservableList, mockQuestions));

        Question testQuestion = this.getMockQuestion("abc");
        assertThrows(QuestionNotFoundRuntimeException.class, () -> this.standardQuestionBank
                .replaceQuestion(testQuestion, testQuestion));
    }

    @Test
    void removeQuestion() {
        ObservableList<Question> questionObservableList = standardQuestionBank.getReadOnlyQuestionListObservable();
        List<Question> mockQuestions = this.getMockQuestionData();
        this.standardQuestionBank.setQuestions(mockQuestions);

        List<Question> originalBankList = new ArrayList<>(questionObservableList);
        Question questionToRemove = originalBankList.get(1);
        originalBankList.remove(questionToRemove);
        this.standardQuestionBank.removeQuestion(questionToRemove);
        assertTrue(this.matchListData(questionObservableList, originalBankList));
    }

    @Test
    void removeQuestionByIndex() {
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

    /**
     * Helper method to check the equality of questions in an observable list and a list.
     * @param observableList the observable list to be checked.
     * @param questionList the list to be checked.
     * @return true if the questions are equal.
     */
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
        String description = "description";

        if (random == 0) {
            Set<Topic> topics = new HashSet<>();
            topics.add(Topic.TREE);
            topics.add(Topic.DYNAMIC_PROGRAMMING);

            return new Question(name, Status.NEW, Difficulty.HARD, topics,
                testCases, userProgram, true, description);
        } else {
            Set<Topic> topics = new HashSet<>();
            topics.add(Topic.LINKED_LIST);
            topics.add(Topic.RECURSION);

            return new Question(name, Status.ATTEMPTED, Difficulty.EASY,
                topics, testCases, userProgram, false, description);
        }
    }
}
