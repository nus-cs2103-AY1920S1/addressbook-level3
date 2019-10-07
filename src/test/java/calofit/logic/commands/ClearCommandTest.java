package calofit.logic.commands;

import calofit.model.AddressBook;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Test;

import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalMeals.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalMeals.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
