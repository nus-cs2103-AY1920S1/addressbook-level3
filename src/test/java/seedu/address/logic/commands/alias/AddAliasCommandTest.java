package seedu.address.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.AliasTestUtil.ALIAS_A_TO_B;
import static seedu.address.testutil.AliasTestUtil.ALIAS_B_TO_C;
import static seedu.address.testutil.TypicalExpenses.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AliasTestUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class AddAliasCommandTest {

    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    private Model expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void equals() {

        AddAliasCommand validAliasCommandOne = new AddAliasCommand(ALIAS_A_TO_B);
        AddAliasCommand validAddAliasCommandTwo = new AddAliasCommand(ALIAS_B_TO_C);

        // same object -> returns true
        assertTrue(validAliasCommandOne.equals(validAliasCommandOne));

        // same alias -> returns true
        AddAliasCommand validAliasCommandOneCopy = new AddAliasCommand(ALIAS_A_TO_B);
        assertTrue(validAliasCommandOne.equals(validAliasCommandOneCopy));

        // different types -> returns false
        assertFalse(validAliasCommandOne.equals(1));

        // null -> returns false
        assertFalse(validAliasCommandOne.equals(null));

        // different alias -> returns false
        assertFalse(validAliasCommandOne.equals(validAddAliasCommandTwo));
    }

    @Test
    public void run_aliasNameIsReservedCommandWord_throwsCommandException() {
        AddAliasCommand command = new AddAliasCommand(AliasTestUtil.ALIAS_NAME_ADD);
        assertThrows(CommandException.class, () -> command.run(model));
    }

    // removed test, alias mapping to alias creation does not result in unwanted behaviour

    @Test
    public void run_aliasCommandIsValid_success() {
        expectedModel.commitModel("");
        expectedModel.addUserAlias(ALIAS_A_TO_B);
        assertCommandSuccess(
                new AddAliasCommand(ALIAS_A_TO_B), model,
                String.format(AddAliasCommand.MESSAGE_SUCCESS, ALIAS_A_TO_B.getAliasName()), expectedModel);
    }
}
