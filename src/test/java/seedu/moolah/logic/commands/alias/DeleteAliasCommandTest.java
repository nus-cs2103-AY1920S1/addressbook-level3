package seedu.moolah.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.testutil.AliasTestUtil;
import seedu.moolah.ui.alias.AliasListPanel;

class DeleteAliasCommandTest {

    @Test
    void constructor_nullName_throwsException() {
        assertThrows(NullPointerException.class, () -> new DeleteAliasCommand(null));
    }

    @Test
    void validate_deleteAliasThatExists_noExceptionThrown() {
        Alias toDelete = AliasTestUtil.ALIAS_A_TO_B;
        DeleteAliasCommand toValidate = new DeleteAliasCommand(toDelete.getAliasName());
        ModelSupportingAliasStub model = new ModelSupportingAliasStub();
        AliasMappings withAlias = model.getAliasMappings().addAlias(toDelete);
        model.setAliasMappings(withAlias);
        assertDoesNotThrow(() -> toValidate.validate(model));
    }

    @Test
    void validate_deleteAliasThatDoesNotExists_exceptionThrown() {
        Alias toDelete = AliasTestUtil.ALIAS_A_TO_B;
        DeleteAliasCommand toValidate = new DeleteAliasCommand(toDelete.getAliasName());
        ModelSupportingAliasStub model = new ModelSupportingAliasStub();
        assertThrows(CommandException.class, () -> toValidate.validate(model));
    }


    @Test
    void execute() {
        Alias toDelete = AliasTestUtil.ALIAS_A_TO_B;
        DeleteAliasCommand toExecute = new DeleteAliasCommand(toDelete.getAliasName());
        ModelSupportingAliasStub model = new ModelSupportingAliasStub();
        model.setAliasMappings(model.getAliasMappings().addAlias(toDelete));
        assertTrue(model.aliasWithNameExists(toDelete.getAliasName()));
        toExecute.execute(model);
        assertFalse(model.aliasWithNameExists(toDelete.getAliasName()));
    }

    @Test
    void testEquals() {
        DeleteAliasCommand a = new DeleteAliasCommand(AliasTestUtil.ALIAS_A_TO_B.getAliasName());
        DeleteAliasCommand a1 = new DeleteAliasCommand("a");

        DeleteAliasCommand b = new DeleteAliasCommand(AliasTestUtil.ALIAS_B_TO_C.getAliasName());
        DeleteAliasCommand b1 = new DeleteAliasCommand("b");

        DeleteAliasCommand c = new DeleteAliasCommand(AliasTestUtil.ALIAS_C_TO_A.getAliasName());
        DeleteAliasCommand c1 = new DeleteAliasCommand("c");

        assertEquals(a, a1);
        assertEquals(b, b1);
        assertEquals(c, c1);

        assertNotEquals(a, null);
        assertNotEquals(a, 1);
        assertNotEquals(a, "1");
        assertNotEquals(a, b);
        assertNotEquals(a, c);
    }

    @Test
    void getDescription_matches_equal() {
        String aliasName = AliasTestUtil.ALIAS_A_TO_B.getAliasName();
        String expected = String.format(DeleteAliasCommand.COMMAND_DESCRIPTION, aliasName);
        assertEquals(new DeleteAliasCommand(aliasName).getDescription(), expected);
    }

    @Test
    void getDescription_mismatch_notEqual() {
        String aliasName = AliasTestUtil.ALIAS_A_TO_B.getAliasName();
        String expected = String.format(DeleteAliasCommand.COMMAND_DESCRIPTION, aliasName);
        String notMatch = AliasTestUtil.ALIAS_B_TO_C.getAliasName();
        assertNotEquals(new DeleteAliasCommand(notMatch).getDescription(), expected);
    }

    @Test
    void execute_executed_commandResultPanelIsAliasPanel() {
        assertEquals(
                new DeleteAliasCommand("").execute(new ModelSupportingAliasStub()).viewRequest(),
                AliasListPanel.PANEL_NAME);
    }
}
