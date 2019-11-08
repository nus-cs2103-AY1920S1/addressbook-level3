package seedu.pluswork.ui.views;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.ui.UiPart;

/**
 * Panel containing the list of tasks.
 * Called by {@code UserViewUpdate} when user executes {@code LisTaskCommand}.
 */
public class MemberListPanel extends UiPart<Region> {
    private static final String FXML = "MemberListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberListPanel.class);

    @FXML
    private ListView<IndivMemberCard> memberListView;

    public MemberListPanel(ObservableList<Member> memberList, ObservableList<Task> taskList, ObservableList<TasMemMapping> tasMemMappings) {
        super(FXML);
        ObservableList<IndivMemberCard> memberCards = FXCollections.observableArrayList();

        for (int i = 0; i < memberList.size(); i++) {
            Member memberInvolved = memberList.get(i);

            ArrayList<Task> specificTasks = new ArrayList<>();
            for (TasMemMapping mapping : tasMemMappings) {
                if (mapping.hasMember(i)) {
                    specificTasks.add(taskList.get(mapping.getTaskIndex()));
                }
            }

            IndivMemberCard memberCard = new IndivMemberCard(memberInvolved, i + 1, specificTasks);
            memberCards.add(memberCard);
        }

        memberListView.setItems(memberCards);
        memberListView.setCellFactory(listView -> new MemberListViewCell());
    }

    public MemberListPanel(ObservableList<Member> filteredMemberList, ObservableList<Member> memberList,
                           ObservableList<Task> taskList, ObservableList<TasMemMapping> tasMemMappings) {
        super(FXML);
        ObservableList<IndivMemberCard> memberCards = FXCollections.observableArrayList();

        for (int i = 0; i < filteredMemberList.size(); i++) {
            Member memberInvolved = filteredMemberList.get(i);

            int memIndex = 0;

            for (int j = 0; j < memberList.size(); j++) {
                if (memberList.get(j).equals(memberInvolved)) {
                    memIndex = j;
                }
            }

            ArrayList<Task> specificTasks = new ArrayList<>();
            for (TasMemMapping mapping : tasMemMappings) {
                if (mapping.hasMember(memIndex)) {
                    specificTasks.add(taskList.get(mapping.getTaskIndex()));
                }
            }

            IndivMemberCard memberCard = new IndivMemberCard(memberInvolved, i + 1, specificTasks);
            memberCards.add(memberCard);
        }

        memberListView.setItems(memberCards);
        memberListView.setCellFactory(listView -> new MemberListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class MemberListViewCell extends ListCell<IndivMemberCard> {
        @Override
        protected void updateItem(IndivMemberCard memberCard, boolean empty) {
            super.updateItem(memberCard, empty);

            if (empty || memberCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(memberCard.getRoot());
            }
        }
    }

}

