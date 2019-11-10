package com.dukeacademy.logic.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;
import com.dukeacademy.storage.question.JsonQuestionBankStorage;
import com.dukeacademy.storage.question.QuestionBankStorage;
import com.dukeacademy.testutil.TypicalQuestions;

import javafx.collections.ObservableList;

class QuestionsLogicManagerTest {
    @TempDir
    public Path tempFolder;

    private Path typicalQuestionBankPath;
    private Path emptyQuestionBankPath;


    /**
     * Creates a typical question bank Json file and an empty question bank Json file for testing.
     */
    @BeforeEach
    void initializeTest() throws IOException {
        typicalQuestionBankPath = tempFolder.resolve("typical.json");
        Files.createFile(typicalQuestionBankPath);

        String typicalQuestionData = Files.readString(Paths.get("src", "test", "data",
                "JsonSerializableQuestionBankTest", "typicalQuestionQuestionBank.json"));
        Files.writeString(typicalQuestionBankPath, typicalQuestionData);

        emptyQuestionBankPath = tempFolder.resolve("empty.json");
        Files.createFile(emptyQuestionBankPath);
        Files.writeString(emptyQuestionBankPath, "{ \"questions\" : []}");
    }

    /**
     * Deletes the created files.
     */
    @AfterEach
    void closeTest() throws IOException {
        Files.delete(typicalQuestionBankPath);
        Files.delete(emptyQuestionBankPath);
    }

    @Test
    void getFilteredQuestionsList() {
        // Ensure that the list in the question bank always corresponds to the list in the logic manager

        // Load empty question bank
        QuestionBankStorage storage = new JsonQuestionBankStorage(emptyQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        assertEquals(0, questionsObservableList.size());

        // Load typical questions
        QuestionBankStorage storage1 = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager1 = new QuestionsLogicManager(storage1);

        ObservableList<Question> questionsObservableList1 = questionsLogicManager1.getFilteredQuestionsList();
        assertTrue(this.matchListData(questionsObservableList1, TypicalQuestions.getTypicalQuestions()));

        // Assert that the list returned is read-only
        assertThrows(UnsupportedOperationException.class, () -> questionsObservableList
                .add(this.getMockQuestion("Test5")));
    }

    @Test
    void getAllQuestionsList() {
        // Ensure that the list in the question bank always corresponds to the list in the logic manager

        // Load empty question bank
        QuestionBankStorage storage = new JsonQuestionBankStorage(emptyQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getAllQuestionsList();
        assertEquals(0, questionsObservableList.size());

        // Load typical questions
        QuestionBankStorage storage1 = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager1 = new QuestionsLogicManager(storage1);

        ObservableList<Question> questionsObservableList1 = questionsLogicManager1.getFilteredQuestionsList();
        assertTrue(this.matchListData(questionsObservableList1, TypicalQuestions.getTypicalQuestions()));

        // Assert that the list returned is read-only
        assertThrows(UnsupportedOperationException.class, () -> questionsObservableList
                .add(this.getMockQuestion("Test5")));
    }

    @Test
    void filterQuestionsList() throws DataConversionException, IOException {
        // Load typical questions
        QuestionBankStorage storage = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> typicalQuestions = TypicalQuestions.getTypicalQuestions();
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));

        // Assert that the filter works
        questionsLogicManager.filterQuestionsList(question -> question.getTitle().equals("Valid Sudoku"));
        assertEquals(1, questionsObservableList.size());
        assertTrue(questionsObservableList.stream().allMatch(question -> question.getTitle().equals("Valid Sudoku")));

        // Assert that the filter did not modify the original list data in the bank and in the logic manager
        ObservableList<Question> storageQuestions = storage.readQuestionBank().get()
                .getReadOnlyQuestionListObservable();
        assertTrue(this.matchListData(storageQuestions, TypicalQuestions.getTypicalQuestions()));
        questionsLogicManager.filterQuestionsList(question -> true);
        assertTrue(this.matchListData(questionsObservableList, TypicalQuestions.getTypicalQuestions()));

    }

