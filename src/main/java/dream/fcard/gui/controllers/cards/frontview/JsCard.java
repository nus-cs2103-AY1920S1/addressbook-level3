package dream.fcard.gui.controllers.cards.frontview;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.jsjava.JsTestRunnerApplication;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.util.datastructures.Pair;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A front view of the JS Card.
 */
public class JsCard extends VBox {
    @FXML
    private Label questionTextLabel;
    @FXML
    private Button openCoderButton;

    private JavascriptCard card;
    private Application jsEditor;
    private Consumer<Pair<String, Pair<Integer, Integer>>> getResult = this::receiveResult;
    private Consumer<Boolean> launchJsCoder = bool -> {
        startCoding();
    };

    public JsCard(JavascriptCard card) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Cards/Front/JsCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.card = card;
            Consumers.addConsumer("LAUNCH_JS", launchJsCoder);
            questionTextLabel.setText(card.getFront());
            openCoderButton.setOnAction(e -> startCoding());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up the JsTestRunnerApplication.
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
     * A function that returns whether the user passed all test cases. used to tell the user via the flashcard.
     *
     * @param result user's attempted code, the number of passed and failed attempts.
     * @return
     */
    private void receiveResult(Pair<String, Pair<Integer, Integer>> result) {
        card.setAttempt(result.fst());
        String pass = "\nPassed!";
        String fail = "\nFailed.";
        String err = "\nCould not compile/run.";
        String front = card.getFront();
        front = front.replaceAll(pass, "").replaceAll(fail, "")
                .replaceAll(err, "").strip();
        if (result.snd().snd().equals(0)) {
            card.editFront(front + pass);
            questionTextLabel.setText(front + pass);
            Consumers.doTask("GET_SCORE", true);
        } else if (result.snd().fst() != -1) {
            card.editFront(front + fail);
            questionTextLabel.setText(front + fail);
            Consumers.doTask("GET_SCORE", false);
        } else {
            card.editFront(front + err);
            questionTextLabel.setText(front + err);
            Consumers.doTask("GET_SCORE", false);
        }
    }
}
