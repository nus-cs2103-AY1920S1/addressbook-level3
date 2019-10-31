package dream.fcard.model;

import dream.fcard.util.datastructures.Pair;

/**
 * A testcase object to remember 1 input file and 1 output file. used by JavaCard.
 */
public class TestCase {
    private String input;
    private String expectedOutput;

    public TestCase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    /**
     * A method to check if the given output matches expected output in the Test Case..
     * @param output the actual output from running the program.
     * @return a boolean specifying pass/fail, and the actual and expected output for comparison.
     */
    public Pair<Boolean, Pair<String, String>> checkDiff(String output) {
        Pair<String, String> outputs = new Pair<>(expectedOutput, output);
        Pair<Boolean, Pair<String, String>> obj;
        if (!expectedOutput.equals(output)) {
            obj = new Pair<>(false, outputs);
        } else {
            obj = new Pair<>(true, outputs);
        }
        return obj;
    }

    public boolean hasMissingInput() {
        return input == null;
    }

    public boolean hasMissingExpectedOutput() {
        return expectedOutput == null;
    }

    public String getInput() {
        return input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    /**
     * A method to clone this object.
     * @return A duplicate TestCase.
     */
    public TestCase duplicate() {
        String in = input;
        String out = expectedOutput;
        return new TestCase(in, out);
    }
}
