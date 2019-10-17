package com.typee.ui;

import com.typee.model.engagement.Engagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EngagementCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EngagementList level 4</a>
     */

    public final Engagement engagement;

    @FXML
    private HBox cardPane;
    @FXML
    private Label type;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label description;
    @FXML
    private Label locationOfEngagement;
    @FXML
    private Label priority;
    @FXML
    private Label attendees;
    @FXML
    private Label id;

    public EngagementCard(Engagement engagement, int displayedIndex) {
        super(FXML);
        this.engagement = engagement;
        id.setText(displayedIndex + ". ");
        type.setText(engagement.getClass().getSimpleName());
        startTime.setText(engagement.getStartTime().toString());
        endTime.setText(engagement.getEndTime().toString());
        locationOfEngagement.setText(engagement.getLocation().toString());
        priority.setText(engagement.getPriority().name());
        attendees.setText(engagement.getAttendees().toString());
        description.setText(engagement.getDescription());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EngagementCard)) {
            return false;
        }

        // state check
        EngagementCard card = (EngagementCard) other;
        return id.getText().equals(card.id.getText())
                && engagement.equals(card.engagement);
    }
}
