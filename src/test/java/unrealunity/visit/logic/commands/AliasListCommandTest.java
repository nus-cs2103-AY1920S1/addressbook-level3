package unrealunity.visit.logic.commands;

import static unrealunity.visit.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;

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
