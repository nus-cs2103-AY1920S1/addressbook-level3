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
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label participantCount;

    public ActivityCard(Activity activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        id.setText("ID: " + activity.getPrimaryKey());
        index.setText("#" + displayedIndex);
        title.setText(activity.getTitle().toString());

        int numParticipants = activity.getParticipantCount();
        participantCount.setText(numParticipants + " " + pluralize("participant", numParticipants));
    }

    private String pluralize(String noun, int count) {
        assert count >= 0;
        return count != 1 ? noun : noun + "s";
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
        return id.getText().equals(card.id.getText())
                && activity.equals(card.activity);
    }
}
