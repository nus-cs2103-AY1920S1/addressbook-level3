package tagline.ui.group;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import tagline.model.group.Group;
import tagline.ui.UiPart;

/**
 * A UI component that displays information of a {@code Group}.
 */
public class GroupListCard extends UiPart<Region> {

    public static final String EMPTY_GROUP_STRING = "No members in this group.";
    private static final String FXML = "GroupListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Group group;

    @FXML
    private VBox groupListInternalPane;
    @FXML
    private HBox memberIdsContainer;
    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label memberIdsLabel;
    @FXML
    private FlowPane memberIds;

    public GroupListCard(Group group) {
        super(FXML);
        this.group = group;
        name.setText(group.getGroupName().value);

        setLabelText(description, group.getGroupDescription().value);

        if (group.getMemberIds().isEmpty()) {
            memberIdsLabel.setText(EMPTY_GROUP_STRING);
            memberIdsLabel.setId("emptyGroupLabel");

            memberIdsContainer.getChildren().remove(memberIds);
        } else {
            group.getMemberIds().stream()
                .sorted(Comparator.comparing(id -> id.value))
                .forEach(id -> memberIds.getChildren().add(new Label("#" + id.value)));
        }
    }

    /**
     * Sets the content in a {@code label} to {@code text}.
     * If {@code text} is empty, hides the {@code label}.
     */
    private void setLabelText(Label label, String text) {
        if (text.isEmpty()) {
            label.setVisible(false);
            groupListInternalPane.getChildren().remove(label);
        } else {
            label.setText(text);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupListCard)) {
            return false;
        }

        // state check
        GroupListCard card = (GroupListCard) other;
        return group.equals(card.group);
    }
}
