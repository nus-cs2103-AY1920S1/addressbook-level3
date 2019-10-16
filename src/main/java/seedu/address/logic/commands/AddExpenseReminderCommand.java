package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ExpenseReminder;



/**
 * Adds a person to the address book.
 */
public class AddExpenseReminderCommand extends Command {

    public static final String COMMAND_WORD = "addExpenseReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an Expense Reminder to reminders list. "
            + "Parameters: "
            + PREFIX_DESC + "REMINDER_MESSAGE"
            + PREFIX_AMOUNT + "QUOTA "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "fat "
            + PREFIX_AMOUNT + "5.50 "
            + PREFIX_TAG + "food "
            + PREFIX_TAG + "mala";

    public static final String MESSAGE_SUCCESS = "New ExpenseReminder added: %1$s";

    private final ExpenseReminder toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddExpenseReminderCommand(ExpenseReminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addExpenseReminder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpenseReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddExpenseReminderCommand) other).toAdd));
    }
}