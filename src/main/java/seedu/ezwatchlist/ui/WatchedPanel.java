package seedu.ezwatchlist.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.show.Show;

/**
 * Panel containing the list of shows.
 */
public class WatchedPanel extends UiPart<Region> {
    private static final String FXML = "WatchedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WatchedPanel.class);
    private MainWindow mainWindow;

    @FXML
    private ListView<Show> watchedListView;

    public WatchedPanel(ObservableList<Show> showList) {
        super(FXML);
        watchedListView.setItems(showList);
        watchedListView.setCellFactory(listView -> new WatchedListViewCell());
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code ShowCard}.
     */
    class WatchedListViewCell extends ListCell<Show> {
        @Override
        protected void updateItem(Show show, boolean empty) {
            super.updateItem(show, empty);

            if (empty || show == null) {
                setGraphic(null);
                setText(null);
            } else {
                ShowCard showCard = new ShowCard(show, getIndex() + 1);
                setGraphic(showCard.getRoot());
                showCard.setWatchedListener(new ChangeableCheckBox(showCard.getDisplayedIndex()));
            }
        }
    }

    /**
     * This class prevents the user from marking the checkbox by clicking
     *
     * @author AxxG "How to make checkbox or combobox readonly in JavaFX"
     */
    class ChangeableCheckBox implements ChangeListener<Boolean> {
        private int displayedIndex;

        ChangeableCheckBox(int displayedIndex) {
            super();
            this.displayedIndex = displayedIndex;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
            try {
                mainWindow.executeCommand("watch " + displayedIndex);
            } catch (CommandException | ParseException | OnlineConnectionException e) {
                mainWindow.getResultDisplay().setFeedbackToUser(e.getMessage());
            }
        }
    }
}

