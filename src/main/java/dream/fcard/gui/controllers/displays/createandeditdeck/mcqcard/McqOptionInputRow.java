package dream.fcard.gui.controllers.displays.createandeditdeck.mcqcard;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * The UI element to represent one multiple choice option. Housed inside the McqOptionsSetter.
 */
public class McqOptionInputRow extends HBox {
    @FXML
    private Label optionValue;
    @FXML
    private TextField optionText;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addNewRowButton;
    @FXML
    private RadioButton rightAnswerRadio;

    public McqOptionInputRow(ToggleGroup rightAnswer, Consumer<McqOptionInputRow> deleteRow,
                             Consumer<Boolean> addNewRow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays"
                    + "/MCQOptionInputRow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            deleteButton.setOnAction(e -> deleteRow.accept(this));
            addNewRowButton.setOnAction(e->addNewRow.accept(true));
            rightAnswerRadio.setToggleGroup(rightAnswer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOptionNumber(int i) {
        optionValue.setText(i + ".");
    }

    public boolean isBlank() {
        return optionText.getText().isBlank();
    }

    public boolean hasRightAnswer() {
        return rightAnswerRadio.isSelected();
    }

    public String getOption() {
        return optionText.getText();
    }

    public void setOptionText(String text) {
        optionText.setText(text);
    }

    public void setRightAnswerRadio() {
        rightAnswerRadio.setSelected(true);
    }
}
