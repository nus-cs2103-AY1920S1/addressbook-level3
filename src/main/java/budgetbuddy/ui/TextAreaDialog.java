package budgetbuddy.ui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;

/**
 * A dialog that has a text area and an OK button.
 */
public class TextAreaDialog extends Dialog<String> {
    private final TextArea textArea;

    /**
     * Creates a new TextAreaDialog.
     */
    public TextAreaDialog() {
        final DialogPane dialogPane = getDialogPane();
        textArea = new TextArea();
        dialogPane.setContent(textArea);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        setResultConverter((dialogButton) -> textArea.getText());
    }
}
