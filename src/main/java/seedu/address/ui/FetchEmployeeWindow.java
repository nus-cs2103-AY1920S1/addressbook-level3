package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

import seedu.address.model.distinctdate.DistinctDate;
//import seedu.address.model.employee.Employee;

/**
 * New Window to display the newly generated List of DistinctDate Objects
 */
public class FetchEmployeeWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchEmployeeWindow.class);
    private static final String FXML = "FetchEmployeeWindow.fxml";

    @FXML
    private ListView<DistinctDate> dateListView;

    @FXML
    private AnchorPane employeeCard;

    /**
     * Creates a new FetchEmployeeWindow.
     *
     * @param root Stage to use as the root of the FetchEmployeeWindow.
     */
    public FetchEmployeeWindow(Stage root, Logic logic, Integer index) {
        super(FXML, root);
        ObservableList<DistinctDate> dateList = logic.getEmployeeDistinctDateList();
        dateListView.setItems(dateList);
        dateListView.setCellFactory(listView -> new DateListViewCell());
    }

    /**
     * Creates a new FetchEmployeeWindow.
     */
    public FetchEmployeeWindow(Logic logic, Integer index) {
        this(new Stage(), logic, index);
    }

    /**
     * Shows the FetchEmployeeWindow.
     */
    public void show() {
        logger.fine("Showing generated employee schedule - all dates and corresponding events.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the FetchEmployeeWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the FetchEmployeeWindow.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the FetchEmployeeWindow.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DistinctDate} using a {@code DateCard}.
     */
    class DateListViewCell extends ListCell<DistinctDate> {
        @Override
        protected void updateItem(DistinctDate date, boolean empty) {
            super.updateItem(date, empty);
            if (empty || date == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DateCard(date, getIndex() + 1, null).getRoot());
            }
        }
    }
}
