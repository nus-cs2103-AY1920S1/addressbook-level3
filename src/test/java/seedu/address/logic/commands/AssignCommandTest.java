package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.mapping.Mapping;
import seedu.address.model.mapping.TasMemMapping;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.settings.ClockFormat;
import seedu.address.model.settings.Theme;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.task.Task;
import seedu.address.testutil.MappingBuilder;
import seedu.address.testutil.MemberBuilder;
import seedu.address.testutil.TaskBuilder;

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

    /*@Test
    public void execute_invalidTaskId_throwsCommandException() {
        Member validMember = new MemberBuilder().build();
        AddMemberCommand addMemberCommand = new AddMemberCommand(validMember);
        AddMemberCommandTest.ModelStub modelStub = new AddMemberCommandTest.ModelStubWithMember(validMember);

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
    }*/

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
        public void addMapping(InvMemMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMapping(InvTasMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMapping(TasMemMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMapping(InvMemMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMapping(InvTasMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMapping(TasMemMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMapping(InvMemMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMapping(InvTasMapping mapping) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMapping(TasMemMapping mapping) {
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
        public void setClockFormat(ClockFormat newClockFormat) {
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
        public boolean hasMapping(TasMemMapping mapping) {
            requireNonNull(mapping);
            return this.mapping.isSameMapping(mapping);
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
        public boolean hasMapping(TasMemMapping mapping) {
            requireNonNull(mapping);
            return mappingsAdded.stream().anyMatch(mapping::isSameMapping);
        }

        @Override
        public void addMapping(TasMemMapping mapping) {
            requireNonNull(mapping);
            mappingsAdded.add(mapping);
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
