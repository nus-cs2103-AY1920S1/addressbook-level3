package dream.fcard.gui.components;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.core.Main;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.util.datastructures.Pair;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class JsTestRunnerApplication extends Application {

    Consumer<Pair<Integer, Pair<Integer, Integer>>> sendResult;
    JavascriptCard card;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(Main.class.getResource("/view/Windows/JsTestEvaluator.fxml"));
            AnchorPane ap = fxmlloader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("FlashCoder JS");
            TextArea textArea = (TextArea) ap.getChildren().get(2);
            textArea.setText(boilerPlate());
            ap.getChildren().get(2).requestFocus();
            ((JsTestEvaluator) fxmlloader.getController()).setCodeReturner(sendResult);
            ((JsTestEvaluator) fxmlloader.getController()).setCard(card);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JsTestRunnerApplication(Consumer<Pair<Integer, Pair<Integer, Integer>>> sendResult, JavascriptCard c) {
        super();
        this.sendResult = sendResult;
        this.card = c;
    }

    /**
     * The boilerplate JavaScript code for the user when the editor is first loaded.
     *
     * @return basic code
     */
    private String boilerPlate() {
        return "//use print() to print text";
    }
}
