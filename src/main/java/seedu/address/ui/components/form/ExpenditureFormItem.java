package seedu.address.ui.components.form;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.address.model.itinerary.Expenditure;

import java.util.function.Consumer;

/**
 * Abstraction of a expenditure form item allowing the user to enter expenditure.
 * Backed by JavaFX's {@code TextField} control.
 */
public class ExpenditureFormItem extends FormItem<Expenditure> {
    private static final String FXML = "components/forms/ExpenditureFormItem.fxml";

    @FXML
    private TextField formExpenditureField;

    public ExpenditureFormItem(String textFormItemName, Expenditure initialAmount, Consumer<Expenditure> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        formExpenditureField.setText(initialAmount.toString());
        //tie execute changeHandler to focusedProperty
        formExpenditureField.focusedProperty().addListener((observableVal, oldVal, newVal) -> {
            if (!newVal) {
                executeChangeHandler.accept(getValue());
            }
        });
    }

    public ExpenditureFormItem(String textFormItemName, Consumer<Expenditure> executeChangeHandler) {
        this(textFormItemName, new Expenditure(0.0), executeChangeHandler);
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
