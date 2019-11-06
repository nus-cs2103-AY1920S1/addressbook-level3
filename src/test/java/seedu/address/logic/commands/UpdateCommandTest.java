package seedu.address.logic.commands;

//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
//import seedu.address.model.BankAccount;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.UserState;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.testutil.BankOperationBuilder;
import seedu.address.testutil.UpdateTransactionDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model,
 * UndoCommand and RedoCommand) and unit tests for UpdateCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalUserState(), new UserPrefs());

    private String typeTransaction = "t";
    private String typeBudget = "b";
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        BankAccountOperation editedTransaction = new BankOperationBuilder().build();
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder(editedTransaction).build();
        UpdateCommand updateCommand = new UpdateCommand(typeTransaction, INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTRY_SUCCESS, editedTransaction);
        Model expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
        BankAccountOperation txn = model.getFilteredTransactionList().get(0);
        expectedModel.set(txn, editedTransaction);
        // assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        BankAccountOperation lastTransaction = model.getFilteredTransactionList()
                .get(indexLastTransaction.getZeroBased());

        BankOperationBuilder transactionInList = new BankOperationBuilder(lastTransaction);
        BankAccountOperation editedTransaction = transactionInList.withAmount("100")
                .withDate("10102019").withCategories("food").withDescription("milk").build();

        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder().withAmount("100")
                .withDate("10102019").withCategories("food").build();
        UpdateCommand updateCommand = new UpdateCommand(typeTransaction, indexLastTransaction, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTRY_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
        expectedModel.set(lastTransaction, editedTransaction);

        // assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }
    /*
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION, new EditPersonDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_TRANSACTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new BankAccount(model.getBankAccount()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_TRANSACTION);

        Transaction transactionInFilteredList = model
                .getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        // TODO: FIX
        Transaction editedTransaction = new BankOperationBuilder(transactionInFilteredList).withAmount("1").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new BankAccount(model.getBankAccount()), new UserPrefs());
        expectedModel.set(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTransactionUnfilteredList_failure() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_TRANSACTION.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_TRANSACTION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateTransactionFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_TRANSACTION);

        // edit person in filtered list into a duplicate in address book
        Transaction transactionInList = model
                .getBankAccount().getTransactionHistory().get(INDEX_SECOND_TRANSACTION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION,
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
        showPersonAtIndex(model, INDEX_FIRST_TRANSACTION);
        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBankAccount().getTransactionHistory().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TRANSACTION, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TRANSACTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TRANSACTION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TRANSACTION, DESC_BOB)));
    }
    */


}
