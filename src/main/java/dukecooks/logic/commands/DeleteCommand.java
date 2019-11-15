package dukecooks.logic.commands;

/**
 * Deletes the identified using it's displayed index from DukeCooks.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entry identified by the index number used in the displayed list.\n"
            + "Parameters: VARIANT, INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "<variant> 1";
}
