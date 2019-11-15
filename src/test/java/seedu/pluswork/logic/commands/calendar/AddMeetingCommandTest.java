package seedu.pluswork.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
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
import seedu.pluswork.testutil.MeetingQueryBuilder;

public class AddMeetingCommandTest {

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMeetingCommand(null));
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        MeetingQuery validMeetingQuery = new MeetingQueryBuilder().build();

        Index validIndex = new Index(0);
        Meeting validMeeting = validMeetingQuery.getMeetingList().get(validIndex.getZeroBased());
        ModelStubWithMeetingQuery modelStub = new ModelStubWithMeetingQuery(validMeetingQuery);

        CommandResult commandResult = new AddMeetingCommand(validIndex).execute(modelStub);

        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS, validMeeting),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Index validIndex = new Index(1);
        MeetingQuery validMeetingQuery = new MeetingQueryBuilder().build();
        Meeting validMeeting = validMeetingQuery.getMeetingList().get(validIndex.getZeroBased());

        ModelStub modelStub = new ModelStubWithMeetingQuery(validMeetingQuery);
        modelStub.addMeeting(validMeeting);

        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(validIndex);
        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_DUPLICATE_MEETING, () ->
                addMeetingCommand.execute(modelStub));
    }

    @Test
    public void execute_meetingIndexOutOfBounds_throwsCommandException() {
        MeetingQuery validMeetingQuery = new MeetingQueryBuilder().build();
        ModelStub modelStub = new ModelStubWithMeetingQuery(validMeetingQuery);
        int validIndexUpperBound = validMeetingQuery.getMeetingList().size();

        Index invalidIndex1 = new Index(20);
        Index invalidIndex2 = new Index(validIndexUpperBound);
        Index invalidIndex3 = new Index(validIndexUpperBound + 1);
        Index invalidIndex4 = new Index(validIndexUpperBound + 200);

        assert (invalidIndex1.getZeroBased() >= validIndexUpperBound);
        assert (invalidIndex2.getZeroBased() >= validIndexUpperBound);
        assert (invalidIndex3.getZeroBased() >= validIndexUpperBound);
        assert (invalidIndex4.getZeroBased() >= validIndexUpperBound);

        AddMeetingCommand invalidCommand1 = new AddMeetingCommand(invalidIndex1);
        AddMeetingCommand invalidCommand2 = new AddMeetingCommand(invalidIndex2);
        AddMeetingCommand invalidCommand3 = new AddMeetingCommand(invalidIndex3);
        AddMeetingCommand invalidCommand4 = new AddMeetingCommand(invalidIndex4);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
                invalidCommand1.execute(modelStub));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
                invalidCommand2.execute(modelStub));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
                invalidCommand3.execute(modelStub));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
                invalidCommand4.execute(modelStub));
    }

    @Test
    public void execute_nullMeetingQuery_throwsCommandException() {
        MeetingQuery nullMeetingQuery = null;
        ModelStub modelStub = new ModelStubWithMeetingQuery(nullMeetingQuery);

        Index validIndex1 = new Index(1);
        Index validIndex2 = new Index(123934);
        Index validIndex3 = new Index(99999999);
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(validIndex1);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(validIndex2);
        AddMeetingCommand addMeetingCommand3 = new AddMeetingCommand(validIndex3);

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_INVALID_MEETING_REQUEST, () ->
                addMeetingCommand1.execute(modelStub));
        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_INVALID_MEETING_REQUEST, () ->
                addMeetingCommand2.execute(modelStub));
        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_INVALID_MEETING_REQUEST, () ->
                addMeetingCommand3.execute(modelStub));
    }

    @Test
    public void equals() {
        Index testIndex1 = new Index(1);
        Index testIndex2 = new Index(200000000);
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(testIndex1);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(testIndex2);

        // same object -> returns true
        assertTrue(addMeetingCommand1.equals(addMeetingCommand1));

        // same values -> returns true
        AddMeetingCommand addMeetingCommand1Copy = new AddMeetingCommand(testIndex1);
        assertTrue(addMeetingCommand1.equals(addMeetingCommand1Copy));

        // different types -> returns false
        assertFalse(addMeetingCommand2.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand2.equals(null));

        // different task -> returns false
        assertFalse(addMeetingCommand1.equals(addMeetingCommand2));
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
        public void setCurrentTheme(Theme newTheme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ClockFormat getCurrentClockFormat() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TasMemMapping> getFilteredTasMemMappingsList() {
            throw new AssertionError("This method should not be called.");
        }


        public void setClockFormat(ClockFormat newClockFormat) {
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
        public ObservableList<InvMemMapping> getFilteredInvMemMappingsList() {
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

        @Override
        public void updateData() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithMeetingQuery extends ModelStub {
        final MeetingQuery meetingQuery;
        final ArrayList<Meeting> meetingsAdded = new ArrayList<>();

        private ModelStubWithMeetingQuery(MeetingQuery meetingQuery) {
            this.meetingQuery = meetingQuery;
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            requireNonNull(meeting);
            return meetingsAdded.stream().anyMatch(meeting::isSameMeeting);
        }

        @Override
        public void addMeeting(Meeting meeting) {
            requireNonNull(meeting);
            meetingsAdded.add(meeting);
        }

        @Override
        public MeetingQuery getMeetingQuery() {
            return meetingQuery;
        }

        @Override
        public void updateFilteredMeetingsList(Predicate<Meeting> predicate) {
        }
    }
}
