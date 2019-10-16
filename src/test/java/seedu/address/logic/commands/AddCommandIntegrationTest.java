package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSpendings.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.spending.Spending;
import seedu.address.testutil.SpendingBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newSpending_success() {
        Spending validSpending = new SpendingBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addSpending(validSpending);

        assertCommandSuccess(new AddCommand(validSpending), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validSpending), expectedModel);
    }

    @Test
    public void execute_duplicateSpending_throwsCommandException() {
        Spending spendingInList = model.getAddressBook().getSpendingList().get(0);
        assertCommandFailure(new AddCommand(spendingInList), model, AddCommand.MESSAGE_DUPLICATE_SPENDING);
    }

}