    @Test
    void getQuestion() {
        // Load typical questions
        QuestionBankStorage storage = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> typicalQuestions = TypicalQuestions.getTypicalQuestions();
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));

        Question question0 = questionsObservableList.get(0);
        Question question2 = questionsObservableList.get(2);

        assertTrue(typicalQuestions.get(0).checkContentsEqual(question0));
        assertTrue(typicalQuestions.get(2).checkContentsEqual(question2));
    }

    @Test
    void addQuestion() throws IOException, DataConversionException {
        // Load empty question bank
        QuestionBankStorage storage = new JsonQuestionBankStorage(emptyQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        assertEquals(0, questionsObservableList.size());

        // Verify added question is reflected in the logic manager and the storage
        Question newQuestion = this.getMockQuestion("abc");
        questionsLogicManager.addQuestion(newQuestion);
        assertTrue(this.matchListData(questionsObservableList, List.of(newQuestion)));
        ObservableList<Question> storageQuestions = storage.readQuestionBank().get()
                .getReadOnlyQuestionListObservable();
        assertTrue(this.matchListData(storageQuestions, List.of(newQuestion)));
    }

    @Test
    void addQuestions() throws IOException, DataConversionException {
        // Load empty question bank
        QuestionBankStorage storage = new JsonQuestionBankStorage(emptyQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        assertEquals(0, questionsObservableList.size());

        // Verify added questions are reflected in the logic manager and the storage
        List<Question> mockQuestions = this.getMockQuestionData();
        questionsLogicManager.addQuestions(mockQuestions);
        assertTrue(this.matchListData(questionsObservableList, mockQuestions));
        ObservableList<Question> storageQuestions = storage.readQuestionBank().get()
                .getReadOnlyQuestionListObservable();
        assertTrue(this.matchListData(storageQuestions, mockQuestions));
    }

    @Test
    void addQuestionsFromPath() throws IOException, DataConversionException {
        // Load empty question bank
        QuestionBankStorage storage = new JsonQuestionBankStorage(emptyQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        assertEquals(0, questionsObservableList.size());

        // Verify that original data is untouched if file path is invalid.
        questionsLogicManager.addQuestionsFromPath(Paths.get("a!3@"));
        assertEquals(0, questionsObservableList.size());
        ObservableList<Question> storageQuestions = storage.readQuestionBank().get()
                .getReadOnlyQuestionListObservable();
        assertEquals(0, storageQuestions.size());

        // Verify added questions are reflected in the logic manager and the storage
        questionsLogicManager.addQuestionsFromPath(typicalQuestionBankPath);
        List<Question> expectedQuestions = TypicalQuestions.getTypicalQuestions();
        assertTrue(this.matchListData(questionsObservableList, expectedQuestions));
        ObservableList<Question> storageQuestions1 = storage.readQuestionBank().get()
                .getReadOnlyQuestionListObservable();
        assertTrue(this.matchListData(storageQuestions1, expectedQuestions));

    }

    @Test
    void setQuestion() throws IOException, DataConversionException {
        // Load typical questions
        QuestionBankStorage storage = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> typicalQuestions = TypicalQuestions.getTypicalQuestions();
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));

        // Check the question is replaced both in the logic manager and in the storage
        Question newQuestion = this.getMockQuestion("Test4");
        Question oldQuestion = questionsObservableList.get(1);
        questionsLogicManager.setQuestion(oldQuestion.getId(), newQuestion);
        typicalQuestions.remove(1);
        typicalQuestions.add(1, newQuestion);
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));
        ObservableList<Question> storageQuestions = storage.readQuestionBank()
                .get().getReadOnlyQuestionListObservable();
        assertTrue(this.matchListData(storageQuestions, typicalQuestions));

        assertThrows(QuestionNotFoundRuntimeException.class, () -> questionsLogicManager.setQuestion(100, newQuestion));
        assertThrows(QuestionNotFoundRuntimeException.class, () -> questionsLogicManager.setQuestion(-1, newQuestion));
    }

    @Test
    void deleteAllQuestions() throws IOException, DataConversionException {
        // Load typical questions
        QuestionBankStorage storage = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> typicalQuestions = TypicalQuestions.getTypicalQuestions();
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));

        // Check that the questions are deleted in both the logic manager and in the question bank
        questionsLogicManager.deleteAllQuestions();
        assertEquals(0, questionsObservableList.size());
        ObservableList<Question> storageQuestions = storage.readQuestionBank().get()
                .getReadOnlyQuestionListObservable();
        assertEquals(0, storageQuestions.size());
    }


    @Test
    void replaceQuestion() {
        // Load typical questions
        QuestionBankStorage storage = new JsonQuestionBankStorage(typicalQuestionBankPath);
        QuestionsLogicManager questionsLogicManager = new QuestionsLogicManager(storage);
        ObservableList<Question> questionsObservableList = questionsLogicManager.getFilteredQuestionsList();
        List<Question> typicalQuestions = TypicalQuestions.getTypicalQuestions();
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));

        // Check that question replaced in both the logic manager and in the question bank
        Question oldQuestion = questionsObservableList.get(1);
        Question newQuestion = this.getMockQuestion("abc");
        questionsLogicManager.replaceQuestion(oldQuestion, newQuestion);
        typicalQuestions.remove(1);
        typicalQuestions.add(1, newQuestion);
        assertTrue(this.matchListData(questionsObservableList, typicalQuestions));
    }

    /**
     * Helper method to compare an observable list to a list for equality.
     * @param observableList the observable list to be compared.
     * @param questionList the question list to be compared.
     * @return true if both lists are equal.
     */
    private boolean matchListData(ObservableList<Question> observableList, List<Question> questionList) {
        if (observableList.size() != questionList.size()) {
            return false;
        }

        if (observableList.size() == 0) {
            return true;
        }

        return IntStream.range(0, observableList.size())
                .mapToObj(i -> observableList.get(i).checkContentsEqual(questionList.get(i)))
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
                topics, testCases, userProgram, true, description);
        }
    }

    @Test
    void getSelectedQuestion() {
        // TODO
    }

    @Test
    void selectQuestion() {
        // TODO
    }
}
