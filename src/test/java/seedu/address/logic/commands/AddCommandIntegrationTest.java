package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalTransactions.getTypicalBankAccount;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBankAccount(), new UserPrefs());
    }

    // TODO: Refactor into InCommandIntegrationTest
    /*
    @Test
    public void execute_newPerson_success() {
        Transaction validTransaction = new TransactionBuilder().build();

        Model expectedModel = new ModelManager(model.getBankAccount(), new UserPrefs());
        expectedModel.addTransaction(validTransaction);

        assertCommandSuccess(new AddCommand(validTransaction), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validTransaction), expectedModel);
    }


    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Transaction transactionInList = model.getBankAccount().getTransactionHistory().get(0);
        assertCommandFailure(new AddCommand(transactionInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }
     */

}
