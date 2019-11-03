package dream.fcard.gui.controllers.cards.frontview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.jsjava.JavaTestRunnerApplication;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.model.TestCase;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.util.datastructures.Pair;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A UI element for the JavaCard
 */
public class JavaFront extends AnchorPane {
    @FXML
    private Label questionTextLabel;
    @FXML
    private Button openCoderButton;

    private JavaCard card;
    private Application javaEditor;
    private Consumer<ArrayList<TestCase>> getResult = this::receiveResult;
    private Consumer<String> updateUserAttempt;
    private Consumer<Boolean> getScore;

    public JavaFront(JavaCard card, Consumer<String> updateUserAttempt, Consumer<Boolean> getScore) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Cards/Front/JavaFront.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.card = card;
            this.getScore = getScore;
            this.updateUserAttempt = updateUserAttempt;
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
    private void receiveResult(ArrayList<TestCase> result) {
        int failed = 0;
        boolean compileWrong = false;
        for (TestCase tc : result) {
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
        } else if (compileWrong) {
            card.editFront(front + err);
            questionTextLabel.setText(front + err);
        } else {
            card.editFront(front + fail);
            questionTextLabel.setText(front + fail);
        }
    }
}
