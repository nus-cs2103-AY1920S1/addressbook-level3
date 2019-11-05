package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.logic.history.HistoryFilterSettings;
import mams.model.Model;
import mams.model.ModelManager;

public class HistoryCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        // no parameters -> success
        assertCommandSuccess(new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE, true, false, false, false),
                expectedModel);

        // hide command feedback -> success with specific message
        assertCommandSuccess(new HistoryCommand(true, HistoryFilterSettings.SHOW_ALL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE + "\n"
                        + HistoryCommand.HIDING_COMMAND_OUTPUT_MESSAGE,
                        true, true, false, false),
                expectedModel);

        // hide all unsuccessful commands -> success with specific message
        assertCommandSuccess(new HistoryCommand(false, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE + "\n"
                        + HistoryCommand.SHOW_ONLY_SUCCESSFUL_MESSAGE,
                        true, false, false, false),
                expectedModel);

        // hide command feedback and all unsuccessful commands -> success with specific message
        assertCommandSuccess(new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE + "\n"
                        + HistoryCommand.HIDING_COMMAND_OUTPUT_MESSAGE + "\n"
                        + HistoryCommand.SHOW_ONLY_SUCCESSFUL_MESSAGE,
                        true, true, false, false),
                expectedModel);
    }

    @Test
    public void equals() {
        HistoryCommand showOutput = new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL);
        HistoryCommand hideOutput = new HistoryCommand(true, HistoryFilterSettings.SHOW_ALL);
        HistoryCommand anotherShowOutput = new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL);


        // same object -> returns true
        assertTrue(showOutput.equals(showOutput));

        // same values -> returns true
        assertTrue(showOutput.equals(anotherShowOutput));

        // different types -> returns false
        assertFalse(showOutput.equals(1));

        // null -> returns false
        assertFalse(showOutput.equals(null));

        // different internal values -> returns false
        assertFalse(showOutput.equals(hideOutput));
    }
}
