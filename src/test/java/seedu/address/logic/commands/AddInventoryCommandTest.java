package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.testutil.InventoryBuilder;

public class AddInventoryCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddInventoryCommand(null,null));
    }

    @Test
    public void execute_inventoryAcceptedByModel_addSuccessful() throws Exception {
        AddInventoryCommandTest.ModelStubAcceptingInventoryAdded modelStub = new AddInventoryCommandTest.ModelStubAcceptingInventoryAdded();
        Inventory validInventory = new InventoryBuilder().build();

        CommandResult commandResult = new AddInventoryCommand(new Index(1),
                validInventory.getName(), validInventory.getPrice()).execute(modelStub);

        assertEquals(String.format(AddInventoryCommand.MESSAGE_SUCCESS, validInventory), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInventory), modelStub.inventoriesAdded);
    }

    @Test
    public void execute_duplicateInventory_throwsCommandException() {
        Inventory validInventory = new InventoryBuilder().build();
        AddInventoryCommand addInventoryCommand = new AddInventoryCommand(new Index(1),
                                                            validInventory.getName(), validInventory.getPrice());
        AddInventoryCommandTest.ModelStub modelStub = new AddInventoryCommandTest
                                                            .ModelStubWithInventory(validInventory);

        assertThrows(CommandException.class, addInventoryCommand.MESSAGE_DUPLICATE_INVENTORY, () ->
                addInventoryCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Inventory alice = new InventoryBuilder().withName("Alice").build();
        Inventory bob = new InventoryBuilder().withName("Bob").build();
        AddInventoryCommand addAliceCommand = new AddInventoryCommand(new Index(1),
                                                                        alice.getName(), alice.getPrice());
        AddInventoryCommand addBobCommand = new AddInventoryCommand(new Index(1),
                                                                        bob.getName(), bob.getPrice());

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddInventoryCommand addAliceCommandCopy = new AddInventoryCommand(new Index(1),
                                                                    alice.getName(), alice.getPrice());
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
        public void deleteMember(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMember(Member target, Member editedMember) {
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
            return 0;
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
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithInventory extends AddInventoryCommandTest.ModelStub {
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
    }

    /**
     * A Model stub that always accept the inventory being added.
     */
    private class ModelStubAcceptingInventoryAdded extends AddInventoryCommandTest.ModelStub {
        final ArrayList<Inventory> inventoriesAdded = new ArrayList<>();

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
        public ReadOnlyProjectDashboard getProjectDashboard() {
            return new ProjectDashboard();
        }
    }
}
