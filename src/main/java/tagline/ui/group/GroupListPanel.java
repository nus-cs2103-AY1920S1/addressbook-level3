package tagline.ui.group;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tagline.model.group.Group;
import tagline.ui.UiPart;

/**
 * Panel containing the list of groups.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";

    @FXML
    private ListView<Group> groupListView;

    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Group} using a {@code GroupListCard}.
     */
    class GroupListViewCell extends ListCell<Group> {
        @Override
        protected void updateItem(Group group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GroupListCard(group).getRoot());
            }
        }
    }
}
