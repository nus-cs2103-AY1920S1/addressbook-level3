package seedu.ichifund.logic.commands;

import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;

public class EmptyCommand extends Command {
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("");
    }
}
