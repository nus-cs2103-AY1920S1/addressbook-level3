package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ORDER_SHIRTS;
import static seedu.address.testutil.TypicalTasks.getTypicalProjectDashboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
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

    /**
     * A stub ReadOnlyProjectDashboard whose tasks list can violate interface constraints.
     */
    private static class ProjectDashboardStub implements ReadOnlyProjectDashboard {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Member> members = FXCollections.observableArrayList();

        ProjectDashboardStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }
    }

}
