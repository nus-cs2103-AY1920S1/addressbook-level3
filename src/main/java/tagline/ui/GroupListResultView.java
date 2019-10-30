package tagline.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import tagline.model.group.Group;

/**
 * The UI component that displays the group list as a result.
 */
public class GroupListResultView extends ResultView {

    private static final String FXML = "GroupListResultView.fxml";

    private GroupListPanel groupListPanel;

    @FXML
    private StackPane groupListPanelPlaceholder;

    public GroupListResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(ObservableList<Group> groupList) {
        groupListPanel = new GroupListPanel(groupList);
        groupListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());
    }
}
