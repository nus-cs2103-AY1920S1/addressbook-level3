package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_EXIT_FROM_SERVE_MODE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits the application. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting LiBerry as requested ...";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.isServeMode()) {
            throw new CommandException(MESSAGE_CANNOT_EXIT_FROM_SERVE_MODE);
        }
        return CommandResult.commandResultExit(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
