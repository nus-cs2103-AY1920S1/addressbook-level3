package seedu.elisa.logic.commands;

import static seedu.elisa.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.elisa.commons.core.item.Item;

/**
 * Adds a Task to the item model.
 */
public class AddTaskCommand extends AddCommand {

    public static final String SHOW_TASK_VIEW = "T";
    public static final String COMMAND_WORD = "task";
    public static final String MESSAGE_SUCCESS = "New Task added: %1$s\nDon't just watch it pile up!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the Task List. \n"
            + "Parameters: \n"
            + "<Compulsory> " + "description \n"
            + "<Optional> " + PREFIX_DATETIME + " Deadline \n"
            + "<Optional> " + PREFIX_REMINDER + " Reminder \n"
            + "<Optional> " + PREFIX_PRIORITY + " Priority \n"
            + "<Optional> " + PREFIX_TAG + " Tag \n";

    public AddTaskCommand(Item item) {
        super(item);
    }

    @Override
    public String getListView() {
        return SHOW_TASK_VIEW;
    }

    @Override
    public String getMessageSuccess() {
        return MESSAGE_SUCCESS;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
