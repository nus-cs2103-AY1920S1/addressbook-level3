package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AliasListCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @Test
    public void execute_aliasList_success() {
        String expectedStringForDefaultAliasTable = "a ⟶ add\n"
                + "e ⟶ exit\n"
                + "h ⟶ help\n";
        CommandResult expectedCommandResult = new CommandResult(expectedStringForDefaultAliasTable,
                false, false, false, false, false, false, false, true);
        System.out.println((new AliasListCommand().execute(model).getFeedbackToUser()));
        assertCommandSuccess(new AliasListCommand(), model, expectedCommandResult, expectedModel);
    }
}
