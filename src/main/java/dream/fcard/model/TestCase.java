package dream.fcard.model;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.datastructures.Pair;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * A testcase object to remember 1 input file and 1 output file. used by JavaCard.
 */
public class TestCase implements JsonInterface {
    private String input;
    private String expectedOutput;
    private String actualOutput;

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
        expectedOutput = expectedOutput.strip();
        output = output.strip();
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

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public String getActualOutput() {
        return actualOutput;
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

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put(Schema.TEST_CASE_INPUT, input);
        obj.put(Schema.TEST_CASE_OUTPUT, expectedOutput);
        return new JsonValue(obj);
    }
}
