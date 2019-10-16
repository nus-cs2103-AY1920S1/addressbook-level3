package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;

/**
 * An UI component that displays information of a {@code Activity}.
 */
public class ActivityCardSmall extends UiPart<Region> {

    private static final String FXML = "ActivityListCardSmall.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Planner level 4</a>
     */

    public final Activity activity;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public ActivityCardSmall(Activity activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        id.setText(displayedIndex + ". ");
        name.setText(activity.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCardSmall)) {
            return false;
        }

        // state check
        ActivityCardSmall card = (ActivityCardSmall) other;
        return id.getText().equals(card.id.getText())
                && activity.getName().equals(card.activity.getName());
    }
}
