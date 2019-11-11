package seedu.tarence.logic.commands;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.storage.Storage;

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
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DisplayCommand)) {
            return false;
        }

        return (o == this) || ((DisplayCommand) o).message.equals(this.message);
    }
}
