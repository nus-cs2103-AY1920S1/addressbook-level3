package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.accommodation.Accommodation;

/**
 * An UI component that displays information of a {@code Accommodation}.
 */
public class AccommodationCardSmall extends UiPart<Region> {

    private static final String FXML = "AccommodationListCardSmall.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Planner level 4</a>
     */

    public final Accommodation accommodation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public AccommodationCardSmall(Accommodation accommodation, int displayedIndex) {
        super(FXML);
        this.accommodation = accommodation;
        id.setText(displayedIndex + ". ");
        name.setText(accommodation.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AccommodationCardSmall)) {
            return false;
        }

        // state check
        AccommodationCardSmall card = (AccommodationCardSmall) other;
        return id.getText().equals(card.id.getText())
                && accommodation.getName().equals(card.accommodation.getName());
    }
}
