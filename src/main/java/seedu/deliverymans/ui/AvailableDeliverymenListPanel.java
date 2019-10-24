package seedu.deliverymans.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Panel containing the list of deliverymen.
 */
public class AvailableDeliverymenListPanel extends UiPart<Region> {
    private static final String FXML = "AvailableDeliverymenListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeliverymanListPanel.class);

    @javafx.fxml.FXML
    private ListView<Deliveryman> availableDeliverymanListView;

    public AvailableDeliverymenListPanel(ObservableList<Deliveryman> deliverymanList) {
        super(FXML);
        availableDeliverymanListView.setItems(deliverymanList);
        availableDeliverymanListView.setCellFactory(listView -> new DeliverymanListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Deliveryman} using a {@code DeliverymanCard}.
     */
    class DeliverymanListViewCell extends ListCell<Deliveryman> {
        @Override
        protected void updateItem(Deliveryman deliveryman, boolean empty) {
            super.updateItem(deliveryman, empty);

            if (empty || deliveryman == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeliverymanCard(deliveryman, getIndex() + 1).getRoot());
            }
        }
    }
}
