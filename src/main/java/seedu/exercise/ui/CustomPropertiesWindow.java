package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;

/**
 * UI component to display all previously defined custom properties.
 */
public class CustomPropertiesWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(CustomPropertiesWindow.class);
    private static final String FXML = "CustomPropertiesWindow.fxml";

    @FXML
    private TableView<CustomProperty> customPropertiesTable;

    @FXML
    private TableColumn<CustomProperty, String> nameColumn;

    @FXML
    private TableColumn<CustomProperty, String> prefixColumn;

    @FXML
    private TableColumn<CustomProperty, String> parameterColumn;

    /**
     * Creates a new custom properties window.
     *
     * @param root Stage to use as the root of the CustomPropertiesWindow.
     */
    public CustomPropertiesWindow(Stage root) {
        super(FXML, root);
    }

    public CustomPropertiesWindow() {
        this(new Stage());
    }

    /**
     * Displays the custom properties defined by the user.
     */
    public void show() {
        logger.fine("Displaying custom properties defined by the user.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the window is currently being displayed.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Helps to populate the table with the data for users to view.
     */
    public void initialiseTable() {
        ObservableList<CustomProperty> observableList = PropertyBook.getInstance()
            .getObservableCustomProperties();
        customPropertiesTable.setItems(observableList);
        nameColumn.setCellValueFactory(cp ->
            new ReadOnlyObjectWrapper<>(cp.getValue().getFullName()));
        prefixColumn.setCellValueFactory(cp ->
            new ReadOnlyObjectWrapper<>(cp.getValue().getPrefix().getPrefixName()));
        parameterColumn.setCellValueFactory(cp ->
            new ReadOnlyObjectWrapper<>(cp.getValue().getParameterType().getParameterName()));
    }

}
