package seedu.module.logic.commands.deadlinecommands;

import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TIME;

/**
 * Represents an Edit deadline command.
 */
public abstract class EditDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits description or time deadline task identified by the index and task number.\n"
            + "Parameters: MODULE_INDEX (must be a positive integer), "
            + "TASK(must be a positive integer), \n"
            + "Example: deadline 2 " + PREFIX_ACTION + COMMAND_WORD + " " + PREFIX_TASK_LIST_NUMBER + "1 "
            + PREFIX_DESCRIPTION + "New Description \n "
            + "OR\n"
            + "Example: deadline 2 " + PREFIX_ACTION + COMMAND_WORD + " " + PREFIX_TASK_LIST_NUMBER + "1 "
            + PREFIX_TIME + " New Time ";

    public static final String MESSAGE_EDIT_DEADLINE_SUCCESS = "Edited deadline task to Module: %1$s";
    public static final String MESSAGE_EDIT_DEADLINE_FAIL = "Unable to edit deadline task to Module: %1$s";
}
