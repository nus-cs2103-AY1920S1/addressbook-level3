package dream.fcard.util;

import java.util.Scanner;

import dream.fcard.util.code.JavascriptRunner;
import dream.fcard.util.datastructures.Pair;

/**
 * This class takes in a Java file, an ArrayList of test cases, and runs the input against each test case.
 */
public class JsTestCaseRunner {
    private String expectedOutput;
    private String userAttempt;
    private String consoleDisplay;

    public JsTestCaseRunner(String codeToRun, String expectedOutput) {
        this.expectedOutput = expectedOutput;
        this.userAttempt = codeToRun;
    }

    public String getConsoleDisplay() {
        return consoleDisplay;
    }



    /**
     * Runs the user's attempt at writing a function against the assertions that the user has written.
     *
     * @return (TotalTestCases, ( TotalCorrect, TotalWrong)) in a Pair object.
     */
    public Pair<String, Pair<Integer, Integer>> testCode() {
        String finalCode = processjs(userAttempt, expectedOutput);
        String output = JavascriptRunner.evaluateString(finalCode);
        consoleDisplay = output;
        output = output.replaceAll("pass", "")
                .replaceAll("fail", "")
                .strip();
        System.out.println(output);
        Scanner sc = new Scanner(output);
        int correct = sc.nextInt();
        int wrong = sc.nextInt();
        return new Pair<>(userAttempt, new Pair<>(correct, wrong));
    }

    /**
     * Inserts testing functionality around the user's code. Used to check correctness of user's code.
     *
     * @param userInput      the user's testing code.
     * @param expectedOutput the user's assertions.
     * @return the final piece of code that the evaluator can use to score the user.
     */
    private static String processjs(String userInput, String expectedOutput) {
        StringBuilder sb = new StringBuilder();
        sb.append("var correct = 0;\n"
                + "var wrong = 0;\n"
                + "\n"
                + "function assert(actual, expected) {\n"
                + "    if (actual == expected) {\n"
                + "        correct++;"
                + "        print('pass');\n"
                + "    } else {\n"
                + "        wrong++;\n"
                + "        print('fail');\n"
                + "    }\n"
                + "}\n");
        sb.append(userInput).append("\n");
        sb.append(expectedOutput).append("\n");
        sb.append("print(correct, wrong);\n");
        return sb.toString();
    }

}
