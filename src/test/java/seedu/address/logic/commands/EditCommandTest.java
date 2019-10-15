package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalTransactions.getTypicalBankAccount;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBankAccount(), new UserPrefs());

    /*
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new BankAccount(model.getBankAccount()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        TransactionBuilder transactionInList = new TransactionBuilder(lastTransaction);
        // TODO : FIX
        Transaction editedTransaction = transactionInList.withAmount("1").withDate("1")
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new BankAccount(model.getBankAccount()), new UserPrefs());
        expectedModel.setTransaction(lastTransaction, editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new BankAccount(model.getBankAccount()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Transaction transactionInFilteredList = model
                .getFilteredTransactionList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        // TODO: FIX
        Transaction editedTransaction = new TransactionBuilder(transactionInFilteredList).withAmount("1").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new BankAccount(model.getBankAccount()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTransactionUnfilteredList_failure() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateTransactionFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Transaction transactionInList = model
                .getBankAccount().getTransactionHistory().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(transactionInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
     */

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    /*
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBankAccount().getTransactionHistory().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }
     */
}
