package seedu.ezwatchlist.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.model.show.Show;

public class SearchPanel extends UiPart<Region> {
    private static final String FXML = "SearchPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SearchPanel.class);

    @FXML
    private ListView<Show> searchListView;

    public SearchPanel() {
        super(FXML);
    }

    public SearchPanel(ObservableList<Show> showList) {
        super(FXML);
        searchListView.setItems(showList);
        searchListView.setCellFactory(listView -> new SearchPanel.SearchListViewCell());
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
                setGraphic(new ShowCard(show, getIndex() + 1).getRoot());
            }
        }
    }

}