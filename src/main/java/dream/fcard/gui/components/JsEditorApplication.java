package dream.fcard.gui.components;

import java.io.IOException;

import dream.fcard.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The application for the JavaScript Editor.
 */
public class JsEditorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(MainWindow.class.getResource("/view/Windows/JsEditor.fxml"));
            AnchorPane ap = fxmlloader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("FlashCoder JS");
            TextArea textArea = (TextArea) ap.getChildren().get(2);
            textArea.setText(boilerPlate());
            ap.getChildren().get(2).requestFocus();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * The boilerplate JavaScript code for the user when the editor is first loaded.
     * @return basic class code
     */
    private String boilerPlate() {
        return "//use print() to print text";
    }
}
