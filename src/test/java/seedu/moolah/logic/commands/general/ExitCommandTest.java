package seedu.moolah.logic.commands.general;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.logic.commands.general.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.ui.panel.PanelName;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void run_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT,
                false, true, PanelName.CURRENT);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
