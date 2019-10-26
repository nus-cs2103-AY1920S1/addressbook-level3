package seedu.moneygowhere.logic.commands;

import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_MESSAGE;

import seedu.moneygowhere.logic.commands.reminder.AddReminderCommand;

/**
 * Adds a Reminder to the Reminder list.
 */
public abstract class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + AddReminderCommand.COMMAND_WORD
            + ": Adds a Reminder to MoneyGoWhere. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_MESSAGE + "MESSAGE "
            + "\n" + " or " + "\n"
            + COMMAND_WORD + " delete" + ": Remove a Reminder from MoneyGoWhere. "
            + "Parameters: "
            + "INDEX";
}
