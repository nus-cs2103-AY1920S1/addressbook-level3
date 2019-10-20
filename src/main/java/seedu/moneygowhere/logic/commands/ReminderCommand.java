package seedu.moneygowhere.logic.commands;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.*;

public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder to MoneyGoWhere. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_MESSAGE + "MESSAGE ";

    public static final String MESSAGE_SUCCESS = "New Reminder added: %1$s";

    private final Reminder toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ReminderCommand(Reminder reminder) {
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
                || (other instanceof ReminderCommand // instanceof handles nulls
                && toAdd.equals(((ReminderCommand) other).toAdd));
    }
}
