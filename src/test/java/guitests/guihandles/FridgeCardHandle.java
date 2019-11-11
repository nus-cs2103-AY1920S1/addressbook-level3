package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.ui.FridgeCard;

//@@author shaoyi1997
/**
 * Provides a handle to a person card in the person list panel.
 */
public class FridgeCardHandle extends NodeHandle<Node> {
    private static final String FRIDGE_FIELD_ID = "#fridgeIdPlaceholder";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String BODY_ID_IN_FRIDGE_FIELD_ID = "#bodyIdInFridge";

    private final StackPane fridgeIdLabel;
    private final Label statusLabel;
    private final Label bodyIdLabel;


    public FridgeCardHandle(Node cardNode) {
        super(cardNode);

        fridgeIdLabel = getChildNode(FRIDGE_FIELD_ID);
        statusLabel = getChildNode(STATUS_FIELD_ID);
        bodyIdLabel = getChildNode(BODY_ID_IN_FRIDGE_FIELD_ID);
    }

    public String getFridgeId() {
        StackPane fridgeCardPane = (StackPane) fridgeIdLabel.getChildren().get(0);
        Label fridgeId = (Label) fridgeCardPane.getChildren().get(1);
        return fridgeId.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public String getBodyId() {
        return bodyIdLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code fridge}.
     */
    public boolean equals(Fridge fridge) {
        return getFridgeId().equals(fridge.getIdNum().toString())
            && (getStatus().equals(fridge.getFridgeStatus().toString())
            && (getBodyId().equals(fridge.getBody().isPresent()
            ? fridge.getBody().get().getIdNum().toString() : FridgeCard.NO_BODY_IN_FRIDGE)));
    }
}
//@@author
