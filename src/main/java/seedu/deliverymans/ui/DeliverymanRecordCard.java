package seedu.deliverymans.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.DeliveryRecord;

/**
 * An UI component that displays the delivery record of a {@code Deliveryman}.
 */
public class DeliverymanRecordCard extends UiPart<Region> {

    private static final String FXML = "DeliverymanRecordCard.fxml";

    @FXML
    private ListView<DeliveryRecord> listView;

    @javafx.fxml.FXML
    private HBox cardPane;
    //@FXML
    // private Label id;
    @javafx.fxml.FXML
    private Label name;
    @FXML
    private Label durationOfService;
    @FXML
    private Label noOrdersCompleted;

    public DeliverymanRecordCard(DeliveryRecord record) {
        super(FXML);
        //id.setText(recordID + ". ");
        name.setText("Name: " + record.getName());
        durationOfService.setText("Duration of service: ");
        noOrdersCompleted.setText("Orders completed: ");
    }

    /*
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverymanCard)) {
            return false;
        }


        // state check
        DeliverymanRecordCard card = (DeliverymanRecordCard) other;
        return id.getText().equals(card.id.getText());
    } */
}
