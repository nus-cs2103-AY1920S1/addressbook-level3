package dream.fcard.gui.controllers.jsjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.core.Main;
import dream.fcard.model.TestCase;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.util.datastructures.Pair;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A popup window that allows the user to enter JS code for the flashcard in the test.
 */
public class JavaTestRunnerApplication extends Application {

    private Consumer<Pair<String, ArrayList<TestCase>>> sendResult;
    private JavaCard card;

    public JavaTestRunnerApplication(Consumer<Pair<String, ArrayList<TestCase>>> sendResult, JavaCard c) {
        super();
        this.sendResult = sendResult;
        this.card = c;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(Main.class
                    .getResource("/view/Windows/JavaTestEvaluator.fxml"));
            VBox vb = fxmlloader.load();
            Scene scene = new Scene(vb);
            stage.setScene(scene);
            stage.getIcons().add(new Image(JavaTestRunnerApplication.class.getResourceAsStream(
                "/images/icon_black_resized.png")));
            stage.setTitle("FlashCard Pro: FlashCoder Java");
            TextArea textArea = (TextArea) vb.getChildren().get(2);
            textArea.setText(boilerPlate());
            vb.getChildren().get(2).requestFocus();
            JavaTestEvaluator evaluator = fxmlloader.getController();
            evaluator.setCodeReturner(sendResult);
            evaluator.setCard(card);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * The boilerplate JavaScript code for the user when the editor is first loaded.
     *
     * @return basic code
     */
    private String boilerPlate() {
        if (card.getAttempt() == null) {
            return "import java.util.Scanner;\n"
                    + "\n"
                    + "public class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        //Write your code here\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "\n"
                    + "    }\n"
                    + "}\n";
        } else {
            return card.getAttempt();
        }
    }
}
