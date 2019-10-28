package dream.fcard.model;

import java.io.File;
import java.io.FileNotFoundException;

import dream.fcard.util.FileReadWrite;
import dream.fcard.util.datastructures.Pair;

public class TestCase {
    File input;
    File expectedOutput;

    public TestCase(File input, File expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    public Pair<Boolean, Pair<String, String>> checkDiff(String output) throws FileNotFoundException {
        String expected = FileReadWrite.read(expectedOutput.getAbsolutePath());
        Pair<String, String> outputs = new Pair<>(expected, output);
        Pair<Boolean, Pair<String, String>> obj;
        if (!expected.equals(output)) {
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

    public File getInput() {
        return input;
    }

    public File getExpectedOutput() {
        return expectedOutput;
    }
}
