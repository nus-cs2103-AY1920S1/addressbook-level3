package seedu.address.ui.cards;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {

    private static final String FXML = "ScheduleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Schedule schedule;

    @FXML
    private HBox cardPane;
    @FXML
    private Label index;
    @FXML
    private Label customerName;
    @FXML
    private Label customerNumber;
    @FXML
    private Label phoneName;
    @FXML
    private Label phoneColour;
    @FXML
    private Label phoneCapacity;
    @FXML
    private Label orderId;
    @FXML
    private Label time;
    @FXML
    private Label venue;
    @FXML
    private FlowPane tags;

    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        index.setText(displayedIndex + ". ");

        customerName.setText(schedule.getOrder().getCustomer().getCustomerName().fullName);
        customerNumber.setText(schedule.getOrder().getCustomer().getContactNumber().value);

        phoneName.setText(schedule.getOrder().getPhone().getPhoneName().fullName);
        phoneColour.setText(schedule.getOrder().getPhone().getColour().value);
        phoneCapacity.setText(schedule.getOrder().getPhone().getCapacity().value);

        orderId.setText(schedule.getOrder().getId().toString());

        time.setText(schedule.getCalendar().getTime().toString());
        venue.setText(schedule.getVenue().venue);

        schedule.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return index.getText().equals(card.index.getText())
                && schedule.equals(card.schedule);
    }
}
