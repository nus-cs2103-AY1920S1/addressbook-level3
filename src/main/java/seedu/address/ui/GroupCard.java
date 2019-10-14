package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.ui.util.BubbleGenerator;

/**
 * Class to view groups.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    @FXML
    private static final Label memberLabel = new Label("Members:");

    @FXML
    private StackPane groupId;

    @FXML
    private HBox groupCardPane;

    @FXML
    private Label groupName;

    @FXML
    private Label groupDescription;

    private GroupDisplay groupDisplay;

    public GroupCard(GroupDisplay groupDisplay, int displayedIndex) {
        super(FXML);
        this.groupDisplay = groupDisplay;
        groupId.getChildren().add(new BubbleGenerator(displayedIndex, 50, 2).getBubble());
        groupName.setText(groupDisplay.getGroupName().toString());
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
                    && groupDisplay.equals(g.groupDisplay);
        } else {
            return false;
        }
    }
}
