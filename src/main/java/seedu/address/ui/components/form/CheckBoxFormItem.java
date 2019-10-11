package seedu.address.ui.components.form;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

/**
 * A {@code FormItem} implemented using a checkbox control.
 */
public class CheckBoxFormItem extends FormItem<Boolean> {
    private static final String FXML = "components/forms/CheckBoxFormItem.fxml";

    @FXML
    private CheckBox formCheckBox;

    public CheckBoxFormItem(String textFormItemName, Boolean initialValue, Consumer<Boolean> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        formCheckBox.setSelected(initialValue);
    }

    public CheckBoxFormItem(String textFormItemName, Consumer<Boolean> executeChangeHandler) {
        this(textFormItemName, false, executeChangeHandler);
    }

    @Override
    public Boolean getValue() {
        return formCheckBox.isSelected();
    }

    @Override
    public void setValue(Boolean value) {
        formCheckBox.setSelected(value);
    }

    /**
     * JavaFX handler to call when value of the form field changes.
     */
    @FXML
    private void handleChange() {
        executeChangeHandler.accept(getValue());
    }
}
