package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.exceptions.CommandException;
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
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.testutil.CalendarWrapperBuilder;

public class DeleteCalendarCommandTest {

    @Test
    public void constructor_nullCalendar_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCalendarCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        MemberName validMemberName = new MemberName("Sample");
        DeleteCalendarCommand validCommand = new DeleteCalendarCommand(validMemberName);
        ModelStub modelStub = null;

        assertThrows(NullPointerException.class, () -> validCommand.execute(modelStub));
    }

    @Test
    public void execute_deleteCalendar_deleteSuccessful() throws CommandException {
        MemberName validMemberName = new MemberName("Sample Name");
        CalendarWrapper validCalendar = new CalendarWrapperBuilder()
                .withMemberName(validMemberName)
                .build();

        DeleteCalendarCommand validCommand = new DeleteCalendarCommand(validMemberName);
        ModelStub modelStub = new ModelStubAcceptingCalendarAdded();
        modelStub.addCalendar(validCalendar);

        assert(validCalendar.getMemberName().equals(validMemberName));

        CommandResult commandResult = validCommand.execute(modelStub);

        assertEquals(String.format(DeleteCalendarCommand.MESSAGE_SUCCESS, validCalendar),
                commandResult.getFeedbackToUser());
    }

//    @Test
//    public void execute_duplicateCalendar_throwsCommandException() {
//        CalendarWrapper validCalendar = new CalendarWrapperBuilder().build();
//        AddCalendarCommand invalidCommand = new AddCalendarCommand(validCalendar);
//
//        ModelStub modelStub = new ModelStubAcceptingCalendarAdded();
//        modelStub.addCalendar(validCalendar);
//
//        assertThrows(CommandException.class, AddCalendarCommand.MESSAGE_DUPLICATE_CALENDAR, () ->
//                invalidCommand.execute(modelStub));
//    }
//
//    @Test
//    public void equals() {
//        CalendarWrapper validCalendar1 = new CalendarWrapperBuilder().build();
//        CalendarWrapper validCalendar2 = new CalendarWrapperBuilder()
//                .withMemberName(new MemberName("Bobby")).build();
//        assert(!validCalendar1.getMemberName().equals(validCalendar2.getMemberName()));
//
//        AddCalendarCommand addCalendarCommand1 = new AddCalendarCommand(validCalendar1);
//        AddCalendarCommand addCalendarCommand2 = new AddCalendarCommand(validCalendar2);
//
//        // same object -> returns true
//        assertTrue(addCalendarCommand1.equals(addCalendarCommand1));
//
//        // same values -> returns true
//        AddCalendarCommand addCalendarCommand1Copy = new AddCalendarCommand(validCalendar1);
//        assertTrue(addCalendarCommand1.equals(addCalendarCommand1Copy));
//
//        // different types -> returns false
//        assertFalse(addCalendarCommand2.equals("Weird string"));
//
//        // null -> returns false
//        assertFalse(addCalendarCommand2.equals(null));
//
//        // different task -> returns false
//        assertFalse(addCalendarCommand1.equals(addCalendarCommand2));
//    }

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
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingCalendarAdded extends ModelStub {
        final ObservableList<CalendarWrapper> calendars = FXCollections.observableArrayList();

        @Override
        public void addCalendar(CalendarWrapper calendar) {
            calendars.add(calendar);
        }

        @Override
        public void deleteCalendar(CalendarWrapper calendar) {
            calendars.remove(calendar);
        }

        @Override
        public ObservableList<CalendarWrapper> getFilteredCalendarList() {
            return calendars;
        }
    }
}
