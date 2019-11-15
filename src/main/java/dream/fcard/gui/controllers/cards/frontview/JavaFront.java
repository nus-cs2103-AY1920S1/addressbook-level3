package dream.fcard.gui.controllers.cards.frontview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.jsjava.JavaTestRunnerApplication;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.TestCase;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.util.datastructures.Pair;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A UI element for the JavaCard
 */
public class JavaFront extends VBox {
    @FXML
    private Label questionTextLabel;
    @FXML
    private Button openCoderButton;

    private JavaCard card;
    private Application javaEditor;
    private Consumer<Pair<String, ArrayList<TestCase>>> getResult = this::receiveResult;
    private Consumer<Boolean> launchJavaCoder = bool -> {
        startCoding();
    };

    public JavaFront(JavaCard card) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Cards/Front/JavaFront.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.card = card;
            Consumers.addConsumer("LAUNCH_JAVA", launchJavaCoder);
            questionTextLabel.setText(card.getFront());
            openCoderButton.setOnAction(e -> startCoding());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up the JavaTestRunnerApplication.
     */
    private void startCoding() {
        final Stage stage = new Stage();
        stage.setTitle("Java Quiz Code Editor");
        try {
            javaEditor = new JavaTestRunnerApplication(getResult, card);
            javaEditor.start(stage);
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
    private void receiveResult(Pair<String, ArrayList<TestCase>> result) {
        int failed = 0;
        boolean compileWrong = false;
        card.setAttempt(result.fst());
        ArrayList<TestCase> cases = result.snd();
        for (TestCase tc : cases) {
            Pair<Boolean, Pair<String, String>> difference = tc.checkDiff(tc.getActualOutput());
            if (!difference.fst()) {
                failed++;
            }
            if (difference.snd().snd() == null) {
                compileWrong = true;
                break;
            }
        }
        String pass = "\nPassed!";
        String fail = "\nFailed.";
        String err = "\nCould not compile/run.";
        String front = card.getFront();
        front = front.replaceAll(pass, "").replaceAll(fail, "")
                .replaceAll(err, "").strip();

        if (failed == 0) {
            card.editFront(front + pass);
            questionTextLabel.setText(front + pass);
            Consumers.doTask("GET_SCORE", true);
        } else if (compileWrong) {
            card.editFront(front + err);
            questionTextLabel.setText(front + err);
            Consumers.doTask("GET_SCORE", false);
        } else {
            card.editFront(front + fail);
            questionTextLabel.setText(front + fail);
            Consumers.doTask("GET_SCORE", false);
        }
    }
}
