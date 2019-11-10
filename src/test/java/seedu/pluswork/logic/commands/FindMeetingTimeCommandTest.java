package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.parser.exceptions.ParseException;
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

public class FindMeetingTimeCommandTest {
    private static final LocalDateTime SAMPLE_START_DATE1;
    private static final LocalDateTime SAMPLE_START_DATE2;
    private static final LocalDateTime SAMPLE_START_DATE3;
    private static final LocalDateTime SAMPLE_END_DATE1;
    private static final LocalDateTime SAMPLE_END_DATE2;
    private static final LocalDateTime SAMPLE_END_DATE3;
    private static final Duration SAMPLE_DURATION1 = Duration.ofHours(20);
    private static final Duration SAMPLE_DURATION2 = Duration.ofHours(1);
    private static final Duration SAMPLE_DURATION3 = Duration.ofHours(2);

    static {
        LocalDateTime startTmp1 = null;
        LocalDateTime startTmp2 = null;
        LocalDateTime startTmp3 = null;
        LocalDateTime endTmp1 = null;
        LocalDateTime endTmp2 = null;
        LocalDateTime endTmp3 = null;
        try {
            startTmp1 = DateTimeUtil.parseDateTime("10-11-2019 18:00");
            startTmp2 = DateTimeUtil.parseDateTime("01-01-2001 01:00");
            startTmp3 = DateTimeUtil.parseDateTime("30-03-2020 23:59");
            endTmp1 = DateTimeUtil.parseDateTime("15-11-2019 19:00");
            endTmp2 = DateTimeUtil.parseDateTime("15-10-2030 23:59");
            endTmp3 = DateTimeUtil.parseDateTime("31-03-2020 01:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        requireNonNull(startTmp1);
        requireNonNull(startTmp2);
        requireNonNull(startTmp3);
        requireNonNull(endTmp1);
        requireNonNull(endTmp2);
        requireNonNull(endTmp3);
        assert(startTmp1.isBefore(endTmp1));
        assert(startTmp2.isBefore(endTmp2));
        assert(startTmp3.isBefore(endTmp3));
        SAMPLE_START_DATE1 = startTmp1;
        SAMPLE_START_DATE2 = startTmp2;
        SAMPLE_START_DATE3 = startTmp3;
        SAMPLE_END_DATE1 = endTmp1;
        SAMPLE_END_DATE2 = endTmp2;
        SAMPLE_END_DATE3 = endTmp3;
    }

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new FindMeetingTimeCommand(null, null, null));
        assertThrows(NullPointerException.class, ()
            -> new FindMeetingTimeCommand(SAMPLE_END_DATE1, null, SAMPLE_DURATION1));
        assertThrows(NullPointerException.class, ()
            -> new FindMeetingTimeCommand(null, null, SAMPLE_DURATION3));
        assertThrows(NullPointerException.class, ()
            -> new FindMeetingTimeCommand(SAMPLE_START_DATE1, SAMPLE_END_DATE3, null));
        assertThrows(NullPointerException.class, ()
            -> new FindMeetingTimeCommand(null, SAMPLE_END_DATE1, null));
    }

