package seedu.deliverymans.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Panel containing the list of deliverymen.
 */
public class DeliverymenStatusListPanel extends UiPart<Region> implements Initializable {
    private static final String FXML = "DeliverymenStatusListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeliverymanListPanel.class);

    @FXML
    private Button availableButton;
    @FXML
    private Button unavailableButton;
    @FXML
    private Button deliveringButton;
    @FXML
    private Label statusLabel;

    private ObservableList<Deliveryman> availableList;
    private ObservableList<Deliveryman> unavailableList;
    private ObservableList<Deliveryman> deliveringList;

    @javafx.fxml.FXML
    private ListView<Deliveryman> deliverymenStatusListView;

    public DeliverymenStatusListPanel(ObservableList<Deliveryman> availableList,
                                         ObservableList<Deliveryman> unavailableList,
                                         ObservableList<Deliveryman> deliveringList) {
        super(FXML);
        this.availableList = availableList;
        this.unavailableList = unavailableList;
        this.deliveringList = deliveringList;
        deliverymenStatusListView.setItems(availableList);
        deliverymenStatusListView.setCellFactory(listView -> new DeliverymenStatusListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Deliveryman} using a {@code DeliverymanCard}.
     */
    class DeliverymenStatusListViewCell extends ListCell<Deliveryman> {
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

    /**
     * Displays the corresponding status lists according to the button that is clicked.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableButton.setOnAction(e -> {
            statusLabel.setText("AVAILABLE DELIVERYMEN");
            deliverymenStatusListView.setItems(availableList);
            deliverymenStatusListView.setCellFactory(listView -> new DeliverymenStatusListViewCell());
        });
        unavailableButton.setOnAction(e -> {
            statusLabel.setText("UNAVAILABLE DELIVERYMEN");
            deliverymenStatusListView.setItems(unavailableList);
            deliverymenStatusListView.setCellFactory(listView -> new DeliverymenStatusListViewCell());
        });
        deliveringButton.setOnAction(e -> {
            statusLabel.setText("DELIVERING DELIVERYMEN");
            deliverymenStatusListView.setItems(deliveringList);
            deliverymenStatusListView.setCellFactory(listView -> new DeliverymenStatusListViewCell());
        });
    }

}
