package seedu.address.itinerary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.itinerary.model.event.Event;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {

    private static final String fxmlCard = "EventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label title;

    @FXML
    private Label loc;

    @FXML
    private Label desc;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label tag;

    @FXML
    private ImageView checkBox;

    /**
     * Constructor of the event card which makes the card based on the details of the current event.
     * @param event current event which forms the event card in the event pane.
     * @param displayedIndex the id for each event
     */
    public EventCard(Event event, int displayedIndex) {
        super(fxmlCard);
        this.event = event;
        id.setText(displayedIndex + "");
        title.setText(event.getTitle().title);
        desc.setText(event.getDesc().desc);
        loc.setText(event.getLocation().location);
        date.setText(event.getDate().date);
        time.setText(event.getTime().time);
        tag.setText(event.getTag().tag);

        tag.setStyle(selectStyle(event.getTag().tag));

        Image image;

        if (event.getIsDone() == true) {
            image = new Image("/images/tick.png");
        } else {
            image = new Image("/images/cross.png");
        }

        checkBox.setImage(image);
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

    /**
     * Style the priority tagging to suit the event.
     * @param command the user input on the event priority.
     * @return the tag with different styling based on the priority of the event.
     */
    private String selectStyle(String command) {
        String priority = command.split(" ")[1];
        switch (priority) {
        case ("None"):
            return "-fx-background-color: darkslategrey;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-label-padding: 2;"
                    + "-fx-text-fill: white;"
                    + "-fx-border-color: white;";

        case ("Low"):
            return "-fx-background-color: darkorchid;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-label-padding: 2;"
                    + "-fx-text-fill: white;"
                    + "-fx-border-color: white;";

        case("Medium"):
            return "-fx-background-color: indianred;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-label-padding: 2;"
                    + "-fx-text-fill: white;"
                    + "-fx-border-color: white;";

        case("High"):
            return "-fx-background-color: crimson;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-label-padding: 2;"
                    + "-fx-text-fill: white;"
                    + "-fx-border-color: white;";

        case("Critical"):
            return "-fx-background-color: darkred;"
                    + "-fx-background-radius: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-label-padding: 2;"
                    + "-fx-text-fill: white;"
                    + "-fx-border-color: white;";

        default:
            return "";
        }
    }
}
