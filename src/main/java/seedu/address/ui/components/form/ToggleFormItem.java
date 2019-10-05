package seedu.address.ui.components.form;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.util.function.Consumer;

public class ToggleFormItem extends FormItem<Boolean> {
    private static final String FXML = "components/forms/ToggleFormItem.fxml";

    @FXML
    private ToggleButton formToggle;

    public ToggleFormItem(String textFormItemName, Boolean initialValue, Consumer<Boolean> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        formToggle.setSelected(initialValue);
        formToggle.fire();
        splitPaneItems.add(formToggle);
    }

    public ToggleFormItem(String textFormItemName, Consumer<Boolean> executeChangeHandler) {
        this(textFormItemName, false, executeChangeHandler);
    }

    @Override
    public Boolean getValue() {
        return formToggle.isSelected();
    }

    @Override
    public void setValue(Boolean value) {
        formToggle.setSelected(value);
        formToggle.fire();
    }

    /**
     * JavaFX handler to call when value of the form field changes.
     */
    @FXML
    private void handleChange() {
        executeChangeHandler.accept(getValue());
    }
}
