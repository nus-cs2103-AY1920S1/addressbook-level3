package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;
/**
 * An UI component that displays information of a {@code Lesson}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Classroom level 4</a>
     */

    public final Lesson lesson;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label className;
    @FXML
    private Label day;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public ReminderCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText(displayedIndex + ". ");
        className.setText(lesson.getName().className);
        startTime.setText(lesson.getStartTime().getStringTime());
        endTime.setText(lesson.getEndTime().getStringTime());
        day.setText(lesson.getStartTime().getStringDay());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && lesson.equals(card.lesson);
    }
}
