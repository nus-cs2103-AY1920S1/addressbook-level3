package seedu.address.ui.calendar;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.calendar.task.Task;



/**
 * An UI component that displays information of a {@code Task}.
 */
public class ModuleTaskCard extends UiPart<Region> {

    private static final String FXML = "ModuleTaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CalendarAddressBook
     * level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane calendartags;

    public ModuleTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getTaskTitle().fullName);
        address.setText(task.getTaskTime().value);
        email.setText(task.getTaskDescription().value);
        task.getTaskTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> calendartags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleTaskCard)) {
            return false;
        }

        // state check
        ModuleTaskCard card = (ModuleTaskCard) other;
        return id.getText().equals(card.id.getText())
            && task.equals(card.task);
    }
}
