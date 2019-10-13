package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyRecordBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RecordBook;
import seedu.address.model.calendar.DateTime;
import seedu.address.model.person.Person;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.Record;
import seedu.address.model.record.Weight;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodMap;


public class AddCommandTest {

    private LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
    private LocalTime lt = LocalTime.of(8, 0, 0);
    private DateTime dt = new DateTime(ld, lt);
    private BloodSugar bs = new BloodSugar(new Concentration("12.34"), dt);
    private Bmi bmi = new Bmi(new Height("12.34"), new Weight("23.34"), dt);

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_recordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecordAdded modelStub = new ModelStubAcceptingRecordAdded();

        //        Person validPerson = new PersonBuilder().build();
        CommandResult commandResult = new AddCommand(bs).execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, bs), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(bs), modelStub.recordsAdded);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        //        Person validPerson = new PersonBuilder().build();

        AddCommand addCommand = new AddCommand(bs);
        ModelStub modelStub = new ModelStubWithRecord(bs);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_RECORD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddCommand addBloodSugarCommand = new AddCommand(bs);
        AddCommand addBmiCommand = new AddCommand(bmi);

        // same object -> returns true
        assertTrue(addBloodSugarCommand.equals(addBloodSugarCommand));

        // same values -> returns true
        AddCommand addBloodSugarCommandCopy = new AddCommand(bs);
        assertTrue(addBloodSugarCommand.equals(addBloodSugarCommandCopy));

        // different types -> returns false
        assertFalse(addBloodSugarCommand.equals(1));

        // null -> returns false
        assertFalse(addBloodSugarCommand.equals(null));

        // different record -> returns false
        assertFalse(addBloodSugarCommand.equals(addBmiCommand));
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
        public void updateFilteredFoodMap(Predicate<Food> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRecordBook getRecordBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FoodMap getFoodMap() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingRecordAdded extends ModelStub {
        final ArrayList<Record> recordsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addRecord(Record record) {
            requireNonNull(record);
            recordsAdded.add(record);
        }

        @Override
        public ReadOnlyRecordBook getRecordBook() {
            return new RecordBook();
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithRecord extends ModelStub {
        private final Record record;

        ModelStubWithRecord(Record record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

}
