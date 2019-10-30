package seedu.address.ui.views;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class IndivTaskCard extends UiPart<Region> {

    private static final String FXML = "IndivTaskCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ProjectDashboard level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;
    @FXML
    private Label membersAllocated;
    @FXML
    private Text members;

    public IndivTaskCard(Task task, int displayedIndex, List<Member> memberList) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        task.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (task.hasDeadline()) {
            deadline.setText("Due on: " + DateTimeUtil.displayDateTime(task.getDeadline()));
        } else {
            deadline.setText("No deadline set");
        }

        String listOfMembers = "";

        for (int i = 0; i < memberList.size(); i++) {
            listOfMembers += (i+1) + ". " + memberList.get(i).toString() + "\n";
        }

        members.setText(listOfMembers);
    }

    public Label getDeadlineLabel() {
        return deadline;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IndivTaskCard)) {
            return false;
        }

        // state check
        IndivTaskCard card = (IndivTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
