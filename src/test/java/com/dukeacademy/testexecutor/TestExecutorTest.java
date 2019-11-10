package com.dukeacademy.testexecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.model.program.TestCaseResult;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.testexecutor.compiler.Compiler;
import com.dukeacademy.testexecutor.compiler.StandardCompiler;
import com.dukeacademy.testexecutor.environment.CompilerEnvironment;
import com.dukeacademy.testexecutor.environment.StandardCompilerEnvironment;
import com.dukeacademy.testexecutor.environment.exceptions.CreateEnvironmentException;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;
import com.dukeacademy.testexecutor.exceptions.TestExecutorException;
import com.dukeacademy.testexecutor.executor.ProgramExecutor;
import com.dukeacademy.testexecutor.executor.StandardProgramExecutor;

class TestExecutorTest {
    @TempDir
    public Path tempFolder;
    private TestExecutor executor;

    /**
     * Reinitialize test entities.
     */
    @BeforeEach
    void initialize() throws CreateEnvironmentException {
        CompilerEnvironment environment = new StandardCompilerEnvironment(tempFolder.resolve("test"));
        Compiler compiler = new StandardCompiler();
        ProgramExecutor executor = new StandardProgramExecutor();

        this.executor = new TestExecutor(environment, compiler, executor, 20);
    }

    /**
     * Deletes any remnant files after each test.
     */
    @AfterEach
    void close() throws IOException {
        Files.walk(tempFolder)
                .filter(path -> !path.equals(tempFolder))
                .map(Path::toFile)
                .sorted(Comparator.reverseOrder())
                .forEach(File::delete);
    }

    /**
     * Run test cases against a simple program to calculate fibonacci.
     */
    @Test
    void runTestCasesFib() throws IOException, TestExecutorException,
            IncorrectCanonicalNameException, EmptyUserProgramException {
        // Load test cases
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "fib");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        // Load user program
        Path program = rootFolder.resolve("fib.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Fib", sourceCode);

        // Check results
        TestResult result = executor.runTestCases(testCases, userProgram);
        assertEquals(5, result.getNumPassed());
        assertTrue(result.getCompileError().isEmpty());

