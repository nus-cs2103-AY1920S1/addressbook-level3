package com.dukeacademy.testexecutor;

import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.solutions.TestCaseResult;
import com.dukeacademy.model.solutions.TestResult;
import com.dukeacademy.model.solutions.UserProgram;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.TestListener;
import com.dukeacademy.testexecutor.exceptions.ServiceInitializationException;
import com.dukeacademy.testexecutor.exceptions.ServiceNotInitializedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StandardTestExecutorServiceTest {
    @TempDir
    public static Path tempPath;


    private StandardTestExecutorService testExecutorService;

    @Order(1)
    @Test
    void initializeTestExecutorService() throws ServiceInitializationException {
        assertThrows(ServiceNotInitializedException.class, StandardTestExecutorService::getStandardTestExecutorService);
        StandardTestExecutorService.initializeTestExecutorService(tempPath.toUri().getPath());
    }

    @Order(2)
    @Test
    void getTestResultObservable() throws ServiceNotInitializedException {
        this.testExecutorService = StandardTestExecutorService.getStandardTestExecutorService();
        assertNotNull(testExecutorService.getTestResultObservable());
    }

    @Order(3)
    @Test
    void runTestCasesAgainstUserProgram() throws ServiceNotInitializedException, IOException {
        this.testExecutorService = StandardTestExecutorService.getStandardTestExecutorService();
        TestListener<TestResult> resultListener = new TestListener<>();
        this.testExecutorService.getTestResultObservable().addListener(resultListener);

        // Test for fib
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "fib");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        Path program = rootFolder.resolve("fib.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Fib", sourceCode);

        this.testExecutorService.runTestCasesAgainstUserProgram(testCases, userProgram);

        TestResult result = resultListener.getLatestValue();
        assertNotNull(result);
        assertFalse(result.getCompileError().isPresent());
        assertEquals(5, result.getNumPassed());
        assertTrue(this.matchTestCaseAndResults(testCases, result.getResults()));

        // Test for nested class
        Path rootFolder1 = Paths.get("src", "test", "data", "TestPrograms", "nested");
        List<TestCase> testCases1 = this.loadTestCases(rootFolder1);

        Path program1 = rootFolder1.resolve("nested.txt");
        String sourceCode1 = Files.readString(program1);
        UserProgram userProgram1 = new UserProgram("Nested", sourceCode1);

        this.testExecutorService.runTestCasesAgainstUserProgram(testCases1, userProgram1);

        TestResult result1 = resultListener.getLatestValue();
        assertNotNull(result1);
        assertFalse(result1.getCompileError().isPresent());
        assertEquals(5, result1.getNumPassed());
        assertTrue(this.matchTestCaseAndResults(testCases1, result1.getResults()));

        // Tests for errors
        TestCase mockTestCase = new TestCase("", "");
        List<TestCase> mockTestCases = new ArrayList<>();
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);

        // Test for compile error
        UserProgram program2 = new UserProgram("CompileError", "foobar");
        this.testExecutorService.runTestCasesAgainstUserProgram(mockTestCases, program2);
        TestResult result2 = resultListener.getLatestValue();
        assertTrue(result2.getCompileError().isPresent());

        // Test for runtime error
        Path programPath = Paths.get("src", "test", "data",
                "TestPrograms", "errors", "indexoutofbounds.txt");
        UserProgram program3 = new UserProgram("Main", Files.readString(programPath));

        this.testExecutorService.runTestCasesAgainstUserProgram(mockTestCases, program3);
        TestResult result3 = resultListener.getLatestValue();
        assertTrue(result3.getCompileError().isEmpty());
        assertEquals(0, result3.getNumPassed());

        IntStream.range(0, 3).forEach(index -> {
            TestCaseResult testCaseResult = result3.getResults().get(index);
            assertFalse(testCaseResult.isSuccessful());
            assertTrue(testCaseResult.getRuntimeError().isPresent());
        });
    }

    @Order(4)
    @Test
    void closeTestExecutorService() throws ServiceNotInitializedException {
        StandardTestExecutorService.closeTestExecutorService();
        assertThrows(ServiceNotInitializedException.class, StandardTestExecutorService::getStandardTestExecutorService);
        assertTrue(tempPath.toFile().exists());
        assertEquals(0, Objects.requireNonNull(tempPath.toFile().listFiles()).length);
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
                    TestCase testCase = testCases.get(index);
                    TestCaseResult result = results.get(index);

                    return result.getActualOutput().isPresent()
                            && testCase.getExpectedResult().equals(result.getActualOutput().get())
                            && testCase.getExpectedResult().equals(result.getExpectedOutput())
                            && result.isSuccessful();
                }).reduce((x, y) -> x && y)
                .orElse(false);
    }
}