package seedu.weme.logic.commands.generalcommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;

public class HelpCommandTest extends ApplicationTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
