package dukecooks.logic.commands;

/**
 * Marks a task identified using it's displayed index from DukeCooks.
 */
public abstract class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " done"
            + ": Marks the task identified by the index number used in the displayed task list as complete. \n"
            + "Parameters: INDEX (must be a positive integer)\n";
}
