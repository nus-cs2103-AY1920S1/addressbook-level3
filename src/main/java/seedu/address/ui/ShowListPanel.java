package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.show.Show;

/**
 * Panel containing the list of persons.
 */
public class ShowListPanel extends UiPart<Region> {
    private static final String FXML = "ShowListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);//leave it first

    @FXML
    private ListView<Show> showListView;

    public ShowListPanel(ObservableList<Show> showList) {
        super(FXML);
        showListView.setItems(showList);
        showListView.setCellFactory(listView -> new ShowListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ShowListViewCell extends ListCell<Show> {
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
