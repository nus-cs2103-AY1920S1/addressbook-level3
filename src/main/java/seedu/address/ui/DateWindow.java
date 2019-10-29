package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

import seedu.address.model.distinctdate.DistinctDate;


/**
 * New Window to display the newly generated List of DistinctDate Objects
 */
public class DateWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchEventWindow.class);
    private static final String FXML = "DateWindow.fxml";

    @FXML
    private ListView<DistinctDate> dateListView;

    /**
     * Creates a new DateWindow.
     *
     * @param root Stage to use as the root of the DateWindow.
     */
    public DateWindow(Stage root, Logic logic) {
        super(FXML, root);
        ObservableList<DistinctDate> dateList = logic.getEventDistinctDateList();
        dateListView.setItems(dateList);
        dateListView.setCellFactory(listView -> new DateListViewCell());
    }

    /**
     * Creates a new DateWindow.
     */
    public DateWindow(Logic logic) {
        this(new Stage(), logic);
    }


    /**
     * Shows the Date window.
     */
    public void show() {
        logger.fine("Showing generated schedule - all dates and corresponding events.");
        getRoot().show();
        getRoot().centerOnScreen();

    }

    /**
     * Returns true if the date window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the date window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the date window.
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
