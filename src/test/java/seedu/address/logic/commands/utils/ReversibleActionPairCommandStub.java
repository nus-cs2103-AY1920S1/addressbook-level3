package seedu.address.logic.commands.utils;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.model.Model;

/**
 * Stubs an {@code ReversibleActionPairCommand} for unit testing.
 */
public class ReversibleActionPairCommandStub extends ReversibleActionPairCommand {
    private final String result;

    public ReversibleActionPairCommandStub(String result) {
        super(new ReversibleCommandStub(""), new ReversibleCommandStub(""));
        this.result = result;
    }

    @Override
    public CommandResult undo(Model model) {
        return new CommandResult(result);
    }

    @Override
    public CommandResult redo(Model model) {
        return new CommandResult(result);
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(result);
    }
}
