package dream.fcard.gui.controllers.jsjava;

import java.io.IOException;

import dream.fcard.core.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The application for the JavaScript Editor.
 */
public class JsEditorApplication extends Application {


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(Main.class.getResource("/view/Windows/JsEditor.fxml"));
            VBox vb = fxmlloader.load();
            Scene scene = new Scene(vb);
            stage.setScene(scene);
            stage.setTitle("FlashCoder JS");
            TextArea textArea = (TextArea) vb.getChildren().get(2);
            textArea.setText(boilerPlate());
            vb.getChildren().get(2).requestFocus();
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
        return "//use print() to print text";
    }

}
