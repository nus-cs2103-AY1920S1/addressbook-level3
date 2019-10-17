package seedu.address.transaction.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_TRANSACTION_EDITED;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

import org.junit.jupiter.api.Test;

class EditCommandTest {
    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_filteredList_allFieldsSpecified_successful() {
        Person validPerson = new PersonBuilder().build();
        Transaction editedTransaction = new TransactionBuilder(validPerson).build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction)
                .build();
        EditCommand editCommand = new EditCommand(1, descriptor);

        String expectedMessage = String.format(MESSAGE_TRANSACTION_EDITED, editedTransaction);

        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.setTransaction(model.getFilteredList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        int indexLastTransaction = model.getFilteredList().size();
        Transaction lastTransaction = model.getFilteredList().get(indexLastTransaction);

        TransactionBuilder transactionInList = new TransactionBuilder(lastTransaction.getPerson());
        Transaction editedTransaction = transactionInList.withAmount(80.0).withDate("01-Jan-2019")
                .build();

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withAmount(80.0)
                .withDate("01-Jan-2019").build();
        EditCommand editCommand = new EditCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(MESSAGE_TRANSACTION_EDITED, editedTransaction);

        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.setTransaction(lastTransaction, editedTransaction);;

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(0, new EditCommand.EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredList().get(0);

        String expectedMessage = String.format(MESSAGE_TRANSACTION_EDITED, editedTransaction);

        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());

        Transaction transactionInFilteredList = model.getFilteredList().get(1);
        Transaction editedTransaction = new TransactionBuilder(transactionInFilteredList.getPerson())
                .withDate("01-Jan-2019").build();
        EditCommand editCommand = new EditCommand(1,
                new EditTransactionDescriptorBuilder().withDate("01-Jan-2019").build());

        String expectedMessage = String.format(MESSAGE_TRANSACTION_EDITED, editedTransaction);

        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.setTransaction(model.getFilteredList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Transaction firstTransaction = model.getFilteredList().get(0);
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().build();
        EditCommand editCommand = new EditCommand(1, descriptor);

        assertCommandFailure(editCommand, model, TransactionMessages.MESSAGE_DUPLICATE_TRANSACTION, personModel);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
