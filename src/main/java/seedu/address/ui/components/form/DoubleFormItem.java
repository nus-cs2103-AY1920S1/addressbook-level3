package seedu.address.ui.components.form;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;

/**
 * Abstraction of a double form item allowing the user to enter double.
 * Backed by JavaFX's {@code Spinner} control.
 */
public class DoubleFormItem extends FormItem<Double> {
    private static final String FXML = "components/forms/DoubleFormItem.fxml";

    private static final double DEFAULT_SPINNER_VALUE = 0.0;
    private static final double MIN_SPINNER_VALUE = 0.0;
    private static final double MAX_SPINNER_VALUE = Double.MAX_VALUE;
    private static final double SPINNER_INCREMENT = 0.5;

    @FXML
    private Spinner<Double> formDoubleSpinner;

    public DoubleFormItem(String textFormItemName, double initialValue, Consumer<Double> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        formDoubleSpinner.setValueFactory(
                new DoubleSpinnerValueFactory(MIN_SPINNER_VALUE, MAX_SPINNER_VALUE, SPINNER_INCREMENT));
        formDoubleSpinner.getValueFactory().setValue(initialValue);

        /*
        Tie execute changeHandler to the value of the Spinner (for clicking up/down),
        and the textProperty of the underlying TextField supporting
        the spinner (if user types the value manually)
        */
        formDoubleSpinner.getEditor().textProperty().addListener((observableVal, oldVal, newVal) -> {
            try {
                Double.parseDouble(newVal);
                formDoubleSpinner.commitValue();
                //valueProperty listener will run after this
            } catch (NumberFormatException ex) {
                formDoubleSpinner.getEditor().setText(oldVal);
            }
        });

        formDoubleSpinner.valueProperty().addListener((observableVal, oldVal, newVal) -> {
            if (oldVal != newVal) {
                executeChangeHandler.accept(getValue());
            }
        });
    }

    public DoubleFormItem(String textFormItemName, Consumer<Double> executeChangeHandler) {
        this(textFormItemName, DEFAULT_SPINNER_VALUE, executeChangeHandler);
    }

    @Override
    public Double getValue() {
        return formDoubleSpinner.getValue();
    }

    @Override
    public void setValue(Double value) {
        formDoubleSpinner.getValueFactory().setValue(value);
    }
}
