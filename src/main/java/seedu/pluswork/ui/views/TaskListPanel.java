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
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<IndivTaskCard> taskListView;

    public TaskListPanel(ObservableList<Task> taskList, ObservableList<Member> memberList,
                         ObservableList<TasMemMapping> tasMemMappings) {
        super(FXML);

        ObservableList<IndivTaskCard> taskCards = FXCollections.observableArrayList();

        for (int i = 0; i < taskList.size(); i++) {
            Task taskInvolved = taskList.get(i);
            //int taskIndex = taskList.indexOf(taskInvolved);
            //int taskIndex = taskList.indexOf(taskInvolved);

            ArrayList<Member> specificMembers = new ArrayList<>();
            for (TasMemMapping mapping : tasMemMappings) {
                if (mapping.hasTask(i)) {
                    specificMembers.add(memberList.get(mapping.getMemberIndex()));
                }
            }

            IndivTaskCard taskCard = new IndivTaskCard(taskInvolved, i + 1, specificMembers);
            taskCards.add(taskCard);
        }

        taskListView.setItems(taskCards);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    public TaskListPanel(ObservableList<Task> filteredTaskList, ObservableList<Task> taskList, ObservableList<Member> memberList,
                         ObservableList<TasMemMapping> tasMemMappings) {
        super(FXML);

        ObservableList<IndivTaskCard> taskCards = FXCollections.observableArrayList();

        for (int i = 0; i < filteredTaskList.size(); i++) {
            Task taskInvolved = filteredTaskList.get(i);
            int taskIndex = 0;

            for (int j = 0; j < taskList.size(); j++) {
                if (taskList.get(j).equals(taskInvolved)) {
                    taskIndex = j;
                }
            }

            ArrayList<Member> specificMembers = new ArrayList<>();
            for (TasMemMapping mapping : tasMemMappings) {
                if (mapping.hasTask(taskIndex)) {
                    specificMembers.add(memberList.get(mapping.getMemberIndex()));
                }
            }

            IndivTaskCard taskCard = new IndivTaskCard(taskInvolved, i + 1, specificMembers);
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

