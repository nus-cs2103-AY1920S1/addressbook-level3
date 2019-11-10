package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static unrealunity.visit.logic.commands.CommandTestUtil.assertCommandFailure;
import static unrealunity.visit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unrealunity.visit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;
import unrealunity.visit.model.UserPrefs;

public class AliasCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_createAliasUseAliasDeleteAlias_success() {
        String alias1 = "TEST ALIAS";
        String aliasTo1 = "help";
        String aliasUsage1 = "TEST ALIAS";
        String aliasUsageExpected1 = "help";
        String alias2 = "TEST ADD";
        String aliasTo2 = "add";
        String aliasUsage2 = "TEST ADD n/John Smith p/91234567 e/john@smith.co.uk "
                + "a/12 Downing St Westminster, London SW1A 2AD, UK";
        String aliasUsageExpected2 = "add n/John Smith p/91234567 e/john@smith.co.uk "
                + "a/12 Downing St Westminster, London SW1A 2AD, UK";

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.addAlias(alias1.toLowerCase(), aliasTo1.toLowerCase());
        assertCommandSuccess(new AliasCommand(alias1, aliasTo1), model,
                String.format(AliasCommand.MESSAGE_SUCCESS, alias1.toLowerCase(),
                aliasTo1.toLowerCase()), expectedModel);

        expectedModel.addAlias(alias2.toLowerCase(), aliasTo2.toLowerCase());
        assertCommandSuccess(new AliasCommand(alias2, aliasTo2), model,
                String.format(AliasCommand.MESSAGE_SUCCESS, alias2.toLowerCase(),
                aliasTo2.toLowerCase()), expectedModel);

        assertTrue(model.applyAlias(aliasUsage1).equals(aliasUsageExpected1));
        assertTrue(model.applyAlias(aliasUsage2).equals(aliasUsageExpected2));

        expectedModel.removeAlias(alias1.toLowerCase());
        assertCommandSuccess(new UnaliasCommand(alias1), model,
                String.format(UnaliasCommand.MESSAGE_SUCCESS, alias1.toLowerCase()), expectedModel);

        expectedModel.removeAlias(alias2.toLowerCase());
        assertCommandSuccess(new UnaliasCommand(alias2), model,
                String.format(UnaliasCommand.MESSAGE_SUCCESS, alias2.toLowerCase()), expectedModel);
    }

    @Test
    public void execute_overridingExistingCommand_throwsCommandException() {
        String alias = "help";
        String aliasTo = "exit";
        assertCommandFailure(new AliasCommand(alias, aliasTo), model, AliasCommand.MESSAGE_ALIAS_FAILED);
    }
}
