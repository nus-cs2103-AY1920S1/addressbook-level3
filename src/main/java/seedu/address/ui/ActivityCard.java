package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;

/**
 * An UI component that displays information of an {@code Activity}.
 */
public class ActivityCard extends UiPart<Region> {
    private static final String FXML = "ActivityCard.fxml";

    public final Activity activity;

    @FXML
    private Label index;
    @FXML
    private Label title;
    @FXML
    private Label participantCount;

    public ActivityCard(Activity activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;

        index.setText("#" + displayedIndex);
        title.setText(activity.getTitle().toString());

        int numParticipants = activity.getParticipantIds().size();
        participantCount.setText(numParticipants + (numParticipants != 1 ? " participants" : " participant"));
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
        ActivityCard card = (ActivityCard) other;
        return activity.equals(card.activity);
    }
}
