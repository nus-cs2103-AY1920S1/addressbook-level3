package dream.fcard.gui.controllers.displays.createandeditdeck.javacard;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * A controller for a row of test case input boxes when creating a JavaCard.
 */
public class JavaTestCaseRow extends HBox {
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private Button addRowButton;

    public JavaTestCaseRow(Consumer<Boolean> addNewRow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/JavaTestCaseRow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            addRowButton.setOnAction(e -> addNewRow.accept(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
