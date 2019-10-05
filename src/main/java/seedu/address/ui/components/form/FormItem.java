package seedu.address.ui.components.form;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import seedu.address.ui.UiPart;

import java.util.function.Consumer;

/**
 * Abstraction of a javaFX form item, consisting of a label and a form control.
 * It is backed by a SplitPane, with the label on the left and item on the right.
 * Supports setters and getters for changing the content of the form item.
 * Requires all implementing classes to set the executeChangeHandler, to be executed when
 * the value of the form field changes.
 */
public abstract class FormItem<T> extends UiPart<AnchorPane> {

    /** Form field change handler that receives the form field value of type {@code T}. */
    Consumer<T> executeChangeHandler;

    @FXML
    Label formItemLabel;

    FormItem(String FXML, Consumer<T> executeChangeHandler) {
        super(FXML);
        this.executeChangeHandler = executeChangeHandler;
    }

    /**
     * Retrieves the underlying value of the field.
     *
     * @return T value of the form item field.
     */
    public abstract T getValue();

    /**
     * Sets the underlying value of the field.
     */
    public abstract void setValue(T value);
}
