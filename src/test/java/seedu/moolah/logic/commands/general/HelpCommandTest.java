package seedu.moolah.logic.commands.general;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.logic.commands.general.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.ui.panel.PanelName;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void run_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE,
                true, false, PanelName.CURRENT);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
