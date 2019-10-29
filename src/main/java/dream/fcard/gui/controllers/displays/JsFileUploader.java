package dream.fcard.gui.controllers.displays;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A file uploader for Js card creating.
 */
public class JsFileUploader extends ScrollPane {
    @FXML
    private Button uploadFileButton;
    @FXML
    private Label filenameLabel;

    private File file;

    public JsFileUploader() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/JsFileUploader.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            uploadFileButton.setOnAction(e -> {
                file = getFile();
                filenameLabel.setText(file.getName());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private File getFile() {
        final Stage dialog = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JS File", "*.js")
        );
        return fileChooser.showOpenDialog(dialog);
    }

    public boolean hasFile() {
        return file != null;
    }

    public String getAssertions() throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }
}
