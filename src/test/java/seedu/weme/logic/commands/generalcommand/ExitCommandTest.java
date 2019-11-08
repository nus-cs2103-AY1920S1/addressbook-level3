package seedu.weme.logic.commands.generalcommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;

public class ExitCommandTest extends ApplicationTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
