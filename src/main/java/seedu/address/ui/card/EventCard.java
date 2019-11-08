package seedu.address.ui.card;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.events.EventSource;

//@@author Kyzure
/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends Card {

    private static final String FXML = "EventCard.fxml";

    @FXML
    private VBox eventDetails;

    @FXML
    private Label eventName;

    @FXML
    private Label eventStartDate;

    @FXML
    private Label eventEndDate;

    @FXML
    private StackPane eventEndDateBase;

    @FXML
    private Label eventRemindDate;

    @FXML
    private StackPane eventRemindDateBase;

    @FXML
    private StackPane eventTagsBase;

    @FXML
    private VBox eventTagList;

    @FXML
    private Label eventIndex;

    @FXML
    private StackPane eventIndexBase;

    /**
     * Constructor for the EventCard, which displays the information of a particular event.
     * This is used for ListPanel.
     *
     * @param event The given event.
     */
    public EventCard(EventSource event, Integer index) {
        super(FXML);
        eventName.setText(event.getDescription());
        eventStartDate.setText("Start Date: " + event.getStartDateTime());
        eventIndex.setText("[" + index + "]");
        addOptions(event);
    }

    /**
     * Removes the optional part of the event card if it does not exists.
     *
     * @param event The given event.
     */
    private void addOptions(EventSource event) {
        // End Date Option
        if (event.getEndDateTime() != null) {
            eventEndDate.setText("End Date: " + event.getEndDateTime());
        } else {
            eventDetails.getChildren().remove(eventEndDateBase);
        }

        // Remind Date Option
        if (event.getRemindDateTime() != null) {
            eventRemindDate.setText("Remind Date: " + event.getRemindDateTime());
        } else {
            eventDetails.getChildren().remove(eventRemindDateBase);
        }

        // Tags Option
        if (event.getTags() != null) {
            addTags(event);
        } else {
            eventDetails.getChildren().remove(eventTagsBase);
        }

        // Others
        eventName.setMinHeight(Region.USE_PREF_SIZE);
    }

    /**
     * Adds CardTagline to the EventCard with the given tags.
     *
     * @param event The given event.
     * @see CardTagline
     */
    private void addTags(EventSource event) {
        Set<String> tags = event.getTags();
        CardTagline cardTagline = new CardTagline();
        for (String tag : tags) {
            CardTag cardTag = new CardTag(tag);
            if (cardTag.getWidth() >= CardTagline.MAX_WIDTH) {
                CardTagline cardTagline1 = new CardTagline();
                cardTagline1.addSingleTag(cardTag);
                eventTagList.getChildren().add(cardTagline1.getRoot());
                continue;
            }
            boolean isAdded = cardTagline.isTagAdded(cardTag);
            if (!isAdded) {
                eventTagList.getChildren().add(cardTagline.getRoot());
                cardTagline = new CardTagline();
                cardTagline.isTagAdded(cardTag);
            }
        }
        eventTagList.getChildren().add(cardTagline.getRoot());
    }
}
