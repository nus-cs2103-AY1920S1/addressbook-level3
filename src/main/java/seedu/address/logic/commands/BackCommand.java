package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Changes the UI to the previous display.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": go back to the previous display "
            + "Example: " + COMMAND_WORD;

    public BackCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("", COMMAND_WORD);
    }
}
