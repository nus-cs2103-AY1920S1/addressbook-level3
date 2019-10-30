package seedu.address.ui.event;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Event;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MooLah level 4</a>
     */

    public final Event event;

    @FXML
    private AnchorPane eventCardPane;
    @FXML
    private Label description;
    @FXML
    private Label index;
    @FXML
    private Label price;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label budgetName;
    @FXML
    private FlowPane categories;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        index.setText(Integer.toString(displayedIndex));
        description.setText(event.getDescription().fullDescription);
        price.setText("$" + event.getPrice().value);
        categories.getChildren().add(new Label(event.getCategory().getCategoryName()));
        date.setText(event.getTimestamp().fullTimestamp.format(DateTimeFormatter.ISO_DATE));
        time.setText(null);
        budgetName.setText(event.getBudgetName().fullDescription);
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
        return index.getText().equals(card.index.getText())
                && event.equals(card.event);
    }
}
