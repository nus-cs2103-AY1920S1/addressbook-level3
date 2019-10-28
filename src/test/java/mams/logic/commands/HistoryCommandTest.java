package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;

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
}
