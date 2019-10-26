package seedu.moneygowhere.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_MESSAGE;

import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.ReminderCommand;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.reminder.Reminder;

/**
 * Adds a Reminder to the Reminder list.
 */
public class AddReminderCommand extends ReminderCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder to MoneyGoWhere. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_MESSAGE + "MESSAGE ";

    public static final String MESSAGE_SUCCESS = "New Reminder added: %1$s";

    private final Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code reminder}
     */
    public AddReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addReminder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
