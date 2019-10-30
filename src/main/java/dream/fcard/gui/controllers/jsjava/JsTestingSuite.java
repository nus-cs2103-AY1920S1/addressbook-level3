package dream.fcard.gui.components;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.util.datastructures.Pair;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A coordinator to take in a card, and start a popup window to test the user.
 */
public class JsTestingSuite {
    private JavascriptCard card;
    private Application jsEditor;
    private Consumer<Pair<Integer, Pair<Integer, Integer>>> getResult = r -> receiveResult(r);

    public JsTestingSuite(JavascriptCard card) {
        this.card = card;
    }

    /**
     * Opens the popup window.
     */
    private void startCoding() {
        final Stage stage = new Stage();
        stage.setTitle("Js Quiz Code Editor");
        try {
            jsEditor = new JsTestRunnerApplication(getResult, card);
            jsEditor.start(stage);
        } catch (IOException e) {
            System.err.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Once the user has run code, this method will trigger and receive the results.
     * @param result the number of test cases, and how many are correct or wrong.
     * @return a boolean denoting whether the user got the question completely correct or not.
     */
    public boolean receiveResult(Pair<Integer, Pair<Integer, Integer>> result) {
        //Need to wire this up to Shawn's waiting ans function and need to add a popup button on the card render
        if (result.fst().equals(result.snd().fst())) {
            return true;
        } else {
            return false;
        }
    }
}
