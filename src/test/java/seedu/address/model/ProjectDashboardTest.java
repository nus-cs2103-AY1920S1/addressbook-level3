package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.GET_SPONSORS;
import static seedu.address.testutil.TypicalTasks.ORDER_SHIRTS;
import static seedu.address.testutil.TypicalTasks.RECRUIT_MEMBERS;
import static seedu.address.testutil.TypicalTasks.getTypicalProjectDashboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class ProjectDashboardTest {

    private final ProjectDashboard projectDashboard = new ProjectDashboard();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), projectDashboard.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectDashboard.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyProjectDashboard_replacesData() {
        ProjectDashboard newData = getTypicalProjectDashboard();
        projectDashboard.resetData(newData);
        assertEquals(newData, projectDashboard);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withTags(VALID_TAG_PUBLICITY)
                .build();
        List<Task> newTasks = Arrays.asList(ORDER_SHIRTS, editedShirtOrderTask);
        ProjectDashboardStub newData = new ProjectDashboardStub(newTasks);

        assertThrows(DuplicateTaskException.class, () -> projectDashboard.resetData(newData));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> projectDashboard.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInProjectDashboard_returnsFalse() {
        assertFalse(projectDashboard.hasTask(ORDER_SHIRTS));
    }

    @Test
    public void hasTask_taskInProjectDashboard_returnsTrue() {
        projectDashboard.addTask(ORDER_SHIRTS);
        assertTrue(projectDashboard.hasTask(ORDER_SHIRTS));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInProjectDashboard_returnsTrue() {
        projectDashboard.addTask(ORDER_SHIRTS);
        Task editedShirtOrderTask = new TaskBuilder(ORDER_SHIRTS).withTags(VALID_TAG_URGENCY)
                .build();
        assertTrue(projectDashboard.hasTask(editedShirtOrderTask));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> projectDashboard.getTaskList().remove(0));
    }

    @Test
    public void addTaskNotStarted_splitTasksBasedOnTaskStatus_presentInExpectedLists() {
        projectDashboard.addTask(ORDER_SHIRTS);
        assertTrue(projectDashboard.getTaskList().contains(ORDER_SHIRTS));
        assertTrue(projectDashboard.getTasksNotStarted().contains(ORDER_SHIRTS));
        assertFalse(projectDashboard.getTasksDoing().contains(ORDER_SHIRTS));
        assertFalse(projectDashboard.getTasksDone().contains(ORDER_SHIRTS));
    }

    @Test
    public void removeTaskDoing_splitTasksBasedOnTask_expectedTasksPresentInExpectedLists() {
        projectDashboard.addTask(GET_SPONSORS);
        projectDashboard.removeTask(GET_SPONSORS);
        assertFalse(projectDashboard.getTaskList().contains(GET_SPONSORS));
        assertFalse(projectDashboard.getTasksNotStarted().contains(GET_SPONSORS));
        assertFalse(projectDashboard.getTasksDoing().contains(GET_SPONSORS));
        assertFalse(projectDashboard.getTasksDone().contains(GET_SPONSORS));
    }

    @Test
    public void overloadDashboardWithDifferentTasks_splitTasksBasedOnStatus_expectedTasksPresentInExpectedLists() {
        projectDashboard.addTask(ORDER_SHIRTS);
        projectDashboard.addTask(GET_SPONSORS);
        assertTrue(projectDashboard.getTasksDone().isEmpty());

        projectDashboard.removeTask(ORDER_SHIRTS);
        projectDashboard.setTask(GET_SPONSORS, RECRUIT_MEMBERS);
        assertTrue(projectDashboard.getTasksDoing().isEmpty());
        assertTrue(projectDashboard.getTasksDone().contains(RECRUIT_MEMBERS));

        projectDashboard.addTask(ORDER_SHIRTS);
        assertTrue(projectDashboard.getTaskList().contains(RECRUIT_MEMBERS));
        assertTrue(projectDashboard.getTasksNotStarted().contains(ORDER_SHIRTS));

        ProjectDashboard newData = getTypicalProjectDashboard();
        projectDashboard.resetData(newData);
        assertEquals(newData, projectDashboard);
        assertFalse(projectDashboard.getTasksNotStarted().isEmpty());
    }


    /**
     * A stub ReadOnlyProjectDashboard whose tasks list can violate interface constraints.
     */
    private static class ProjectDashboardStub implements ReadOnlyProjectDashboard {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Task> tasksNotStarted = FXCollections.observableArrayList();
        private final ObservableList<Task> tasksDoing = FXCollections.observableArrayList();
        private final ObservableList<Task> tasksDone = FXCollections.observableArrayList();

        ProjectDashboardStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
            splitTasksBasedOnStatus();
        }

        /**
         * Same implementation as that in {@code ProjectDashboard}, to test accuracy of splitting.
         */
        public void splitTasksBasedOnStatus() {

            tasksNotStarted.setAll();
            tasksDoing.setAll();
            tasksDone.setAll();

            for (Task task: tasks) {
                TaskStatus taskStatus = task.getTaskStatus();

                switch (taskStatus) {
                case UNBEGUN:
                    tasksNotStarted.add(task);
                    break;

                case DOING:
                    tasksDoing.add(task);
                    break;

                case DONE:
                    tasksDone.add(task);
                    break;

                default:
                    // no action
                }
            }
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Task> getTasksNotStarted() {
            return tasksNotStarted;
        }

        @Override
        public ObservableList<Task> getTasksDoing() {
            return tasksDoing;
        }

        @Override
        public ObservableList<Task> getTasksDone() {
            return tasksDone;
        }
    }

}
