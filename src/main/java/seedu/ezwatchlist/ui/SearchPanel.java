package seedu.ezwatchlist.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.model.show.Show;

/**
 * A ui for the search panel that is displayed at the side of the application.
 */
public class SearchPanel extends UiPart<Region> {
    private static final String FXML = "SearchPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SearchPanel.class);
    private MainWindow mainWindow;
    private ShowListPanel showListPanel;

    @FXML
    private ListView<Show> searchListView;

    public SearchPanel(ObservableList<Show> searchList) {
        super(FXML);
        searchListView.setItems(searchList);
        searchListView.setCellFactory(listView -> new SearchListViewCell());
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.showListPanel = mainWindow.getShowListPanel();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code ShowCard}.
     */
    class SearchListViewCell extends ListCell<Show> {
        @Override
        protected void updateItem(Show show, boolean empty) {
            super.updateItem(show, empty);

            if (empty || show == null) {
                setGraphic(null);
                setText(null);
            } else {
                ShowCard showCard = new ShowCard(show, getIndex() + 1);
                setGraphic(showCard.getRoot());
                showCard.setWatchedListener(new NonChangeableCheckBox(showCard.getWatched(), show));
            }
        }
    }

    /**
     * This class prevents the user from marking the checkbox by clicking
     *
     * @author AxxG "How to make checkbox or combobox readonly in JavaFX"
     */
    class NonChangeableCheckBox implements ChangeListener<Boolean> {
        private CheckBox checkBox;
        private Show show;

        public NonChangeableCheckBox (CheckBox checkBox, Show show) {
            this.show = show;
            this.checkBox = checkBox;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
            this.checkBox.setSelected(show.isWatched().value);
        }
    }
}
