package seedu.moolah.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.testutil.AliasTestUtil;

class DeleteAliasCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    void validate_deleteAliasThatExists_noExceptionThrown() {
        assertDoesNotThrow(() -> {
            Alias toDelete = AliasTestUtil.ALIAS_B_TO_C;
            model.addUserAlias(toDelete);
            DeleteAliasCommand toRun = new DeleteAliasCommand(toDelete.getAliasName());
            toRun.run(model);
        });
    }

    @Test
    void run_deleteAliasThatDoesNotExists_exceptionThrown() {
        assertThrows(CommandException.class, () -> {
            Alias toDelete = AliasTestUtil.ALIAS_A_TO_B;
            DeleteAliasCommand toValidate = new DeleteAliasCommand(toDelete.getAliasName());
            toValidate.validate(model);
        });

        assertThrows(CommandException.class, () -> {
            Alias toDelete = AliasTestUtil.ALIAS_B_TO_C;
            DeleteAliasCommand toValidate = new DeleteAliasCommand(toDelete.getAliasName());
            toValidate.validate(model);
        });
    }

    @Test
    void run_deleteAliasThatDoesExists_aliasDeleted() {
        Alias alias = AliasTestUtil.ALIAS_A_TO_B;
        assertEquals(expectedModel, model);
        model.addUserAlias(alias);
        DeleteAliasCommand deleteAliasCommand = new DeleteAliasCommand(alias.getAliasName());
        try {
            deleteAliasCommand.run(model);
            assertEquals(model.getAliasMappings(), expectedModel.getAliasMappings());
        } catch (Exception e) {
            throw new AssertionError("Should not throw exception.");
        }

    }
}
