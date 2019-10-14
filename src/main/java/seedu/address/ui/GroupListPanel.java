package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.display.sidepanel.GroupDisplay;

/**
 * A class that shows the list of existing groups.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<GroupDisplay> groupListView;

    public GroupListPanel(ObservableList<GroupDisplay> groupList) {
        super(FXML);
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class GroupListViewCell extends ListCell<GroupDisplay> {
        @Override
        protected void updateItem(GroupDisplay grp, boolean empty) {
            super.updateItem(grp, empty);

            if (empty || grp == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GroupCard(grp, getIndex() + 1).getRoot());
            }
        }
    }
}
