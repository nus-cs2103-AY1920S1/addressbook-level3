package dream.fcard.gui.controllers.windows.editors;

import java.io.IOException;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            ap.getChildren().get(2).requestFocus();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
