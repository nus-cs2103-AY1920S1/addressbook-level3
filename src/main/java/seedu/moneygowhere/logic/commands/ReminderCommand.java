package seedu.moneygowhere.logic.commands;

import seedu.moneygowhere.logic.commands.reminder.AddReminderCommand;
import seedu.moneygowhere.logic.commands.reminder.DeleteReminderCommand;

/**
 * Adds a Reminder to the Reminder list.
 */
public abstract class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + AddReminderCommand.MESSAGE_USAGE
            + "\n" + "or " + "\n"
            + DeleteReminderCommand.MESSAGE_USAGE;
}
