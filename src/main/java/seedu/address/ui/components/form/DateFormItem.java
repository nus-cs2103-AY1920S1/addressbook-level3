package seedu.address.ui.components.form;

import java.time.LocalDate;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

/**
 * Abstraction of a form item allowing the user to choose a date.
 * Backed by JavaFx's {@code DatePicker} control.
 */
public class DateFormItem extends FormItem<LocalDate> {
    private static final String FXML = "components/forms/DateFormItem.fxml";

    @FXML
    private DatePicker formDateField;

    public DateFormItem(String textFormItemName, LocalDate initialDate, Consumer<LocalDate> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        formDateField.setValue(initialDate);
    }

    public DateFormItem(String textFormItemName, Consumer<LocalDate> executeChangeHandler) {
        this(textFormItemName, null, executeChangeHandler);
    }

    @Override
    public LocalDate getValue() {
        return formDateField.getValue();
    }

    @Override
    public void setValue(LocalDate value) {
        formDateField.setValue(value);
    }

    /**
     * JavaFX handler to call when value of the form field changes.
     */
    @FXML
    private void handleChange() {
        executeChangeHandler.accept(getValue());
    }
}
