package seedu.address.ui.views;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of tasks.
 * Called by {@code UserViewUpdate} when user executes {@code LisTaskCommand}.
 */
public class MemberListPanel extends UiPart<Region> {
    private static final String FXML = "MemberListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberListPanel.class);

    @FXML
    private ListView<Member> memberListView;

    public MemberListPanel(ObservableList<Member> memberList) {
        super(FXML);
        memberListView.setItems(memberList);
        memberListView.setCellFactory(listView -> new MemberListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MemberListViewCell extends ListCell<Member> {
        @Override
        protected void updateItem(Member member, boolean empty) {
            super.updateItem(member, empty);

            if (empty || member == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MemberCard(member, getIndex() + 1).getRoot());
            }
        }
    }

}

