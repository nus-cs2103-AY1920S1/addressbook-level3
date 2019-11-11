package seedu.elisa.logic.commands;

import static seedu.elisa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.elisa.commons.core.item.Item;

/**
 * Adds a Reminder to the item model.
 */
public class AddReminderCommand extends AddCommand {

    public static final String SHOW_REMINDER_VIEW = "R";
    public static final String COMMAND_WORD = "reminder";
    public static final String MESSAGE_SUCCESS = "Fine, I'll remind you. New Reminder added: %1$s"
            + "\nIt's like you need a keeper";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder to the Reminder list. \n"
            + "Parameters: \n"
            + "<Compulsory> " + "description \n"
            + "<Compulsory> " + PREFIX_REMINDER + " Reminder time \n"
            + "<Optional> " + PREFIX_PRIORITY + " Priority \n"
            + "<Optional> " + PREFIX_TAG + " Tag \n";


    public AddReminderCommand(Item item) {
        super(item);
    }

    @Override
    public String getListView() {
        return SHOW_REMINDER_VIEW;
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
