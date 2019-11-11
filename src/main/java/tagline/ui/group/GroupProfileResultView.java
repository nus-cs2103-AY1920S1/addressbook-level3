//@@author tanlk99
package tagline.ui.group;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import tagline.model.contact.Contact;
import tagline.model.group.Group;
import tagline.ui.ResultView;
import tagline.ui.contact.ContactListPanel;

/**
 * The UI component that displays a single group as a result.
 */
public class GroupProfileResultView extends ResultView {

    private static final String FXML = "GroupProfileResultView.fxml";

    private ContactListPanel contactListPanel;

    @FXML
    private Label name;
    @FXML
    private Label memberCount;
    @FXML
    private Label description;
    @FXML
    private StackPane contactListPanelPlaceholder;

    public GroupProfileResultView() {
        super(FXML);

        description.managedProperty().bind(description.visibleProperty());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts(ObservableList<Group> groupList, ObservableList<Contact> contactList) {
        contactListPanel = new ContactListPanel(contactList);
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());

        groupList.addListener(new ListChangeListener<Group>() {
            @Override
            public void onChanged(Change<? extends Group> change) {
                if (groupList.size() == 1) { //Single group display
                    updateLabels(groupList.get(0));
                }
            }
        });
    }

    /**
     * Updates the inner labels of this component, and hides them when empty.
     */
    public void updateLabels(Group group) {
        name.setText(group.getGroupName().value);

        if (group.getMemberIds().size() == 0) {
            memberCount.setText("No members");
        } else {
            memberCount.setText(group.getMemberIds().size() + " member"
                + (group.getMemberIds().size() == 1 ? "" : "s"));
        }

        if (group.getGroupDescription().value.isBlank()) {
            description.setVisible(false); //hide field
        } else {
            description.setVisible(true);
            description.setText(group.getGroupDescription().value);
        }
    }
}
