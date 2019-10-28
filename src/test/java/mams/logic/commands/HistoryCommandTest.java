package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;

public class HistoryCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        assertCommandSuccess(new HistoryCommand(), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE, true, false, false, false),
                expectedModel);

        assertCommandSuccess(new HistoryCommand(true), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE, true, true, false, false),
                expectedModel);
    }

    @Test
    public void equals() {
        HistoryCommand showOutput = new HistoryCommand();
        HistoryCommand hideOutput = new HistoryCommand(true);
        HistoryCommand anotherShowOutput = new HistoryCommand();


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
