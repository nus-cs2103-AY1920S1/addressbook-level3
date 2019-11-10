package seedu.planner.ui.cards;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.ui.UiPart;

//@@author 1nefootstep
/**
 * An UI component that displays information of a {@code ActivityWithTime}.
 */
public class ActivityWithTimeCardFull extends UiPart<Region> {

    private static final String FXML = "ActivityWithTimeListCardFull.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Planner level 4</a>
     */

    public final ActivityWithTime activityWithTime;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,
            FormatStyle.SHORT);

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    @FXML
    private Label name;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public ActivityWithTimeCardFull(ActivityWithTime activityWithTime, int displayedIndex) {
        super(FXML);
        this.activityWithTime = activityWithTime;
        id.setText(displayedIndex + ". ");
        name.setText(activityWithTime.getActivity().getName().toString());

        LocalDateTime startDateTime = activityWithTime.getStartDateTime();
        LocalDateTime endDateTime = activityWithTime.getEndDateTime();
        if (startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
            startTime.setText(startDateTime.toLocalTime().format(timeFormatter));
            endTime.setText(endDateTime.toLocalTime().format(timeFormatter));
        } else {
            startTime.setText(startDateTime.format(dateTimeFormatter));
            endTime.setText(endDateTime.format(dateTimeFormatter));
        }

        activityWithTime.getActivity().getTags().stream()
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
        if (!(other instanceof ActivityWithTimeCardFull)) {
            return false;
        }

        // state check
        ActivityWithTimeCardFull card = (ActivityWithTimeCardFull) other;
        return id.getText().equals(card.id.getText())
                && activityWithTime.equals(card.activityWithTime);
    }
}
