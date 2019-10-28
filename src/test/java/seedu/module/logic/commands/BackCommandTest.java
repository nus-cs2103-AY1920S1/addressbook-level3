package seedu.module.logic.commands;

import static seedu.module.logic.commands.BackCommand.MESSAGE_BACK_COMMAND_NOT_EFFECTIVE;
import static seedu.module.logic.commands.BackCommand.MESSAGE_BACK_COMMAND_SUCCESS;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.testutil.ArchivedModuleBuilder;

public class BackCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_back_success() {
        model.setDisplayedModule(new ArchivedModuleBuilder().build());
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_BACK_COMMAND_SUCCESS, false, true, false);
        System.out.printf("%s %s %s %s\n", new BackCommand(), model, expectedCommandResult, expectedModel);
        assertCommandSuccess(new BackCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_back_notEffective() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_BACK_COMMAND_NOT_EFFECTIVE, false, true, false);
        assertCommandSuccess(new BackCommand(), model, expectedCommandResult, expectedModel);
    }
}
