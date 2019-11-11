package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.member.AssignCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.ReadOnlyUserPrefs;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.Mapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.testutil.MappingBuilder;
import seedu.pluswork.testutil.MemberBuilder;
import seedu.pluswork.testutil.TaskBuilder;

public class AssignCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void execute_memberAndTaskValid_mappingSuccessful() throws Exception {
        ModelStubAcceptingTasMemMappingAdded modelStub = new ModelStubAcceptingTasMemMappingAdded();
        Task newTask = new TaskBuilder().build();
        Member newMember = new MemberBuilder().withId(new MemberId("test")).build();
        modelStub.addMember(newMember);
        modelStub.addTask(newTask);

        TasMemMapping validMapping = new MappingBuilder().withTask(0).withMember(0).tasMemMappingBuild();
        CommandResult commandResult = new AssignCommand(new Index(0), new MemberId("test")).execute(modelStub);

        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_SUCCESS), commandResult.getFeedbackToUser());
        assertEquals(validMapping, modelStub.mappingsAdded.get(0));
    }

    @Test
    public void execute_duplicateMapping_throwsCommandException() {
        ModelStubAcceptingTasMemMappingAdded modelStub = new ModelStubAcceptingTasMemMappingAdded();
        Task newTask = new TaskBuilder().build();
        Member newMember = new MemberBuilder().withId(new MemberId("test")).build();
        modelStub.addMember(newMember);
        modelStub.addTask(newTask);

        TasMemMapping validMapping = new MappingBuilder().withTask(0).withMember(0).tasMemMappingBuild();
        modelStub.addMapping(validMapping);
        AssignCommand assignCommand = new AssignCommand(new Index(0), new MemberId("test"));

        assertThrows(CommandException.class, AssignCommand.MESSAGE_DUPLICATE_MAPPING, () ->
                assignCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidMemberId_throwsCommandException() {
        ModelStubAcceptingTasMemMappingAdded modelStub = new ModelStubAcceptingTasMemMappingAdded();
        Task newTask = new TaskBuilder().build();
        Member newMember = new MemberBuilder().withId(new MemberId("test")).build();
        modelStub.addMember(newMember);
        modelStub.addTask(newTask);

        TasMemMapping validMapping = new MappingBuilder().withTask(0).withMember(0).tasMemMappingBuild();
        modelStub.addMapping(validMapping);
        AssignCommand assignCommand = new AssignCommand(new Index(0), new MemberId("testing"));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEMBER_ID, () ->
                assignCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidTaskId_throwsCommandException() {
        ModelStubAcceptingTasMemMappingAdded modelStub = new ModelStubAcceptingTasMemMappingAdded();
        Task newTask = new TaskBuilder().build();
        Member newMember = new MemberBuilder().withId(new MemberId("test")).build();
        modelStub.addMember(newMember);
        modelStub.addTask(newTask);

        AssignCommand assignCommand = new AssignCommand(new Index(1), new MemberId("test"));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, () ->
                assignCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        ModelStubAcceptingTasMemMappingAdded modelStub = new ModelStubAcceptingTasMemMappingAdded();
        Member alice = new MemberBuilder().withId(new MemberId("A")).build();
        Member bob = new MemberBuilder().withId(new MemberId("B")).build();
        Task firstTask = new TaskBuilder().build();
        modelStub.addMember(alice);
        modelStub.addMember(bob);

        TasMemMapping mappingAliceFirstTask = new MappingBuilder().withTask(0).withMember(0).tasMemMappingBuild();
        TasMemMapping mappingBobFirstTask = new MappingBuilder().withTask(0).withMember(1).tasMemMappingBuild();

        // same object -> returns true
        assertTrue(mappingAliceFirstTask.equals(mappingAliceFirstTask));

        // same values -> returns true
        TasMemMapping mappingAliceFirstTaskCopy = new MappingBuilder().withTask(0).withMember(0).tasMemMappingBuild();
        assertTrue(mappingAliceFirstTask.equals(mappingAliceFirstTaskCopy));

        // different types -> returns false
        assertFalse(mappingAliceFirstTask.equals(1));

        // null -> returns false
        assertFalse(mappingAliceFirstTask.equals(null));

        // different task -> returns false
        assertFalse(mappingAliceFirstTask.equals(mappingBobFirstTask));
    }

    private class ModelStub implements Model {

        @Override
        public void updateData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getProjectDashboardFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProjectDashboardFilePath(Path projectDashboardFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProjectDashboard(ReadOnlyProjectDashboard newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProjectDashboard getProjectDashboard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskListByDeadline() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskListNotStarted() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskListDoing() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskListDone() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTasksList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTasksList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMemberId(MemberId memId) {
            return memId.getDisplayId().equals("rak");
        }

        @Override
        public void deleteMember(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMember(Member target, Member editedMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getMembersLength() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Member> getFilteredMembersList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMembersList(Predicate<Member> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTasksLength() {
            return 1;
        }

        @Override
        public ObservableList<Inventory> getFilteredInventoriesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredInventoriesList(Predicate<Inventory> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void addInventory(Inventory inventory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInventory(Inventory inventory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteInventory(Inventory target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventory(Inventory target, Inventory editedInventory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMapping(Mapping mapping) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void deleteMapping(Mapping mapping) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public boolean hasMapping(Mapping mapping) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public ObservableList<Mapping> getFilteredMappingsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TasMemMapping> getFilteredTasMemMappingsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<InvMemMapping> getFilteredInvMemMappingsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMappingsList(Predicate<Mapping> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCalendar(CalendarWrapper calendar) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCalendar(CalendarWrapper calendar) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCalendar(CalendarWrapper calendar) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarWrapper> getFilteredCalendarList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Statistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatistics(Statistics newStats) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undo() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void redo() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UserSettings getUserSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getUserSettingsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Theme getCurrentTheme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentTheme(Theme newTheme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ClockFormat getCurrentClockFormat() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClockFormat(ClockFormat newClockFormat) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meeting> getFilteredMeetingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMeetingsList(Predicate<Meeting> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MeetingQuery getMeetingQuery() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void findMeetingTime(LocalDateTime startDate, LocalDateTime endDate, Duration meetingDuration) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTasMemMapping extends AssignCommandTest.ModelStub {
        private final TasMemMapping mapping;

        ModelStubWithTasMemMapping(TasMemMapping mapping) {
            requireNonNull(mapping);
            this.mapping = mapping;
        }

        @Override
        public boolean hasMapping(Mapping mapping) {
            requireNonNull(mapping);
            return this.mapping.isSameMapping((TasMemMapping) mapping);
        }
    }

    /**
     * A Model stub that always accept the inventory being added.
     */
    private class ModelStubAcceptingTasMemMappingAdded extends AssignCommandTest.ModelStub {
        final ArrayList<TasMemMapping> mappingsAdded = new ArrayList<>();
        final ObservableList<Member> members = FXCollections.observableArrayList();
        final ObservableList<Task> tasks = FXCollections.observableArrayList();

        //final ObservableList<Task> taskList = new ArrayList<Task>().add(new Task(new Name("task"), TaskStatus.DOING));

        @Override
        public boolean hasMapping(Mapping mapping) {
            requireNonNull(mapping);
            return mappingsAdded.stream().anyMatch(((TasMemMapping) mapping)::isSameMapping);
        }

        @Override
        public void addMapping(Mapping mapping) {
            requireNonNull(mapping);
            mappingsAdded.add((TasMemMapping) mapping);
        }

        @Override
        public ObservableList<Task> getFilteredTasksList() {
            return tasks;
        }

        @Override
        public ObservableList<Member> getFilteredMembersList() {
            return members;
        }

        @Override
        public ReadOnlyProjectDashboard getProjectDashboard() {
            return new ProjectDashboard();
        }

        @Override
        public int getMembersLength() {
            return members.size();
        }

        @Override
        public void addMember(Member member) {
            members.add(member);
        }

        @Override
        public void addTask(Task task) {
            tasks.add(task);
        }
    }
}
