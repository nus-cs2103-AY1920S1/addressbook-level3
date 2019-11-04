package seedu.address.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.testutil.AliasTestUtil;

class DeleteAliasCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void freshModel() {
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
