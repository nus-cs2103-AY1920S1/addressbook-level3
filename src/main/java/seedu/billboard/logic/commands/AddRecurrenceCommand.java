package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;
import java.util.stream.IntStream;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Expense;

/**
 * Adds an expense to the address book.
 */
public class AddRecurrenceCommand extends RecurrenceCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds recurring expenses to the billboard. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE "
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bought "
            + PREFIX_DESCRIPTION + "Buy a book "
            + PREFIX_AMOUNT + "9.00 "
            + PREFIX_START_DATE + "25/3/2019 1200 "
            + PREFIX_END_DATE + "25/4/2019 1200 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_INTERVAL + "weekly";

    public static final String MESSAGE_SUCCESS = "New recurring expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This recurrence already exists in the billboard";

    private final Expense toAdd;
    private final Period period;
    private final int repeat;


    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddRecurrenceCommand(Expense expense, Period period, int repeat) {
        requireNonNull(expense);
        toAdd = expense;
        this.period = period;
        this.repeat = repeat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        IntStream.range(0, repeat).forEachOrdered(n -> {
            if (model.hasExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

            model.addExpense(toAdd);
        });
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecurrenceCommand // instanceof handles nulls
                && toAdd.equals(((AddRecurrenceCommand) other).toAdd)
                && period.equals(((AddRecurrenceCommand) other).period));
    }

    @Override
    public int hashCode() {
        return Objects.hash(toAdd, period);
    }
}
