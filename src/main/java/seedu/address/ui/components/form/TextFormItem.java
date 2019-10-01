package seedu.address.ui.components.form;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Abstraction of a text form item allowing the user to enter text.
 * Backed by JavaFX's {@code TextField} control.
 */
public class TextFormItem extends FormItem<String> {
    private static final String FXML = "TextFormItem.fxml";

    @FXML
    private TextField formTextField;

    public TextFormItem(String textFormItemName, String initialText) {
        super(FXML);
        formItemLabel.setText(textFormItemName);
        formTextField.setText(initialText);
        splitPaneItems.add(formTextField);
    }

    public TextFormItem(String textFormItemName) {
        this(textFormItemName, "");
    }

    @Override
    public String getValue() {
        return formTextField.getText();
    }

    @Override
    public void setValue(String value) {
        formTextField.setText(value);
    }
}
