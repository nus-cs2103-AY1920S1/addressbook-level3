package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Checkout to a project to work on it.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": go back to the previous display "
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_CANT_GO_BACK = "Oops can't go back any further!";

    public BackCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getWorkingProject().isEmpty()) {
            throw new CommandException(MESSAGE_CANT_GO_BACK);
        }
        return new CommandResult("", COMMAND_WORD);
    }
}
