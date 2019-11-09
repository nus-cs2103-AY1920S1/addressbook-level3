package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.model.entity.IdentificationNumber;

//@@author shaoyi1997
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
        fridgeIdPlaceholder.setStyle("-fx-font-weight: bold");
        circle.setStrokeWidth(0.0);
        circle.setRadius(15.0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FridgeIdLabel // instanceof handles nulls
            && (fridgeIdPlaceholder.getText()
                    .equals(((FridgeIdLabel) other).fridgeIdPlaceholder.getText()))); // state check
    }
}
//@@author
