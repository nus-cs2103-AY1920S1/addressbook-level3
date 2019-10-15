package seedu.tarence.logic.commands;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;

/**
 * Represents a command to display a message.
 */
public class DisplayCommand extends Command {

    private final String message;

    DisplayCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(message);
    }

    @Override
    public boolean needsInput() {
        return false;
    }

    @Override
    public boolean needsCommand(Command command) {
        return false;
    }
}
