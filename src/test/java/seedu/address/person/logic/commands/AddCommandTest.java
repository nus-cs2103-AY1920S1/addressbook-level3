package seedu.address.person.logic.commands;

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
import seedu.address.person.commons.core.GuiSettings;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.AddressBook;
import seedu.address.person.model.Model;
import seedu.address.person.model.ReadOnlyAddressBook;
import seedu.address.person.model.ReadOnlyUserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.logic.LogicManager;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.util.TransactionList;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();
        TransactionList transactionList = new TransactionList();
        transactionStorage =
                new seedu.address.transaction.storage.StorageManager(FILE_PATH_TRANSACTION, model);
        transactionModel =
                new seedu.address.transaction.model.ModelManager(transactionStorage.readTransactionList());

        //For Reimbursement Storage and Manager
        reimbursementStorage =
                new seedu.address.reimbursement.storage.StorageManager(FILE_PATH_REIMBURSEMENT, transactionModel);
        reimbursementModel =
                new seedu.address.reimbursement.model.ModelManager(reimbursementStorage.readReimbursementList());

        //For Inventory Storage and Manager
        inventoryStorage =
                new seedu.address.inventory.storage.StorageManager("data/inventoryInformation.txt");
        inventoryModel =
                new seedu.address.inventory.model.ModelManager(inventoryStorage);

        //For Cashier Storage and Manager
        cashierStorage = new seedu.address.cashier.storage.StorageManager("data"
                + "/inventoryInformation.txt", "data/transactionHistory.txt", model);
        cashierModel = new seedu.address.cashier.model.ModelManager(cashierStorage);

        //For Overview Storage and Manager
        overviewStorage = new seedu.address.overview.storage.StorageManager("data/overviewInformation.txt");
        overviewModel = new seedu.address.overview.model.ModelManager(overviewStorage);

        //All logic
        transactionLogic = new
                seedu.address.transaction.logic.LogicManager(transactionModel, transactionStorage, model, storage,
                reimbursementModel, reimbursementStorage);
        reimbursementLogic = new
                seedu.address.reimbursement.logic.LogicManager(reimbursementModel, reimbursementStorage,
                transactionModel, transactionStorage, model);

        inventoryLogic = new
                seedu.address.inventory.logic.LogicManager(cashierModel, cashierStorage,
                inventoryModel, inventoryStorage);

        cashierLogic = new seedu.address.cashier.logic.LogicManager(cashierModel, cashierStorage, model, storage,
                reimbursementModel, reimbursementStorage, transactionModel, transactionStorage, inventoryModel,
                inventoryStorage);

        overviewLogic = new seedu.address.overview.logic.LogicManager(overviewModel, overviewStorage, transactionLogic,
                inventoryLogic);

        Logic logic = new LogicManager();
        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, );

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
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
        public Person getPersonByName(String name) {
            throw new AssertionError("This method should not be called.");
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

}
