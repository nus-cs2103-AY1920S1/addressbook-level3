package budgetbuddy.logic.commands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import budgetbuddy.model.AddressBook;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(new LoansManager(), new RuleManager(), getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new LoansManager(), new RuleManager(),
                getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
