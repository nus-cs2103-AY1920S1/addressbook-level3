package seedu.moolah.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.logic.commands.alias.AddAliasCommand.MESSAGE_RECURSIVE_WARNING;
import static seedu.moolah.testutil.AliasTestUtil.ALIAS_A_TO_B;
import static seedu.moolah.testutil.AliasTestUtil.ALIAS_B_TO_C;
import static seedu.moolah.testutil.AliasTestUtil.ALIAS_C_TO_A;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.testutil.AliasTestUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class AddAliasCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_aliasNameIsReservedCommandWord_throwsCommandException() {
        AddAliasCommand command = new AddAliasCommand(AliasTestUtil.ALIAS_NAME_ADD);
        assertThrows(CommandException.class, () -> command.run(model));
    }

    @Test
    public void run_aliasIsRecursive_throwsRecursiveAliasException() {
        AddAliasCommand allowedAliasCommand1 = new AddAliasCommand(ALIAS_A_TO_B);
        AddAliasCommand allowedAliasCommand2 = new AddAliasCommand(ALIAS_B_TO_C);
        AddAliasCommand disallowedAliasCommand = new AddAliasCommand(ALIAS_C_TO_A);
        assertDoesNotThrow(() -> allowedAliasCommand1.run(model));
        assertDoesNotThrow(() -> allowedAliasCommand2.run(model));
        String message = new CommandException(MESSAGE_RECURSIVE_WARNING).getMessage();
        assertThrows(CommandException.class, () -> disallowedAliasCommand.run(model), message);
    }

    @Test
    public void run_aliasCommandIsValid_success() {
        AddAliasCommand command = new AddAliasCommand(ALIAS_A_TO_B);
        expectedModel.addUserAlias(ALIAS_A_TO_B);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setUserPrefs(model.getUserPrefs()));
        assertCommandSuccess(
                command, model,
                String.format(AddAliasCommand.MESSAGE_SUCCESS, ALIAS_A_TO_B.getAliasName()), expectedModel);
    }
}
