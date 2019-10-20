package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.address.model.entity.IdentificationNumber;

/**
 * An UI component that displays information of a {@code fridgeId}.
 */
public class FridgeIdLabel extends UiPart<Region> {

    private static final String FXML = "FridgeIdLabel.fxml";

    public final IdentificationNumber fridgeId;

    @FXML
    private Circle circle;
    @FXML
    private Label fridgeIdPlaceholder;

    public FridgeIdLabel(IdentificationNumber id) {
        super(FXML);
        this.fridgeId = id;
        if (id == null) {
            fridgeIdPlaceholder.setText("NA");
            circle.getStyleClass().add("fridgeIdCircleEmpty");
        } else {
            fridgeIdPlaceholder.setText(id.toString());
            circle.getStyleClass().add("fridgeIdCircleFilled");
        }
        circle.radiusProperty().bind(fridgeIdPlaceholder.widthProperty());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        FridgeIdLabel label = (FridgeIdLabel) other;
        return fridgeIdPlaceholder.getText().equals(label.fridgeIdPlaceholder.getText());
    }
}
