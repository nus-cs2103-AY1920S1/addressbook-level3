package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entity.body.Body;

/**
 * An UI component that displays information of a {@code Body}.
 */
public class BodyCard extends UiPart<Region> {

    private static final String FXML = "BodyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Body body;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label bodyId;
    @FXML
    private Label dateOfAdmission;

    public BodyCard(Body body, int displayedIndex) {
        super(FXML);
        this.body = body;
        id.setText(displayedIndex + ". ");
        name.setText(body.getName().fullName);
        bodyId.setText(body.getBodyIdNum().toString());
        bodyId.setText(body.getDateOfAdmission().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BodyCard)) {
            return false;
        }

        // state check
        BodyCard card = (BodyCard) other;
        return id.getText().equals(card.id.getText())
                && body.equals(card.body);
    }
}
