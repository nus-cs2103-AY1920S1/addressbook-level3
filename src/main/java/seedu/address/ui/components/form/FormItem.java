package seedu.address.ui.components.form;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import seedu.address.ui.UiPart;

import java.util.function.Consumer;

/**
 * Abstraction of a javaFX form item, consisting of a label and a form control.
 * It is backed by a SplitPane, with the label on the left and item on the right.
 * Supports setters and getters for changing the content of the form item.
 */
public abstract class FormItem<T> extends UiPart<SplitPane> {
    final ObservableList<Node> splitPaneItems;

    /** Form field change handler that receives the form field value of type {@code T}. */
    private Consumer<T> executeChangeHandler;

    @FXML
    Label formItemLabel;

    FormItem(String FXML) {
        super(FXML);
        splitPaneItems = this.getRoot().getItems();
        splitPaneItems.clear();
        splitPaneItems.add(formItemLabel);
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

    public void setChangeHandler(Consumer<T> executeChangeHandler) {
        this.executeChangeHandler = executeChangeHandler;
    }

    /**
     * JavaFX handler to call when value of the form field changes.
     */
    @FXML
    public void handleChange() {
        //executeChangeHandler.accept(getValue());
    }
}
