package seedu.address.ui.itinerary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.events.EnterEditEventCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.itinerary.event.Event;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * Displays the information of each event on the {@link EventsPage}
 */
public class EventCard extends UiPart<HBox> {
    private static final String FXML = "itinerary/events/EventCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private VBox propertiesContainer;
    @FXML
    private Button editEventButton;

    private Event event;
    private Index displayedIndex;
    private MainWindow mainWindow;

    public EventCard(Event event, Index displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.event = event;
        this.displayedIndex = displayedIndex;
        this.mainWindow = mainWindow;
        fillEventCardLabels();
    }

    /**
     * Fills the labels of this event card.
     */
    private void fillEventCardLabels() {
        idLabel.setText(displayedIndex.getOneBased() + ".");
        nameLabel.setText(event.getName().toString());
        destinationLabel.setText("Destination :" + event.getDestination().toString());
        startDateLabel.setText("Start: " + ParserDateUtil.getDisplayDateTime(event.getStartDate()));
        endDateLabel.setText("End: " + ParserDateUtil.getDisplayDateTime(event.getEndDate()));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard otherCard = (EventCard) other;
        return event.equals(otherCard.event)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }

    @FXML
    private void handleEditEvent() {
        mainWindow.executeGuiCommand(EnterEditEventCommand.COMMAND_WORD
                + " " + displayedIndex.getOneBased());
    }
}
