package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.member.AddMemberCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.ReadOnlyUserPrefs;
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
import seedu.pluswork.testutil.MemberBuilder;

public class AddMemberCommandTest {
    @Test
    public void constructor_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMemberCommand(null));
    }

    @Test
    public void execute_memberAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMemberAdded modelStub = new ModelStubAcceptingMemberAdded();
        Member validMember = new MemberBuilder().build();

        CommandResult commandResult = new AddMemberCommand(validMember).execute(modelStub);

        assertEquals(String.format(AddMemberCommand.MESSAGE_SUCCESS, validMember), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMember), modelStub.membersAdded);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Member validMember = new MemberBuilder().build();
        AddMemberCommand addMemberCommand = new AddMemberCommand(validMember);
        ModelStub modelStub = new ModelStubWithMember(validMember);

        assertThrows(CommandException.class, addMemberCommand.MESSAGE_DUPLICATE_MEMBER, () ->
                addMemberCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Member alice = new MemberBuilder().withName("Alice").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        AddMemberCommand addAliceCommand = new AddMemberCommand(alice);
        AddMemberCommand addBobCommand = new AddMemberCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMemberCommand addAliceCommandCopy = new AddMemberCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Task> getFilteredTaskListDoing() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Task> getFilteredTaskListDone() {
            throw new AssertionError("This method should not be called");
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
            return false;
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
            return 0;
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
            throw new AssertionError("This method should not be called.");
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
        public void updateFilteredMappingsList(Predicate<Mapping> predicate) {
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
        public ObservableList<TasMemMapping> getFilteredTasMemMappingsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<InvMemMapping> getFilteredInvMemMappingsList() {
            throw new AssertionError("This method should not be called.");
        }

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
        public void updateData() {
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
    private class ModelStubWithMember extends ModelStub {
        final ObservableList<Member> membersAdded = FXCollections.observableArrayList();
        private final FilteredList<Member> filteredMembers = new FilteredList<Member>(membersAdded);
        private final Member member;

        ModelStubWithMember(Member member) {
            requireNonNull(member);
            this.member = member;
        }

        @Override
        public boolean hasMember(Member member) {
            requireNonNull(member);
            return this.member.isSameMember(member);
        }

        @Override
        public ObservableList<Member> getFilteredMembersList() {
            return filteredMembers;
        }

        @Override
        public void updateFilteredMembersList(Predicate<Member> predicate) {
            requireNonNull(predicate);
            filteredMembers.setPredicate(predicate);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingMemberAdded extends ModelStub {
        final ObservableList<Member> membersAdded = FXCollections.observableArrayList();
        private final FilteredList<Member> filteredMembers = new FilteredList<Member>(membersAdded);

        @Override
        public boolean hasMember(Member member) {
            requireNonNull(member);
            return filteredMembers.stream().anyMatch(member::isSameMember);
        }

        @Override
        public void addMember(Member member) {
            requireNonNull(member);
            membersAdded.add(member);
        }

        @Override
        public ObservableList<Member> getFilteredMembersList() {
            return filteredMembers;
        }

        @Override
        public void updateFilteredMembersList(Predicate<Member> predicate) {
            requireNonNull(predicate);
            filteredMembers.setPredicate(predicate);
        }

        @Override
        public ReadOnlyProjectDashboard getProjectDashboard() {
            return new ProjectDashboard();
        }
    }
}
