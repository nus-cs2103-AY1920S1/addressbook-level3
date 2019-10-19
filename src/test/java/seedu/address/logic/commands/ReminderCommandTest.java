package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.testutil.ReminderBuilder;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.UniqueFoodList;

class ReminderCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderCommand(null));
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminder modelStub = new ModelStubAcceptingReminder();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = new ReminderCommand(validReminder).execute(modelStub);

        assertEquals(String.format(ReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.getFeedbackToUser());
        //assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder validReminder = new ReminderBuilder().build();
        ReminderCommand reminderCommand = new ReminderCommand(validReminder);
        ModelStub modelStub = new ModelStubWithReminder(validReminder);
        /*
        assertThrows(CommandException.class, ReminderCommand.MESSAGE_DUPLICATE_REMINDER,
                () -> reminderCommand.execute(modelStub));
         */
    }

    @Test
    public void equals() {
        Reminder insulinInjection = new ReminderBuilder().build();
        Reminder wakeUp = new ReminderBuilder().withDescription("Wake up").build();
        ReminderCommand insulinInjectionCommand = new ReminderCommand(insulinInjection);
        ReminderCommand wakeUpCommand = new ReminderCommand(wakeUp);

        // same object -> returns true
        assertTrue(insulinInjection.equals(insulinInjection));

        // same values -> returns true
        ReminderCommand insulinInjectionCommandCopy = new ReminderCommand(insulinInjection);
        assertTrue(insulinInjectionCommand.equals(insulinInjectionCommandCopy));

        // different types -> returns false
        assertFalse(insulinInjectionCommand.equals(1));

        // null -> returns false
        assertFalse(insulinInjectionCommand.equals(null));

        // different reminders -> returns false
        assertFalse(insulinInjectionCommand.equals(wakeUpCommand));
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
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
        public void addRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueRecordList getUniqueRecordListObject() {
            return null;
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return null;
        }

        @Override
        public ObservableList<Record> getFilterRecordList() {
            return null;
        }

        @Override
        public boolean hasRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(Record record) {

        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueFoodList getUniqueFoodListObject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodList(UniqueFoodList newFoodList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFilterFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodList(Predicate<Food> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecordList(UniqueRecordList newRecordList) {

        }
    }

    /**
     * A Model stub that contains a single reminder.
     */
    private class ModelStubWithReminder extends ModelStub {
        private final Reminder reminder;

        ModelStubWithReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return this.reminder.isSameReminder(reminder);
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminder extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return remindersAdded.stream().anyMatch(reminder::isSameReminder);
        }

        @Override
        public void addReminder(Reminder reminder) {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
