package dream.fcard.gui.controllers.displays.createandeditdeck.jscard;

import java.io.IOException;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * A file uploader for Js card creating.
 */
public class JsTestCaseInputTextArea extends ScrollPane {
    @FXML
    private TextArea testCaseTextArea;

    public JsTestCaseInputTextArea() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/JsTestCaseInputTextArea.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean hasTestCase() {
        return !getAssertions().isBlank();
    }

    public String getAssertions() {
        return testCaseTextArea.getText();
    }

    public void setTextArea(String text) {
        text = text.replaceAll(";assert", ";" + "\n" + "assert");
        testCaseTextArea.setText(text);
    }
}
