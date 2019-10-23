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

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.history.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;
import seedu.address.model.person.Person;
import seedu.address.testutil.WorkerBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_entityAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEntityAdded modelStub = new ModelStubAcceptingEntityAdded();
        Worker validWorker = new WorkerBuilder().build();

        CommandResult commandResult = new AddCommand(validWorker).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validWorker), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWorker), modelStub.entitiesAdded);
    }

    @Test
    public void execute_duplicateEntity_throwsCommandException() {
        Worker validWorker = new WorkerBuilder().build();
        AddCommand addCommand = new AddCommand(validWorker);
        ModelStub modelStub = new ModelStubWithEntity(validWorker);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ENTITY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Worker zach = new WorkerBuilder().withName("Zach").build();
        Worker xenia = new WorkerBuilder().withName("Xenia").build();
        AddCommand addZachCommand = new AddCommand(zach);
        AddCommand addXeniaCommand = new AddCommand(xenia);

        // same object -> returns true
        assertTrue(addZachCommand.equals(addZachCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(zach);
        assertTrue(addZachCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addZachCommand.equals(1));

        // null -> returns false
        assertFalse(addZachCommand.equals(null));

        // different entity -> returns false
        assertFalse(addZachCommand.equals(addXeniaCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addExecutedCommand(UndoableCommand command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UndoableCommand getExecutedCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUndoneCommand(UndoableCommand command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UndoableCommand getUndoneCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearUndoHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CommandHistory getUndoHistory() {
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEntity(Entity entity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEntity(Entity entity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNotif(Notif notif) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEntity(Entity target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEntity(Entity target, Entity editedEntity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNotif(Notif notif) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNotif(Notif target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNotif(Notif target, Notif editedEntity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNumberOfNotifs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Worker> getFilteredWorkerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWorkerList(Predicate<Worker> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Body> getFilteredBodyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBodyList(Predicate<Body> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFridgeList(Predicate<Fridge> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<? extends Entity> getFilteredEntityList(String entityType) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Notif> getFilteredNotifList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Fridge> getFilteredFridgeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredNotifList(Predicate<Notif> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public ReadOnlyProperty<Body> selectedBodyProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Body getSelectedBody() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedBody(Body body) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single entity.
     */
    private class ModelStubWithEntity extends ModelStub {
        private final Entity entity;

        ModelStubWithEntity(Entity entity) {
            requireNonNull(entity);
            this.entity = entity;
        }

        @Override
        public boolean hasEntity(Entity entity) {
            requireNonNull(entity);
            return this.entity.isSameEntity(entity);
        }
    }

    /**
     * A Model stub that always accept the entity being added.
     */
    private class ModelStubAcceptingEntityAdded extends ModelStub {
        final ArrayList<Entity> entitiesAdded = new ArrayList<>();

        @Override
        public boolean hasEntity(Entity entity) {
            requireNonNull(entity);
            return entitiesAdded.stream().anyMatch(entity::isSameEntity);
        }

        @Override
        public void addEntity(Entity entity) {
            requireNonNull(entity);
            entitiesAdded.add(entity);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void addExecutedCommand(UndoableCommand command) {
            return;
        }
    }

}
