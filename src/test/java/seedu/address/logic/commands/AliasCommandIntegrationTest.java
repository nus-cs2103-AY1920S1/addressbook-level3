package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AliasCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_createAliasUseAliasDeleteAlias_success() {
        String alias = "TEST ALIAS";
        String aliasTo = "help";

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAlias(alias.toLowerCase(), aliasTo.toLowerCase());

        assertCommandSuccess(new AliasCommand(alias, aliasTo), model,
                String.format(AliasCommand.MESSAGE_SUCCESS, alias.toLowerCase(), aliasTo.toLowerCase()), expectedModel);

        assertTrue(model.applyAlias(alias).equals(aliasTo));

        expectedModel.removeAlias(alias.toLowerCase());
        assertCommandSuccess(new UnaliasCommand(alias), model,
                String.format(UnaliasCommand.MESSAGE_SUCCESS, alias.toLowerCase()), expectedModel);
    }

    @Test
    public void execute_overridingExistingCommand_throwsCommandException() {
        String alias = "help";
        String aliasTo = "exit";
        assertCommandFailure(new AliasCommand(alias, aliasTo), model, AliasCommand.MESSAGE_ALIAS_FAILED);
    }
}
