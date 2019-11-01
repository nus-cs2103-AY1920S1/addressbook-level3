package seedu.address.ui.views;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.mapping.TasMemMapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of tasks.
 * Called by {@code UserViewUpdate} when user executes {@code LisTaskCommand}.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<IndivTaskCard> taskListView;

    public TaskListPanel(ObservableList<Task> taskList, ObservableList<Member> memberList,
                         ObservableList<TasMemMapping> tasMemMappings) {
        super(FXML);

        ObservableList<IndivTaskCard> taskCards = FXCollections.observableArrayList();

        for (int i=0; i<taskList.size(); i++) {
            Task taskInvolved = taskList.get(i);
            int taskIndex = taskList.indexOf(taskInvolved);

            ArrayList<Member> specificMembers = new ArrayList<>();
            for (TasMemMapping mapping : tasMemMappings) {
                if (mapping.hasTask(taskIndex)) {
                    specificMembers.add(memberList.get(mapping.getMemberIndex()));
                }
            }

            IndivTaskCard taskCard = new IndivTaskCard(taskInvolved, i+1,  specificMembers);
            taskCards.add(taskCard);
        }

        taskListView.setItems(taskCards);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<IndivTaskCard> {
        @Override
        protected void updateItem(IndivTaskCard taskCard, boolean empty) {
            super.updateItem(taskCard, empty);

            if (empty || taskCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(taskCard.getRoot());
            }
        }
    }

}

