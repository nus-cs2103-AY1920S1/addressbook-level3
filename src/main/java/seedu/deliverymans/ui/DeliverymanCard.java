package seedu.deliverymans.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * An UI component that displays information of a {@code Deliveryman}.
 */
public class DeliverymanCard extends UiPart<Region> {

    private static final String FXML = "DeliverymanListCard.fxml";

    public final Deliveryman deliveryman;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private FlowPane tags;
    @FXML
    private TextFlow statusTag;
    @FXML
    private Text statusText;

    public DeliverymanCard(Deliveryman deliveryman, int displayedIndex) {
        super(FXML);
        this.deliveryman = deliveryman;
        id.setText(displayedIndex + ". ");
        name.setText(deliveryman.getName().fullName);
        phone.setText(deliveryman.getPhone().value);
        deliveryman.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        String strStatus = deliveryman.getStatus().getDescription();
        handleStatusTag(strStatus);
    }

    /**
     * Edits the status tag according to the corresponding status as input.
     */
    public void handleStatusTag(String strStatus) {
        if (strStatus.equals("AVAILABLE")) {
            statusText = new Text("AVAILABLE");
            statusText.setFill(Color.GREEN);
        } else if (strStatus.equals("UNAVAILABLE")) {
            statusText = new Text("UNAVAILABLE");
            statusText.setFill(Color.RED);
        } else {
            statusText = new Text("DELIVERING");
            statusText.setFill(Color.YELLOW);
        }
        statusTag.getChildren().add(statusText);
    }

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
        DeliverymanCard card = (DeliverymanCard) other;
        return id.getText().equals(card.id.getText())
                && deliveryman.equals(card.deliveryman);
    }
}
