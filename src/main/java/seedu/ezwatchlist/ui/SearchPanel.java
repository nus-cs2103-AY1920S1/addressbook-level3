package seedu.ezwatchlist.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code SearchShowCard}.
     */
    class SearchListViewCell extends ListCell<Show> {
        @Override
        protected void updateItem(Show show, boolean empty) {
            super.updateItem(show, empty);

            if (empty || show == null) {
                setGraphic(null);
                setText(null);
            } else {
                SearchShowCard searchShowCard = new SearchShowCard(show, getIndex() + 1);
                setGraphic(searchShowCard.getRoot());
            }
        }
    }
}
