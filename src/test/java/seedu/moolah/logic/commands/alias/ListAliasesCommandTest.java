package seedu.moolah.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.logic.commands.alias.ListAliasesCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.ui.alias.AliasListPanel;

class ListAliasesCommandTest {

    @Test
    void validate_nullModel_throwsException() {
        assertThrows(NullPointerException.class, () -> new ListAliasesCommand().validate(null));
        assertDoesNotThrow(() -> new ListAliasesCommand().validate(new ModelSupportingAliasStub()));
    }

    @Test
    void execute_nullModel_throwsException() {
        assertThrows(NullPointerException.class, () -> new ListAliasesCommand().execute(null));
        assertDoesNotThrow(() -> new ListAliasesCommand().execute(new ModelSupportingAliasStub()));
    }

    @Test
    void execute_hasNoAliases_messageSaysZeroAliases() {
        ModelSupportingAliasStub model = new ModelSupportingAliasStub();
        CommandResult res = new ListAliasesCommand().execute(model);
        assertEquals(res.getFeedbackToUser(), String.format(
                MESSAGE_SUCCESS, 0));
    }


    @Test
    void execute_hasNAliases_messageSaysNAliases() {
        ModelSupportingAliasStub model = new ModelSupportingAliasStub();
        for (int i = 1; i < 2; i++) {
            model.setAliasMappings(model.getAliasMappings().addAlias(new Alias("s".repeat(i), "a".repeat(i))));
        }
        CommandResult res1 = new ListAliasesCommand().execute(model);
        assertEquals(res1.getFeedbackToUser(), String.format(
                MESSAGE_SUCCESS, 1));
        for (int i = 2; i < 11; i++) {
            model.setAliasMappings(model.getAliasMappings().addAlias(new Alias("s".repeat(i), "a".repeat(i))));
        }
        CommandResult res2 = new ListAliasesCommand().execute(model);
        assertEquals(res2.getFeedbackToUser(), String.format(
                MESSAGE_SUCCESS, 10));
        for (int i = 11; i < 16; i++) {
            model.setAliasMappings(model.getAliasMappings().addAlias(new Alias("s".repeat(i), "a".repeat(i))));
        }
        CommandResult res3 = new ListAliasesCommand().execute(model);
        assertEquals(res3.getFeedbackToUser(), String.format(
                MESSAGE_SUCCESS, 15));
        for (int i = 16; i < 21; i++) {
            model.setAliasMappings(model.getAliasMappings().addAlias(new Alias("s".repeat(i), "a".repeat(i))));
        }
        CommandResult res4 = new ListAliasesCommand().execute(model);
        assertEquals(res4.getFeedbackToUser(), String.format(
                MESSAGE_SUCCESS, 20));
    }

    @Test
    void execute_executed_commandResultPanelIsAliasPanel() {
        assertEquals(
                new ListAliasesCommand().execute(new ModelSupportingAliasStub()).viewRequest(),
                AliasListPanel.PANEL_NAME);
    }
}
