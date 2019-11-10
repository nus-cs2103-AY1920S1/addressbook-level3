package com.dukeacademy.logic.program;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.logic.program.exceptions.NoQuestionSetException;
import com.dukeacademy.logic.program.exceptions.SubmissionLogicManagerClosedException;
import com.dukeacademy.model.program.TestCaseResult;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;
import com.dukeacademy.testutil.TestListener;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProgramSubmissionLogicManagerTest {
    @TempDir
    public static Path tempPath;


    private ProgramSubmissionLogicManager programSubmissionLogicManager;

    @BeforeEach
    void initializeTest() throws LogicCreationException {
        this.programSubmissionLogicManager = new ProgramSubmissionLogicManager(tempPath.toString());
    }

    @AfterEach
    void closeTest() {
        this.programSubmissionLogicManager.closeProgramSubmissionLogicManager();
    }

    @Test
    void exceptionsTest() {
        // Test no question set
        UserProgram mockProgram = new UserProgram("Test", "test");
        assertThrows(NoQuestionSetException.class, () -> this.programSubmissionLogicManager
                .submitUserProgram(mockProgram));

        // Test invalid directory
        assertThrows(LogicCreationException.class, () -> new
                ProgramSubmissionLogicManager("ra$123sdg!#z"));
    }

    @Test
    void submitUserProgram() throws IOException, IncorrectCanonicalNameException, EmptyUserProgramException {
        TestListener<TestResult> resultListener = new TestListener<>();
        this.programSubmissionLogicManager.getTestResultObservable().addListener(resultListener);

        // Test for fib
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "fib");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        Path program = rootFolder.resolve("fib.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Fib", sourceCode);

        Question question = this.createMockQuestion("Fib", testCases);
        this.programSubmissionLogicManager.setCurrentQuestion(question);

        Optional<TestResult> fibResultOptional = this.programSubmissionLogicManager.submitUserProgram(userProgram);
        assertTrue(fibResultOptional.isPresent());

        // Check that the same result is propagated to the observable
        TestResult fibResult = resultListener.getLatestValue();
        assertNotNull(fibResult);
        assertEquals(fibResultOptional.get(), fibResult);

        // Check value of result
        assertFalse(fibResult.getCompileError().isPresent());
        assertEquals(5, fibResult.getNumPassed());
        assertTrue(this.matchTestCaseAndResults(testCases, fibResult.getResults()));

        // Test for nested class
        Path rootFolder1 = Paths.get("src", "test", "data", "TestPrograms", "nested");
        List<TestCase> testCases1 = this.loadTestCases(rootFolder1);

        Path program1 = rootFolder1.resolve("nested.txt");
        String sourceCode1 = Files.readString(program1);
        UserProgram userProgram1 = new UserProgram("Nested", sourceCode1);

        Question question1 = this.createMockQuestion("Nested", testCases1);
        this.programSubmissionLogicManager.setCurrentQuestion(question1);
        Optional<TestResult> nestedResultOptional = this.programSubmissionLogicManager.submitUserProgram(userProgram1);
        assertTrue(nestedResultOptional.isPresent());

        //Check that the same result is propagated to the observable
        TestResult nestedResult = resultListener.getLatestValue();
        assertNotNull(nestedResult);
        assertEquals(nestedResultOptional.get(), nestedResult);

        // Check contents of result
        assertFalse(nestedResult.getCompileError().isPresent());
        assertEquals(5, nestedResult.getNumPassed());
        assertTrue(this.matchTestCaseAndResults(testCases1, nestedResult.getResults()));

        // Tests for errors
        TestCase mockTestCase = new TestCase("", "");
        List<TestCase> mockTestCases = new ArrayList<>();
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);

        // Test for compile error
        UserProgram program2 = new UserProgram("CompileError", "public class CompileError {\n"
                + "int a = \"Not an int\";\n"
                + "}");
        Question question2 = this.createMockQuestion("CompileError", mockTestCases);
        this.programSubmissionLogicManager.setCurrentQuestion(question2);
        this.programSubmissionLogicManager.submitUserProgram(program2);

        TestResult result2 = resultListener.getLatestValue();
        assertTrue(result2.getCompileError().isPresent());

        // Test for runtime error
        Path programPath = Paths.get("src", "test", "data",
                "TestPrograms", "errors", "indexoutofbounds.txt");
        UserProgram program3 = new UserProgram("Main", Files.readString(programPath));

        this.programSubmissionLogicManager.submitUserProgram(program3);
        TestResult result3 = resultListener.getLatestValue();
        assertTrue(result3.getCompileError().isEmpty());
        assertEquals(0, result3.getNumPassed());

        IntStream.range(0, 3).forEach(index -> {
            TestCaseResult testCaseResult = result3.getResults().get(index);
            assertFalse(testCaseResult.isSuccessful());
            assertTrue(testCaseResult.getRuntimeError().isPresent());
        });
    }

    @Test
    void closeProgramSubmissionLogicManager() throws LogicCreationException {
        this.programSubmissionLogicManager.closeProgramSubmissionLogicManager();
        assertThrows(SubmissionLogicManagerClosedException.class,
                this.programSubmissionLogicManager::closeProgramSubmissionLogicManager);
        assertThrows(SubmissionLogicManagerClosedException.class, () -> this.programSubmissionLogicManager
                .setCurrentQuestion(null));
        assertThrows(SubmissionLogicManagerClosedException.class, () -> this.programSubmissionLogicManager
                .getCurrentQuestionObservable());
        assertThrows(SubmissionLogicManagerClosedException.class, () -> this.programSubmissionLogicManager
                .getTestResultObservable());
        assertThrows(SubmissionLogicManagerClosedException.class, () -> this.programSubmissionLogicManager
                .submitUserProgram(null));

        this.programSubmissionLogicManager = new ProgramSubmissionLogicManager(tempPath.toString());
    }

    @Test
    void getAndSetCurrentQuestion() {
        Observable<Question> currentQuestionObservable =
                this.programSubmissionLogicManager.getCurrentQuestionObservable();
        TestListener<Question> testListener = new TestListener<>();
        currentQuestionObservable.addListener(testListener);

        assertNull(testListener.getLatestValue());
        assertFalse(this.programSubmissionLogicManager.getCurrentQuestion().isPresent());

        this.programSubmissionLogicManager.setCurrentQuestion(this.createMockQuestion("abc123",
                new ArrayList<>()));
        assertEquals("abc123", testListener.getLatestValue().getTitle());
        assertTrue(this.programSubmissionLogicManager.getCurrentQuestion().isPresent());
        assertEquals("abc123", this.programSubmissionLogicManager.getCurrentQuestion().get().getTitle());
    }


    /**
     * Load test cases from a root folder. Each test case is generated from an input text file following
     * the naming convention "test1.txt", "test2.txt", etc and its corresponding expected value text file
     * following the naming convention "expected1.txt", "expected2.txt", etc.
     *
     * @param rootFolder the path to the root folder that contains the text files.
     * @return a list of corresponding test cases.
     * @throws IOException if the files cannot be found or read.
     */
    private List<TestCase> loadTestCases(Path rootFolder) throws IOException {
        String test1 = Files.readString(rootFolder.resolve("test1.txt"));
        String test2 = Files.readString(rootFolder.resolve("test2.txt"));
        String test3 = Files.readString(rootFolder.resolve("test3.txt"));
        String test4 = Files.readString(rootFolder.resolve("test4.txt"));
        String test5 = Files.readString(rootFolder.resolve("test5.txt"));

        String expected1 = Files.readString(rootFolder.resolve("expected1.txt"));
        String expected2 = Files.readString(rootFolder.resolve("expected2.txt"));
        String expected3 = Files.readString(rootFolder.resolve("expected3.txt"));
        String expected4 = Files.readString(rootFolder.resolve("expected4.txt"));
        String expected5 = Files.readString(rootFolder.resolve("expected5.txt"));

        List<TestCase> testCases = new ArrayList<>();
        testCases.add(new TestCase(test1, expected1));
        testCases.add(new TestCase(test2, expected2));
        testCases.add(new TestCase(test3, expected3));
        testCases.add(new TestCase(test4, expected4));
        testCases.add(new TestCase(test5, expected5));

        return testCases;
    }

    /**
     * Compares a list of test cases and results sequentially and checks that each result is successful and that
     * its actual and expected outputs matches those specified in the test case.
     *
     * @param testCases the list of test cases to be checked against.
     * @param results   the list of results to be checked.
     * @return true if all the results match the criteria.
     */
    private boolean matchTestCaseAndResults(List<TestCase> testCases, List<TestCaseResult> results) {
        return IntStream.range(0, testCases.size())
                .mapToObj(index -> {
                    TestCaseResult result = results.get(index);

                    if (result.getActualOutput().isEmpty() || !result.isSuccessful()) {
                        return false;
                    }

                    return testCases.stream()
                            .anyMatch(testCase -> testCase.getExpectedResult().equals(result.getActualOutput().get())
                                    && testCase.getExpectedResult().equals(result.getExpectedOutput()));
                }).reduce((x, y) -> x && y)
                .orElse(false);
    }

    /**
     * Creates a mock question for testing.
     *
     * @param title     the name of the question.
     * @param testCases the test cases of the question.
     * @return the created question.
     */
    private Question createMockQuestion(String title, List<TestCase> testCases) {
        Status status = Status.ATTEMPTED;
        Difficulty difficulty = Difficulty.HARD;
        Set<Topic> topics = new HashSet<>();
        boolean isBookmarked = true;
        String description = "description";
        return new Question(title, status, difficulty, topics, testCases,
                new UserProgram("Main", ""), true, description);
    }

    @Test
    void setAndSubmitUserProgramSubmissionChannelAndGetProgram() throws IOException, IncorrectCanonicalNameException,
            EmptyUserProgramException {
        TestListener<TestResult> resultListener = new TestListener<>();
        this.programSubmissionLogicManager.getTestResultObservable().addListener(resultListener);
        FibMockUserProgramChannel channel = new FibMockUserProgramChannel();
        this.programSubmissionLogicManager.setUserProgramSubmissionChannel(channel);

        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "fib");

        Path program = rootFolder.resolve("fib.txt");
        String sourceCode = Files.readString(program);
        assertEquals(new UserProgram("Fib", sourceCode),
                this.programSubmissionLogicManager.getUserProgramFromSubmissionChannel());

        List<TestCase> testCases = this.loadTestCases(rootFolder);
        Question question = this.createMockQuestion("Fib", testCases);
        this.programSubmissionLogicManager.setCurrentQuestion(question);

        Optional<TestResult> testResultOptional = this.programSubmissionLogicManager
                .submitUserProgramFromSubmissionChannel();
        assertTrue(testResultOptional.isPresent());

        // Check that result is propagated to observable
        TestResult testResult = resultListener.getLatestValue();
        assertNotNull(testResult);
        assertEquals(testResultOptional.get(), testResult);

        // Check values
        assertFalse(testResult.getCompileError().isPresent());
        assertEquals(5, testResult.getNumPassed());
        assertTrue(this.matchTestCaseAndResults(testCases, testResult.getResults()));
    }

    @Test
    void testSubmitEmptyProgram() {
        UserProgram emptyProgram = new UserProgram("Main", "");
        assertThrows(EmptyUserProgramException.class, () -> this.programSubmissionLogicManager
                .submitUserProgram(emptyProgram));
    }

    @Test
    void testProgramSubmissionFailure() {
        // TODO
    }
}
