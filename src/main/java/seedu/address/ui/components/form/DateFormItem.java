package seedu.address.ui.components.form;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

/**
 * Abstraction of a form item allowing the user to choose a date.
 * Backed by JavaFx's {@code DatePicker} control.
 */
public class DateFormItem extends FormItem<LocalDate> {
    private static final String FXML = "DateFormItem.fxml";

    @FXML
    private DatePicker formDateField;

    public DateFormItem(String textFormItemName, LocalDate initialDate) {
        super(FXML);
        formItemLabel.setText(textFormItemName);
        formDateField.setValue(initialDate);
        splitPaneItems.add(formDateField);
    }

    public DateFormItem(String textFormItemName) {
        this(textFormItemName, LocalDate.now());
    }

    @Override
    public LocalDate getValue() {
        return formDateField.getValue();
    }

    @Override
    public void setValue(LocalDate value) {
        formDateField.setValue(value);
    }
}