        List<TestCaseResult> testCaseResults = result.getResults();
        assertEquals(testCases.size(), testCaseResults.size());
        assertTrue(matchTestCaseAndResults(testCases, testCaseResults));
    }

    /**
     * Run test cases against a simple program to check for duplicates.
     */
    @Test
    void runTestCasesDuplicates() throws IOException, TestExecutorException,
            IncorrectCanonicalNameException, EmptyUserProgramException {
        // Load test cases
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "duplicates");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        // Load program
        Path program = rootFolder.resolve("duplicates.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Duplicates", sourceCode);

        // Check results
        TestResult result = executor.runTestCases(testCases, userProgram);
        assertEquals(5, result.getNumPassed());
        assertTrue(result.getCompileError().isEmpty());

        List<TestCaseResult> testCaseResults = result.getResults();
        assertEquals(testCases.size(), testCaseResults.size());
        assertTrue(matchTestCaseAndResults(testCases, testCaseResults));
    }

    /**
     * Runs test cases against a program that relies on a nested class.
     */
    @Test
    void runTestCaseNestedClass() throws IOException, TestExecutorException,
            IncorrectCanonicalNameException, EmptyUserProgramException {
        // Load test cases
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "nested");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        // Load program
        Path program = rootFolder.resolve("nested.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Nested", sourceCode);

        // Check results
        TestResult result = executor.runTestCases(testCases, userProgram);
        assertEquals(5, result.getNumPassed());
        assertTrue(result.getCompileError().isEmpty());

        List<TestCaseResult> testCaseResults = result.getResults();
        assertEquals(testCases.size(), testCaseResults.size());
        assertTrue(matchTestCaseAndResults(testCases, testCaseResults));
    }

    /**
     * Runs test cases against a program that is packaged.
     */
    @Test
    void runTestCasePackaged() throws IOException, EmptyUserProgramException, TestExecutorException,
            IncorrectCanonicalNameException {
        // Load test cases
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "packaged");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        // Load program
        Path program = rootFolder.resolve("packaged.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("packaged.inside.Packaged", sourceCode);

        // Check results
        TestResult result = executor.runTestCases(testCases, userProgram);
        assertEquals(5, result.getNumPassed());
        assertTrue(result.getCompileError().isEmpty());

        List<TestCaseResult> testCaseResults = result.getResults();
        assertEquals(testCases.size(), testCaseResults.size());
        assertTrue(matchTestCaseAndResults(testCases, testCaseResults));
    }

    /**
     * Runs test cases against a program that will give a wrong answer.
     */
    @Test
    void runIncorrectProgram() throws IOException, TestExecutorException,
            IncorrectCanonicalNameException, EmptyUserProgramException {
        // Load test cases
        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "incorrect");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        // Load program
        Path program = rootFolder.resolve("incorrect.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Incorrect", sourceCode);

        // Check results
        TestResult result = executor.runTestCases(testCases, userProgram);
        assertFalse(result.getCompileError().isPresent());
        assertEquals(0, result.getNumPassed());

        result.getResults().stream().forEach(testCaseResult -> {
            assertFalse(testCaseResult.isSuccessful());
            assertTrue(testCaseResult.getActualOutput().isPresent());
            assertEquals("Correct solution" + System.lineSeparator(), testCaseResult.getExpectedOutput());
            assertEquals("Wrong solution" + System.lineSeparator(), testCaseResult.getActualOutput().get());
        });
    }

    /**
     * Runs test cases against programs that will result in a compile error.
     */
    @Test
    void testCompileError() throws TestExecutorException, IncorrectCanonicalNameException,
            EmptyUserProgramException {
        // Compile error due to unrecognized symbol
        UserProgram program = new UserProgram("CompileError", "public class CompileError {\n"
                + "Scanner sc;\n"
                + "}");
        TestResult result = executor.runTestCases(new ArrayList<>(), program);
        assertTrue(result.getCompileError().isPresent());

        // Compile error due to incorrect use of keyword
        UserProgram program1 = new UserProgram("CompileError", "public class CompileError {\n"
                + "package abc = 123;\n"
                + "}");
        TestResult result1 = executor.runTestCases(new ArrayList<>(), program1);
        assertTrue(result1.getCompileError().isPresent());
    }

    /**
     * Run test cases against programs that will result in a runtime error.
     */
    @Test
    void testRuntimeError() throws IOException, TestExecutorException,
            IncorrectCanonicalNameException, EmptyUserProgramException {
        // Load test cases - index out of bounds program
        Path programPath = Paths.get("src", "test", "data",
                "TestPrograms", "errors", "indexoutofbounds.txt");
        UserProgram program = new UserProgram("Main", Files.readString(programPath));

        // Mock empty test cases
        TestCase mockTestCase = new TestCase("", "");
        List<TestCase> mockTestCases = new ArrayList<>();
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);

        // Check results
        TestResult results = executor.runTestCases(mockTestCases, program);
        assertTrue(results.getCompileError().isEmpty());
        assertEquals(0, results.getNumPassed());

        IntStream.range(0, 3).forEach(index -> {
            TestCaseResult result = results.getResults().get(index);
            assertFalse(result.isSuccessful());
            assertTrue(result.getRuntimeError().isPresent());
        });
    }

    /**
     * Tests that an empty program will throw an error
     */
    @Test
    void testEmptyProgramException() {
        assertThrows(EmptyUserProgramException.class, () -> executor
                .runTestCases(new ArrayList<>(), new UserProgram("Main", "")));
    }

    /**
     * Tests that a program with a non-matching canonical name will throw an error
     */
    @Test
    void testIncorrectClassNameException() {
        UserProgram invalidProgram = new UserProgram("Main", "public class Man {}");
        assertThrows(IncorrectCanonicalNameException.class, () -> executor
                .runTestCases(new ArrayList<>(), invalidProgram));

        UserProgram invalidProgram1 = new UserProgram("Main", "public class Test "
                + "{ public class Main { } }");
        assertThrows(IncorrectCanonicalNameException.class, () -> executor
                .runTestCases(new ArrayList<>(), invalidProgram1));

        UserProgram invalidProgram2 = new UserProgram("Main", "package FooBar;\n"
                + "public class Main {}");
        assertThrows(IncorrectCanonicalNameException.class, () -> executor
                .runTestCases(new ArrayList<>(), invalidProgram2));
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
}
