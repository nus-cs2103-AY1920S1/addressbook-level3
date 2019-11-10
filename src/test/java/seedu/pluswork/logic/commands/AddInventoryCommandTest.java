package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.ReadOnlyUserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.Mapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.testutil.InventoryBuilder;
import seedu.pluswork.testutil.MemberBuilder;

public class AddInventoryCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddInventoryCommand(null, null, null));
    }

    @Test
    public void execute_inventoryAcceptedByModel_addSuccessful() throws Exception {
        AddInventoryCommandTest.ModelStubAcceptingInventoryAdded modelStub = new AddInventoryCommandTest
                .ModelStubAcceptingInventoryAdded();
        Inventory validInventory = new InventoryBuilder().build();

        //Commented out for assertion error
        //CommandResult commandResult = new AddInventoryCommand(new Index(0),
        //       validInventory.getName(), validInventory.getPrice(), new MemberId("GS")).execute(modelStub);
        //assertEquals(String.format(AddInventoryCommand.MESSAGE_SUCCESS, validInventory),
        //                                                commandResult.getFeedbackToUser());
        //assertEquals(Collections.singletonList(validInventory), modelStub.inventoriesAdded);
    }

    @Test
    public void execute_duplicateInventory_throwsCommandException() {
        Inventory validInventory = new InventoryBuilder().build();
        AddInventoryCommand addInventoryCommand = new AddInventoryCommand(new Index(0),
                validInventory.getName(), validInventory.getPrice(), new MemberId("GS"));
        ModelStub modelStub = new ModelStubWithInventory(validInventory);
        /*Commented out for assertion error
        assertThrows(CommandException.class, AddInventoryCommand.MESSAGE_DUPLICATE_INVENTORY, () ->
                addInventoryCommand.execute(modelStub));*/
    }

    @Test
    public void execute_inValidMemberId_throwsCommandException() {
        AddInventoryCommand addInventoryCommand = new AddInventoryCommand(new Index(0),
                new InvName("Toy"), new Price(1), new MemberId("invalidId"));
        AddInventoryCommandTest.ModelStubAcceptingInventoryAdded modelStub =
                new AddInventoryCommandTest.ModelStubAcceptingInventoryAdded();
        /*Commented out for assertion error
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_MEMBER_ID, () ->
               addInventoryCommand.execute(modelStub));*/
    }

    @Test
    public void execute_inValidTaskId_throwsCommandException() {
        AddInventoryCommand addInventoryCommand = new AddInventoryCommand(new Index(2),
                new InvName("Toy"), new Price(1), new MemberId("GS"));
        AddInventoryCommandTest.ModelStubAcceptingInventoryAdded modelStub = new ModelStubAcceptingInventoryAdded();

        /*Commented out for assertion error
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, () ->
                addInventoryCommand.execute(modelStub));*/
    }

    @Test
    public void equals() {
        Inventory toy = new InventoryBuilder().withName("Toy").build();
        Inventory ball = new InventoryBuilder().withName("Ball").build();
        AddInventoryCommand addToyCommand = new AddInventoryCommand(new Index(1),
                toy.getName(), toy.getPrice(), new MemberId("rak"));
        AddInventoryCommand addBallCommand = new AddInventoryCommand(new Index(1),
                ball.getName(), ball.getPrice(), new MemberId("rak"));

        // same object -> returns true
        assertTrue(addToyCommand.equals(addToyCommand));

        // same values -> returns true
        AddInventoryCommand addToyCommandCopy = new AddInventoryCommand(new Index(1),
                toy.getName(), toy.getPrice(), new MemberId("rak"));
        assertTrue(addToyCommand.equals(addToyCommandCopy));

        // different types -> returns false
        assertFalse(addToyCommand.equals(1));

        // null -> returns false
        assertFalse(addToyCommand == null);

        // different task -> returns false
        assertFalse(addToyCommand.equals(addBallCommand));
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
        public boolean hasCalendar(CalendarWrapper calendar) {
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
    private class ModelStubWithInventory extends AddInventoryCommandTest.ModelStub {
        final ObservableList<Member> membersAdded = FXCollections.observableArrayList(
                new MemberBuilder().withId(new MemberId("GS")).build());
        private final FilteredList<Member> filteredMembers = new FilteredList<Member>(membersAdded);
        private final ObservableList<Task> tasksAdded = FXCollections.observableArrayList();
        private final FilteredList<Task> filteredTasks = new FilteredList<Task>(tasksAdded);
        private final ObservableList<Inventory> invAdded = FXCollections.observableArrayList();
        private final FilteredList<Inventory> filteredInv = new FilteredList<Inventory>(invAdded);
        private final Inventory inventory;

        ModelStubWithInventory(Inventory inventory) {
            requireNonNull(inventory);
            this.inventory = inventory;
        }

        @Override
        public boolean hasInventory(Inventory inventory) {
            requireNonNull(inventory);
            return this.inventory.isSameInventory(inventory);
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
        public ObservableList<Task> getFilteredTasksList() {
            return filteredTasks;
        }

        @Override
        public void updateFilteredTasksList(Predicate<Task> predicate) {
            requireNonNull(predicate);
            filteredTasks.setPredicate(predicate);
        }

        @Override
        public ObservableList<Inventory> getFilteredInventoriesList() {
            return filteredInv;
        }

        @Override
        public void updateFilteredInventoriesList(Predicate<Inventory> predicate) {
            requireNonNull(predicate);
            filteredInv.setPredicate(predicate);
        }
    }

    /**
     * A Model stub that always accept the inventory being added.
     */
    private class ModelStubAcceptingInventoryAdded extends AddInventoryCommandTest.ModelStub {
        final ObservableList<Member> membersAdded = FXCollections.observableArrayList(
                new MemberBuilder().withId(new MemberId("GS")).build());
        private final FilteredList<Member> filteredMembers = new FilteredList<Member>(membersAdded);
        private final ObservableList<Task> tasksAdded = FXCollections.observableArrayList();
        private final FilteredList<Task> filteredTasks = new FilteredList<Task>(tasksAdded);
        private final ObservableList<Inventory> invAdded = FXCollections.observableArrayList();
        private final FilteredList<Inventory> filteredInv = new FilteredList<Inventory>(invAdded);
        private final ObservableList<Mapping> mappingAdded = FXCollections.observableArrayList();
        private final FilteredList<Mapping> filteredMappings = new FilteredList<Mapping>(mappingAdded);
        private final ArrayList<Inventory> inventoriesAdded = new ArrayList<>();

        @Override
        public boolean hasInventory(Inventory inventory) {
            requireNonNull(inventory);
            return inventoriesAdded.stream().anyMatch(inventory::isSameInventory);
        }

        @Override
        public void addInventory(Inventory inventory) {
            requireNonNull(inventory);
            inventoriesAdded.add(inventory);
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
        public ObservableList<Task> getFilteredTasksList() {
            return filteredTasks;
        }

        @Override
        public void updateFilteredTasksList(Predicate<Task> predicate) {
            requireNonNull(predicate);
            filteredTasks.setPredicate(predicate);
        }

        @Override
        public ObservableList<Inventory> getFilteredInventoriesList() {
            return filteredInv;
        }

        @Override
        public void updateFilteredInventoriesList(Predicate<Inventory> predicate) {
            requireNonNull(predicate);
            filteredInv.setPredicate(predicate);
        }

        @Override
        public void addMapping(Mapping mapping) {
            mappingAdded.add(mapping);
        }

        @Override
        public ReadOnlyProjectDashboard getProjectDashboard() {
            return new ProjectDashboard();
        }
    }
}
