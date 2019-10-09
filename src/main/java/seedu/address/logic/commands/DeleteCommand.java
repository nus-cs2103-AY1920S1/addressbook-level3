package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Abstract parent class for DeleteBySerialNumberCommand and DeleteByIndexCommand.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer)\n or\n SERIAL_NUMBER (must be valid serial number)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " sn/B00001";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    public abstract CommandResult execute(Model model) throws CommandException;

}
