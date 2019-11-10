package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.entity.fridge.Fridge;

//@@author shaoyi1997
/**
 * An UI component that displays information of a {@code Fridge}.
 */
public class FridgeCard extends UiPart<Region> {

    public static final String NO_BODY_IN_FRIDGE = "No body assigned";
    private static final String FXML = "FridgeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Fridge fridge;

    @FXML
    private AnchorPane fridgeCardPane;
    @FXML
    private Label status;
    @FXML
    private StackPane fridgeIdPlaceholder;
    @FXML
    private Label bodyIdInFridge;

    public FridgeCard(Fridge fridge) {
        super(FXML);
        this.fridge = fridge;
        fridgeIdPlaceholder.getChildren().add(new FridgeIdLabel(fridge.getIdNum()).getRoot());
        status.setText(fridge.getFridgeStatus().toString());
        if (fridge.getBody().isPresent()) {
            bodyIdInFridge.setText(fridge.getBody().get().getIdNum().toString());
        } else {
            bodyIdInFridge.setText(NO_BODY_IN_FRIDGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FridgeCard)) {
            return false;
        }

        // state check
        FridgeCard card = (FridgeCard) other;
        return fridge.equals(card.fridge);
    }
}
//@@author
