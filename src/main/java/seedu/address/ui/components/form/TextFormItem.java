package seedu.address.ui.components.form;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Abstraction of a text form item allowing the user to enter text.
 * Backed by JavaFX's {@code TextField} control.
 */
public class TextFormItem extends FormItem<String> {
    private static final String FXML = "components/forms/TextFormItem.fxml";

    @FXML
    private TextField formTextField;

    public TextFormItem(String textFormItemName, String initialText, Consumer<String> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        formTextField.setText(initialText);
        //tie execute changeHandler to focusedProperty
        formTextField.focusedProperty().addListener((observableVal, oldVal, newVal) -> {
            if (!newVal) {
                executeChangeHandler.accept(getValue());
            }
        });
    }

    public TextFormItem(String textFormItemName, Consumer<String> executeChangeHandler) {
        this(textFormItemName, "", executeChangeHandler);
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
