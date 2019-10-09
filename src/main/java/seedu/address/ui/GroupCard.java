package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.group.Group;

/**
 * Class to view groups.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    @FXML
    private static final Label memberLabel = new Label("Members:");

    @FXML
    private VBox groupCardPane;

    @FXML
    private Label groupName;

    @FXML
    private Label groupDescription;

    private Group group;

    public GroupCard(Group g, int displayedIndex) {
        super(FXML);
        this.group = g;
        groupName.setText(g.getGroupName().toString());
        //Remark field in groups cannot be initialised to null.
        groupDescription.setText("REMARK");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof GroupCard) {
            GroupCard g = (GroupCard) o;
            return groupDescription.getText().equals(g.groupDescription.getText())
                    && groupName.getText().equals(g.groupName.getText())
                    && group.equals(g.group);
        } else {
            return false;
        }
    }
}