    @Test
    public void execute_findMeetingTimeAcceptedByModel_successful() throws Exception {
        MeetingQuery validMeetingQuery = new MeetingQueryBuilder().build();
        MeetingQuery validMeetingQueryEmptyList =
                new MeetingQueryBuilder().withMeetings(new ArrayList<Meeting>()).build();

        ModelStub modelStub = new ModelStubWithMeetingQuery(validMeetingQuery);
        ModelStub modelStubEmptyList = new ModelStubWithMeetingQuery(validMeetingQueryEmptyList);

        FindMeetingTimeCommand validCommand1
                = new FindMeetingTimeCommand(SAMPLE_START_DATE1, SAMPLE_END_DATE1, SAMPLE_DURATION1);
        FindMeetingTimeCommand validCommand2
                = new FindMeetingTimeCommand(SAMPLE_START_DATE2, SAMPLE_END_DATE2, SAMPLE_DURATION2);

        CommandResult commandSuccess1 = validCommand1.execute(modelStub);
        CommandResult commandSuccess2 = validCommand2.execute(modelStub);

        assertEquals(String.format(FindMeetingTimeCommand.MESSAGE_SUCCESS,
                DateTimeUtil.displayDateTime(SAMPLE_START_DATE1),
                DateTimeUtil.displayDateTime(SAMPLE_END_DATE1)),
                commandSuccess1.getFeedbackToUser());
        assertEquals(String.format(FindMeetingTimeCommand.MESSAGE_SUCCESS,
                DateTimeUtil.displayDateTime(SAMPLE_START_DATE2),
                DateTimeUtil.displayDateTime(SAMPLE_END_DATE2)),
                commandSuccess2.getFeedbackToUser());

        CommandResult commandFail1 = validCommand1.execute(modelStubEmptyList);
        CommandResult commandFail2 = validCommand2.execute(modelStubEmptyList);

        assertEquals(String.format(FindMeetingTimeCommand.MESSAGE_FAILURE,
                DateTimeUtil.displayDateTime(SAMPLE_START_DATE1),
                DateTimeUtil.displayDateTime(SAMPLE_END_DATE1)),
                commandFail1.getFeedbackToUser());
        assertEquals(String.format(FindMeetingTimeCommand.MESSAGE_FAILURE,
                DateTimeUtil.displayDateTime(SAMPLE_START_DATE2),
                DateTimeUtil.displayDateTime(SAMPLE_END_DATE2)),
                commandFail2.getFeedbackToUser());
    }

//    @Test
//    public void execute_duplicateMeeting_throwsCommandException() {
//        Index validIndex = new Index(1);
//        MeetingQuery validMeetingQuery = new MeetingQueryBuilder().build();
//        Meeting validMeeting = validMeetingQuery.getMeetingList().get(validIndex.getZeroBased());
//
//        ModelStub modelStub = new ModelStubWithMeetingQuery(validMeetingQuery);
//        modelStub.addMeeting(validMeeting);
//
//        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(validIndex);
//        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_DUPLICATE_MEETING, () ->
//                addMeetingCommand.execute(modelStub));
//    }
//
//    @Test
//    public void execute_meetingIndexOutOfBounds_throwsCommandException() {
//        MeetingQuery validMeetingQuery = new MeetingQueryBuilder().build();
//        ModelStub modelStub = new ModelStubWithMeetingQuery(validMeetingQuery);
//        int validIndexUpperBound = validMeetingQuery.getMeetingList().size();
//
//        Index invalidIndex1 = new Index(20);
//        Index invalidIndex2 = new Index(validIndexUpperBound);
//        Index invalidIndex3 = new Index(validIndexUpperBound + 1);
//        Index invalidIndex4 = new Index(validIndexUpperBound + 200);
//
//        assert(invalidIndex1.getZeroBased() >= validIndexUpperBound);
//        assert(invalidIndex2.getZeroBased() >= validIndexUpperBound);
//        assert(invalidIndex3.getZeroBased() >= validIndexUpperBound);
//        assert(invalidIndex4.getZeroBased() >= validIndexUpperBound);
//
//        AddMeetingCommand invalidCommand1 = new AddMeetingCommand(invalidIndex1);
//        AddMeetingCommand invalidCommand2 = new AddMeetingCommand(invalidIndex2);
//        AddMeetingCommand invalidCommand3 = new AddMeetingCommand(invalidIndex3);
//        AddMeetingCommand invalidCommand4 = new AddMeetingCommand(invalidIndex4);
//
//        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
//                invalidCommand1.execute(modelStub));
//        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
//                invalidCommand2.execute(modelStub));
//        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
//                invalidCommand3.execute(modelStub));
//        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX, () ->
//                invalidCommand4.execute(modelStub));
//    }
//
//    @Test
//    public void execute_nullMeetingQuery_throwsCommandException() {
//        MeetingQuery nullMeetingQuery = null;
//        ModelStub modelStub = new ModelStubWithMeetingQuery(nullMeetingQuery);
//
//        Index validIndex1 = new Index(1);
//        Index validIndex2 = new Index(123934);
//        Index validIndex3 = new Index(99999999);
//        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(validIndex1);
//        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(validIndex2);
//        AddMeetingCommand addMeetingCommand3 = new AddMeetingCommand(validIndex3);
//
//        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_INVALID_MEETING_REQUEST, () ->
//                addMeetingCommand1.execute(modelStub));
//        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_INVALID_MEETING_REQUEST, () ->
//                addMeetingCommand2.execute(modelStub));
//        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_INVALID_MEETING_REQUEST, () ->
//                addMeetingCommand3.execute(modelStub));
//    }
//
//    @Test
//    public void equals() {
//        Index testIndex1 = new Index(1);
//        Index testIndex2 = new Index(200000000);
//        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(testIndex1);
//        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(testIndex2);
//
//        // same object -> returns true
//        assertTrue(addMeetingCommand1.equals(addMeetingCommand1));
//
//        // same values -> returns true
//        AddMeetingCommand addMeetingCommand1Copy = new AddMeetingCommand(testIndex1);
//        assertTrue(addMeetingCommand1.equals(addMeetingCommand1Copy));
//
//        // different types -> returns false
//        assertFalse(addMeetingCommand2.equals(1));
//
//        // null -> returns false
//        assertFalse(addMeetingCommand2.equals(null));
//
//        // different task -> returns false
//        assertFalse(addMeetingCommand1.equals(addMeetingCommand2));
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
     * A Model stub that contains a single task.
     */
    private class ModelStubWithMeetingQuery extends ModelStub {
        final MeetingQuery meetingQuery;

        private ModelStubWithMeetingQuery(MeetingQuery meetingQuery) {
            this.meetingQuery = meetingQuery;
        }

        @Override
        public MeetingQuery getMeetingQuery() {
            return meetingQuery;
        }

        @Override
        public void findMeetingTime(LocalDateTime startDate, LocalDateTime endDate, Duration duration) {

        }
    }
}
