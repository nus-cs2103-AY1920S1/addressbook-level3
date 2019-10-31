package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.accommodation.Accommodation;

/**
 * Panel containing the list of accommodations.
 */
public class AccommodationListPanel extends UiPart<Region> {
    private static final String FXML = "AccommodationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Accommodation> accommodationListView;

    public AccommodationListPanel(ObservableList<Accommodation> accommodationList) {
        super(FXML);
        accommodationListView.setItems(accommodationList);
        accommodationListView.setCellFactory(listView -> new AccommodationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Accommodation} using a
     * {@code AccommodationCardSmall}.
     */
    class AccommodationListViewCell extends ListCell<Accommodation> {
        @Override
        protected void updateItem(Accommodation accommodation, boolean empty) {
            super.updateItem(accommodation, empty);

            if (empty || accommodation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AccommodationCardSmall(accommodation, getIndex() + 1).getRoot());
            }
        }
    }

}
