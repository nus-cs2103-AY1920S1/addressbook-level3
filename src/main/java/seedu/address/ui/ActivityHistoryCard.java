package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;

/**
 * An UI component that displays contact-specific information of an {@code Activity}.
 */
public class ActivityHistoryCard extends UiPart<Region> {

    private static final String FXML = "ActivityHistoryCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Activity activity;
    public final double transferAmt;

    @FXML
    private Label title;
    @FXML
    private Label netTransfer;

    public ActivityHistoryCard(Activity activity, double transferAmt) {
        super(FXML);
        this.activity = activity;
        this.transferAmt = transferAmt;

        title.setText(activity.getTitle().toString());
        if (transferAmt < 0) {
            netTransfer.setStyle("-fx-text-fill: maroon");
            netTransfer.setText(String.format("Owes $%.2f", -transferAmt));
        } else if (transferAmt > 0) {
            netTransfer.setStyle("-fx-text-fill: darkgreen");
            netTransfer.setText(String.format("Owed $%.2f", transferAmt));
        } else {
            netTransfer.setText("Not involved in any expenses yet.");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCard)) {
            return false;
        }

        // state check
        ActivityHistoryCard card = (ActivityHistoryCard) other;
        return activity.equals(card.activity)
            && transferAmt == card.transferAmt;
    }
}
