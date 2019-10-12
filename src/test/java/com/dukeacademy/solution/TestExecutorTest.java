package com.dukeacademy.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.model.solution.TestCase;
import com.dukeacademy.model.solution.TestCaseResult;
import com.dukeacademy.model.solution.TestExecutorResult;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.compiler.Compiler;
import com.dukeacademy.solution.compiler.StandardCompiler;
import com.dukeacademy.solution.environment.CompilerEnvironment;
import com.dukeacademy.solution.environment.StandardCompilerEnvironment;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.TestExecutorException;
import com.dukeacademy.solution.program.ProgramExecutor;
import com.dukeacademy.solution.program.StandardProgramExecutor;

class TestExecutorTest {
    @TempDir
    public Path tempFolder;
    private TestExecutor executor;

    @BeforeEach
    public void initialize() throws CompilerEnvironmentException {
        CompilerEnvironment environment = new StandardCompilerEnvironment(tempFolder.resolve("test").toUri().getPath());
        Compiler compiler = new StandardCompiler();
        ProgramExecutor executor = new StandardProgramExecutor();

        this.executor = new TestExecutor(environment, compiler, executor);
    }

    @Test
    public void runTestCasesFib() throws IOException, TestExecutorException {
        System.out.println("Running fib program against test cases...\n");

        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "fib");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        Path program = rootFolder.resolve("fib.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Fib", sourceCode);

        TestExecutorResult result = executor.runTestCases(testCases, userProgram);
        assertEquals(5, result.getNumPassed());
        assertTrue(result.getCompileError().isEmpty());

        List<TestCaseResult> testCaseResults = result.getResults();
        assertEquals(testCases.size(), testCaseResults.size());
        assertTrue(matchTestCaseAndResults(testCases, testCaseResults));
    }

    @Test
    public void runTestCasesDuplicates() throws IOException, TestExecutorException {
        System.out.println("Running duplicates program against test cases...\n");

        Path rootFolder = Paths.get("src", "test", "data", "TestPrograms", "duplicates");
        List<TestCase> testCases = this.loadTestCases(rootFolder);

        Path program = rootFolder.resolve("duplicates.txt");
        String sourceCode = Files.readString(program);
        UserProgram userProgram = new UserProgram("Duplicates", sourceCode);

        TestExecutorResult result = executor.runTestCases(testCases, userProgram);
        assertEquals(5, result.getNumPassed());
        assertTrue(result.getCompileError().isEmpty());

        List<TestCaseResult> testCaseResults = result.getResults();
        assertEquals(testCases.size(), testCaseResults.size());
        assertTrue(matchTestCaseAndResults(testCases, testCaseResults));
    }

    @Test
    public void testCompileError() throws TestExecutorException {
        System.out.println("Running programs that should throw compile error...\n");

        System.out.println("End of file error:");
        UserProgram program = new UserProgram("CompileError", "foobar");
        TestExecutorResult result = executor.runTestCases(new ArrayList<>(), program);
        System.out.println("Compile error detected: " + result.getCompileError().isPresent());
        result.getCompileError().ifPresent(message -> System.out.println("Error: " + message.getErrorMessage()));
        assertTrue(result.getCompileError().isPresent());

        System.out.println("\nFile name match error:");
        UserProgram program1 = new UserProgram("CompileError", "public class CompilerErrors { }");
        TestExecutorResult result1 = executor.runTestCases(new ArrayList<>(), program1);
        System.out.println("Compile error detected: " + result1.getCompileError().isPresent());
        result1.getCompileError().ifPresent(message -> System.out.println("Error: " + message.getErrorMessage()));
        assertTrue(result1.getCompileError().isPresent());

        System.out.println();
    }

    @Test
    public void testRuntimeError() throws IOException, TestExecutorException {
        System.out.println("Running programs that should throw runtime error...\n");

        System.out.println("IndexOutOfBoundsException:\n");
        Path programPath = Paths.get("src", "test", "data",
                "TestPrograms", "errors", "indexoutofbounds.txt");
        UserProgram program = new UserProgram("Main" , Files.readString(programPath));


        TestCase mockTestCase = new TestCase("", "");
        List<TestCase> mockTestCases = new ArrayList<>();
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);
        mockTestCases.add(mockTestCase);

        TestExecutorResult results = executor.runTestCases(mockTestCases, program);
        assertTrue(results.getCompileError().isEmpty());
        assertEquals(0, results.getNumPassed());

        IntStream.range(0, 3).forEach(index -> {
            TestCaseResult result = results.getResults().get(index);
            assertFalse(result.isSuccessful());
            assertTrue(result.getRuntimeError().isPresent());

            System.out.println("Error for test case " + index + ":");
            System.out.println(result.getRuntimeError().get().getErrorMessage());
            System.out.println();
        });
    }

    /**
     * Load test cases from a root folder. Each test case is generated from an input text file following
     * the naming convention "test1.txt", "test2.txt", etc and its corresponding expected value text file
     * following the naming convention "expected1.txt", "expected2.txt", etc.
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
     * @param testCases the list of test cases to be checked against.
     * @param results the list of results to be checked.
     * @return true if all the results match the criteria.
     */
    private boolean matchTestCaseAndResults(List<TestCase> testCases, List<TestCaseResult> results) {
        return IntStream.range(0, testCases.size())
                .mapToObj(index -> {
                    TestCase testCase = testCases.get(index);
                    TestCaseResult result = results.get(index);

                    System.out.println("Comparing test case " + index + ":\n");
                    System.out.println(testCase);
                    System.out.println(result);

                    return testCase.getExpectedResult().equals(result.getActualOutput())
                            && testCase.getExpectedResult().equals(result.getExpectedOutput())
                            && result.isSuccessful();
                }).reduce((x, y) -> x && y)
                .orElse(false);
    }
}
