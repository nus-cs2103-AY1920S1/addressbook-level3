package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.distinctdate.DistinctDate;

/**
 * New Window to display a quick summary of the statistics
 */
public class SummaryWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchEventWindow.class);
    private static final String FXML = "SummaryWindow.fxml";

    @FXML
    private ListView<DistinctDate> dateListView;

    /**
     * Creates a new SummaryWindow.
     *
     * @param root Stage to use as the root of the SummaryWindow.
     */
    public SummaryWindow(Stage root, Logic logic) {
        super(FXML, root);
        /*ObservableList<DistinctDate> dateList = logic.getEventDistinctDateList();
        dateListView.setItems(dateList);
        dateListView.setCellFactory(listView -> new DateListViewCell());*/
    }

    /**
     * Creates a new SummaryWindow.
     */
    public SummaryWindow(Logic logic) {
        this(new Stage(), logic);
    }


    /**
     * Shows the Date window.
     */
    public void show() {
        logger.fine("Showing generated summary of statistics");
        getRoot().show();
        getRoot().centerOnScreen();

    }

    /**
     * Returns true if the summary window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the summary window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the summary window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DistinctDate} using a {@code DateCard}.

    class DateListViewCell extends ListCell<DistinctDate> {
        @Override
        protected void updateItem(DistinctDate date, boolean empty) {
            super.updateItem(date, empty);
            if (empty || date == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (date.getListOfEvents().size() < 4) {
                    setPrefHeight(26.5 + (date.getListOfEvents().size() * 105));
                } else {
                    setPrefHeight(425);
                }
                setGraphic(new DateCard(date, getIndex() + 1, null).getRoot());
            }
        }
    }
}*/
