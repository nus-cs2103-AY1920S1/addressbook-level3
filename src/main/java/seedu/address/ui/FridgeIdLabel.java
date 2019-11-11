package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import seedu.address.model.entity.IdentificationNumber;

//@@author shaoyi1997
/**
 * An UI component that displays information of a {@code fridgeId}.
 */
public class FridgeIdLabel {

    private StackPane fridgeIdPlaceholder = new StackPane();
    private Circle circle = new Circle();
    private Label fridgeIdLabel = new Label();
    private final IdentificationNumber fridgeId;

    public FridgeIdLabel(IdentificationNumber id) {
        this.fridgeId = id;
        if (id == null) {
            fridgeIdLabel.setText("NA");
            circle.getStyleClass().add("fridgeIdCircleEmpty");
        } else {
            fridgeIdLabel.setText(id.toString());
            circle.getStyleClass().add("fridgeIdCircleFilled");
        }
        fridgeIdLabel.setStyle("-fx-font-weight: bold");
        circle.setRadius(15.0);
        fridgeIdPlaceholder.getChildren().addAll(circle, fridgeIdLabel);
    }

    public StackPane getPane() {
        return fridgeIdPlaceholder;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FridgeIdLabel // instanceof handles nulls
            && (fridgeIdLabel.getText()
                    .equals(((FridgeIdLabel) other).fridgeIdLabel.getText()))); // state check
    }
}
//@@author
