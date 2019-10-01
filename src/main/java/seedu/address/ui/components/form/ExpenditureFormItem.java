package seedu.address.ui.components.form;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.address.model.itinerary.Expenditure;

/**
 * Abstraction of a expenditure form item allowing the user to enter expenditure.
 * Backed by JavaFX's {@code TextField} control.
 */
public class ExpenditureFormItem extends FormItem<Expenditure> {
    private static final String FXML = "ExpenditureFormItem.fxml";

    @FXML
    private TextField formExpenditureField;

    public ExpenditureFormItem(String textFormItemName, Expenditure initialAmount) {
        super(FXML);
        formItemLabel.setText(textFormItemName);
        formExpenditureField.setText(initialAmount.toString());
        splitPaneItems.add(formExpenditureField);
    }

    public ExpenditureFormItem(String textFormItemName) {
        this(textFormItemName, new Expenditure(0.0));
    }

    @Override
    public Expenditure getValue() {
        return new Expenditure(formExpenditureField.getText());
    }

    @Override
    public void setValue(Expenditure value) {
        formExpenditureField.setText(value.toString());
    }
}
