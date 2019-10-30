package seedu.address.ui;

import java.util.Comparator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    public final Event event;
    private MainWindow mainWindow;
    private Integer index;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label venue;
    @FXML
    private Label startDate;
    @FXML
    private Label manpowerCount;
    @FXML
    private FlowPane tags;

    public EventCard(Event event, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.event = event;
        this.mainWindow = mainWindow;
        this.index = displayedIndex - 1;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().eventName);
        venue.setText(event.getVenue().venue);
        manpowerCount.setText("Manpower: " + event.getCurrentManpowerCount() + " / "
                + event.getManpowerNeeded().value);
        startDate.setText(event.getStartDate() + " to "
                + event.getEndDate());
        event.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        mainWindow.handleEventFetch(index);
                    }
                }
            }
        };
        cardPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
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
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
