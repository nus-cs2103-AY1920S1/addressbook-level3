package seedu.pluswork.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_URGENCY;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalTasksMembers.GET_SPONSORS;
import static seedu.pluswork.testutil.TypicalTasksMembers.ORDER_SHIRTS;
import static seedu.pluswork.testutil.TypicalTasksMembers.RECRUIT_MEMBERS;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.InvTasMapping;
import seedu.pluswork.model.mapping.Mapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.exceptions.DuplicateTaskException;
import seedu.pluswork.testutil.MeetingQueryBuilder;
import seedu.pluswork.testutil.TaskBuilder;

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
        projectDashboard.splitTasksBasedOnStatus();
        assertTrue(projectDashboard.getTaskList().contains(ORDER_SHIRTS));
        assertTrue(projectDashboard.getTasksNotStarted().contains(ORDER_SHIRTS));
        assertFalse(projectDashboard.getTasksDoing().contains(ORDER_SHIRTS));
        assertFalse(projectDashboard.getTasksDone().contains(ORDER_SHIRTS));
    }

    @Test
    public void removeTaskDoing_splitTasksBasedOnTaskStatus_expectedTasksPresentInExpectedLists() {
        projectDashboard.addTask(GET_SPONSORS);
        projectDashboard.removeTask(GET_SPONSORS);
        projectDashboard.splitTasksBasedOnStatus();
        assertFalse(projectDashboard.getTaskList().contains(GET_SPONSORS));
        assertFalse(projectDashboard.getTasksNotStarted().contains(GET_SPONSORS));
        assertFalse(projectDashboard.getTasksDoing().contains(GET_SPONSORS));
        assertFalse(projectDashboard.getTasksDone().contains(GET_SPONSORS));
    }

    @Test
    public void overloadDashboardWithDifferentTasks_splitTasksBasedOnStatus_expectedTasksPresentInExpectedLists() {
        projectDashboard.addTask(ORDER_SHIRTS);
        projectDashboard.addTask(GET_SPONSORS);
        projectDashboard.splitTasksBasedOnStatus();
        assertTrue(projectDashboard.getTasksDone().isEmpty());

        projectDashboard.removeTask(ORDER_SHIRTS);
        projectDashboard.setTask(GET_SPONSORS, RECRUIT_MEMBERS);
        projectDashboard.splitTasksBasedOnStatus();
        assertTrue(projectDashboard.getTasksDoing().isEmpty());
        assertTrue(projectDashboard.getTasksDone().contains(RECRUIT_MEMBERS));

        projectDashboard.addTask(ORDER_SHIRTS);
        projectDashboard.splitTasksBasedOnStatus();
        assertTrue(projectDashboard.getTaskList().contains(RECRUIT_MEMBERS));
        assertTrue(projectDashboard.getTasksNotStarted().contains(ORDER_SHIRTS));

        ProjectDashboard newData = getTypicalProjectDashboard();
        projectDashboard.resetData(newData);
        assertEquals(newData, projectDashboard);
        projectDashboard.splitTasksBasedOnStatus();
        assertFalse(projectDashboard.getTasksNotStarted().isEmpty());
    }

    /**
     * A stub ReadOnlyProjectDashboard whose tasks list can violate interface constraints.
     */
    private static class ProjectDashboardStub implements ReadOnlyProjectDashboard {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Member> members = FXCollections.observableArrayList();
        private final ObservableList<Inventory> inventories = FXCollections.observableArrayList();
        private final ObservableList<Mapping> mappings = FXCollections.observableArrayList();
        private final ObservableList<InvMemMapping> invMemMappings = FXCollections.observableArrayList();
        private final ObservableList<InvTasMapping> invTasMappings = FXCollections.observableArrayList();
        private final ObservableList<TasMemMapping> tasMemMappings = FXCollections.observableArrayList();
        private final HashMap<Task, ObservableList<Member>> memberByTask = new HashMap<>();
        private final ObservableList<CalendarWrapper> calendars = FXCollections.observableArrayList();
        private final ObservableList<Meeting> meetings = FXCollections.observableArrayList();

        private final ObservableList<Task> tasksNotStarted = FXCollections.observableArrayList();
        private final ObservableList<Task> tasksDoing = FXCollections.observableArrayList();
        private final ObservableList<Task> tasksDone = FXCollections.observableArrayList();

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

        @Override
        public ObservableList<Inventory> getInventoryList() {
            return inventories;
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

        @Override
        public ObservableList<Mapping> getMappingList() {
            return mappings;
        }

        @Override
        public ObservableList<InvMemMapping> getInvMemMappingList() {
            return invMemMappings;
        }

        @Override
        public ObservableList<InvTasMapping> getInvTasMappingList() {
            return invTasMappings;
        }

        @Override
        public ObservableList<TasMemMapping> getTasMemMappingList() {
            return tasMemMappings;
        }

        @Override
        public ObservableList<CalendarWrapper> getCalendarList() {
            return calendars;
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            return meetings;
        }

        @Override
        public MeetingQuery getMeetingQuery() {
            return new MeetingQueryBuilder().build();
        }

    }

}
