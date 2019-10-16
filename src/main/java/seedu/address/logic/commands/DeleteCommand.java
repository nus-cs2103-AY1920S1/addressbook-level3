package seedu.address.logic.commands;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes either a day, person or activity identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n";
}
