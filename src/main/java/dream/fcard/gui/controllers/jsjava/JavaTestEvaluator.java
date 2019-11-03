package dream.fcard.gui.controllers.jsjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.model.TestCase;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.util.DeepCopy;
import dream.fcard.util.JavaTestCaseRunner;
import dream.fcard.util.datastructures.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * An association class between a JavaCard and a JavaTestCaseRunner
 */
public class JavaTestEvaluator {
    @FXML
    private TextArea textEditor;
    @FXML
    private Button runCodeButton;
    @FXML
    private TextArea consoleOutput;

    private Consumer<ArrayList<TestCase>> returnCode;
    private JavaTestCaseRunner runner;
    private JavaCard card;

    /**
     * Runs the code in the text editor in JavaScript and returns the output.
     *
     * @throws IOException if code cannot be compiled.
     */
    @FXML
    private void runCode() throws IOException {
        String code = textEditor.getText();
        ArrayList<TestCase> cases = DeepCopy.duplicateTestCases(card.getTestCases());

        for (TestCase c : cases) {
            runner = new JavaTestCaseRunner(c, code);
            String output = runner.testCode();
            c.setActualOutput(output);
        }

        returnCode.accept(cases); // send results back to the card
        setConsoleOutput(cases);
    }

    public void setCodeReturner(Consumer<ArrayList<TestCase>> returnResult) {
        this.returnCode = returnResult;
    }

    public void setCard(JavaCard c) {
        this.card = c;
    }

    private void setConsoleOutput(ArrayList<TestCase> cases) {
        // return results to user
        StringBuilder sb = new StringBuilder();
        for (TestCase testCase : cases) {
            Pair<Boolean, Pair<String, String>> difference = testCase.checkDiff(testCase.getActualOutput());
            if (difference.fst()) {
                sb.append("Pass")
                        .append("\n")
                        .append("____________________________")
                        .append("\n");
            } else {
                sb.append("Fail")
                        .append("\n")
                        .append("____________________________")
                        .append("\n")
                        .append("Expected: ")
                        .append("\n")
                        .append(difference.snd().fst())
                        .append("\n")
                        .append("____________________________")
                        .append("\n")
                        .append("Actual: ")
                        .append("\n")
                        .append(difference.snd().snd())
                        .append("\n");

            }
        }
        consoleOutput.setText(sb.toString());
    }
}
