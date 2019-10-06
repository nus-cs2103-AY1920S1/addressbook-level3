package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    private HBox members;

    @FXML
    private Label groupName;

    @FXML
    private Label groupDescription;

    public GroupCard(Group g) {
        super(FXML);
    }
}
