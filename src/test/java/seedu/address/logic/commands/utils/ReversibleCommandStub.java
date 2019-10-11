package seedu.address.logic.commands.utils;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.model.Model;

/**
 * Stubs an {@code UndoableCommand} for unit testing.
 */
public class ReversibleCommandStub extends ReversibleCommand {
    private final String result;

    public ReversibleCommandStub(String result) {
        this.result = result;
    }

    @Override
    public CommandResult undo(Model model) {
        return new CommandResult(result);
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(result);
    }
}
