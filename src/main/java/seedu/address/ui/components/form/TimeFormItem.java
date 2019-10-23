package seedu.address.ui.components.form;

import java.time.LocalTime;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Abstraction of a form item allowing the user to choose a time in a day.
 * Backed by JavaFx's {@code Spinner} control.
 */
public class TimeFormItem extends FormItem<LocalTime> {
    private static final String FXML = "components/forms/TimeFormItem.fxml";

    private static final double DEFAULT_SPINNER_VALUE = 0.0;
    private static final double MIN_SPINNER_VALUE = 0.0;
    private static final double MAX_HOUR_SPINNER_VALUE = 23.0;
    private static final double MAX_MINUTE_SPINNER_VALUE = 59.0;
    private static final double SPINNER_INCREMENT = 1;

    @FXML
    private Spinner<Double> hourSpinner;

    @FXML
    private Spinner<Double> minuteSpinner;

    public TimeFormItem(String textFormItemName, Double hours, Double minutes,
             Consumer<LocalTime> executeChangeHandler) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(textFormItemName);
        //initializing hour spinner
        hourSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(MIN_SPINNER_VALUE,
                         MAX_HOUR_SPINNER_VALUE, SPINNER_INCREMENT)
        );
        hourSpinner.getValueFactory().setValue(hours);

        //initializing minute spinner
        minuteSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(MIN_SPINNER_VALUE,
                         MAX_MINUTE_SPINNER_VALUE, SPINNER_INCREMENT)
        );
        minuteSpinner.getValueFactory().setValue(minutes);


        hourSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Double.parseDouble(newValue);
                hourSpinner.commitValue();
            } catch (NumberFormatException e) {
                hourSpinner.getEditor().setText(oldValue);
            }
        });

        minuteSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Double.parseDouble(newValue);
                hourSpinner.commitValue();
            } catch (NumberFormatException e) {
                hourSpinner.getEditor().setText(oldValue);
            }
        });

        hourSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                executeChangeHandler.accept(getValue());
            }
        }));
        minuteSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                executeChangeHandler.accept(getValue());
            }
        }));
    }

    public TimeFormItem(String textFormItemName, Consumer<LocalTime> executeChangeHandler) {
        this(textFormItemName, DEFAULT_SPINNER_VALUE, DEFAULT_SPINNER_VALUE, executeChangeHandler);
    }

    @Override
    public LocalTime getValue() {
        return LocalTime.of(hourSpinner.getValue().intValue(), minuteSpinner.getValue().intValue());
    }

    @Override
    public void setValue(LocalTime value) {
        hourSpinner.getValueFactory().setValue((double) value.getHour());
        minuteSpinner.getValueFactory().setValue((double) value.getMinute());
    }
}
