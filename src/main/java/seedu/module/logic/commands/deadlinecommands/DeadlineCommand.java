package seedu.module.logic.commands.deadlinecommands;

import seedu.module.logic.commands.Command;

/**
 * Adds, Edits or Deletes a Deadline in a Module depending on input.
 */
public abstract class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds, deletes, edits, deletes, marks as done or"
            + " in progress to a Module.\n"
            + "Click or type \"help\" for more assistance. \n"
            + "Parameters: " + "Action \n"
            + "Example: " + COMMAND_WORD + " 1 a/add "
            + "d/tutorial " + "t/3/3/2019 1900 p/HIGH";

    public static final String MESSAGE_DUPLICATE_DEADLINE = "Deadline task already exists, input another one.";
    public static final String MESSAGE_TASK_LIST_NUMBER_NOT_FOUND = "Invalid deadline task list number.";

}

