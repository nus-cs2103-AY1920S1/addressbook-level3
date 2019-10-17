package seedu.ezwatchlist.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.model.show.Show;

public class SearchPanel extends UiPart<Region> {
    private static final String FXML = "SearchPanel.fxml";

    @FXML
    private ListView<Show> searchListView;

    public SearchPanel(/*ObservableList<Show> searchList*/) {
        super(FXML);
        //searchListView.setItems(searchList);
        //searchListView.setCellFactory(listView -> new SearchListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code ShowCard}.
     */
    /*class SearchListViewCell extends ListCell<Show> {
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
    }*/
}
