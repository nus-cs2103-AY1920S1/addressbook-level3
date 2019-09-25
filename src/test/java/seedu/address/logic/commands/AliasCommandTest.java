package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AliasTestUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalExpenses.getTypicalAddressBook;
import static seedu.address.testutil.AliasTestUtil.ALIAS_A_TO_B;
import static seedu.address.testutil.AliasTestUtil.ALIAS_B_TO_C;
import static seedu.address.testutil.AliasTestUtil.ALIAS_C_TO_A;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class AliasCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        AliasCommand validAliasCommandOne = new AliasCommand(ALIAS_A_TO_B);
        AliasCommand validAliasCommandTwo = new AliasCommand(ALIAS_B_TO_C);

        // same object -> returns true
        assertTrue(validAliasCommandOne.equals(validAliasCommandOne));

        // same alias -> returns true
        AliasCommand validAliasCommandOneCopy = new AliasCommand(ALIAS_A_TO_B);
        assertTrue(validAliasCommandOne.equals(validAliasCommandOneCopy));

        // different types -> returns false
        assertFalse(validAliasCommandOne.equals(1));

        // null -> returns false
        assertFalse(validAliasCommandOne.equals(null));

        // different alias -> returns false
        assertFalse(validAliasCommandOne.equals(validAliasCommandTwo));
    }

    @Test
    public void execute_aliasNameIsReservedCommandWord_throwsCommandException() {

        AliasCommand command = new AliasCommand(AliasTestUtil.ALIAS_NAME_ADD);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_aliasCommandWordIsAlias_throwsCommandException() {
        AliasCommand command = new AliasCommand(AliasTestUtil.ALIAS_TO_ALIAS);
        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
