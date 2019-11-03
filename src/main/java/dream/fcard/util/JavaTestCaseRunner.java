package dream.fcard.util;

import java.io.IOException;

import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.TestCase;
import dream.fcard.util.code.JavaRunner;

/**
 * This class takes in a Java file, an ArrayList of test cases, and runs the input against each test case.
 */
public class JavaTestCaseRunner {
    private TestCase testCase;
    private String userAttempt;
    private String consoleDisplay;

    public JavaTestCaseRunner(TestCase testCase, String userAttempt) {
        this.testCase = testCase;
        this.userAttempt = userAttempt;
    }

    /**
     * Runs the user's attempt at writing a function against the test cases that the user has written.
     *
     * @return (TotalTestCases, ( TotalCorrect, TotalWrong)) in a Pair object.
     */
    public String testCode() {
        String filename = "Main.java";
        String input = "input.txt";
        StorageManager.writeCode(filename, userAttempt); //write user code to file
        StorageManager.writeCode(input, testCase.getInput()); //write input to file
        try {
            String compile = JavaRunner.compile(StorageManager.getCodePath(filename));
            if (compile.isBlank()) {
                String output = JavaRunner.runJavaWithRedirection(StorageManager.getCodePath(filename),
                        StorageManager.getCodePath(input));
                return output;
            } else {
                return compile; //compile error
            }
        } catch (IOException e) {
            return null; //either running or compiling failed.
        }
    }


}
