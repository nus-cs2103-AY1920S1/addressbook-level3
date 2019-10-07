package seedu.address.logic.commands;

import seedu.address.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from undo");
    }
}
