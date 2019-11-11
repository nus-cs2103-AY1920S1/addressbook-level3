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

}
