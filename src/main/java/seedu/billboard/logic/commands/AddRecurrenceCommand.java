package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.recurrence.Recurrence;
import seedu.billboard.model.tag.Tag;

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
            + PREFIX_INTERVAL + "week";

    public static final String MESSAGE_SUCCESS = "New recurring expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This recurrence already exists in the billboard";

    private Name name;
    private Description description;
    private Amount amount;
    private CreatedDateTime date;
    private List<String> tagList;
    private DateInterval interval;
    private int iterations;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddRecurrenceCommand(Name name, Description description, Amount amount, CreatedDateTime date,
                                List<String> tagList, DateInterval interval, int iterations) {
        requireAllNonNull(name, description, amount, date, interval, iterations);

        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.tagList = tagList;
        this.interval = interval;
        this.iterations = iterations;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Expense expense;
        ExpenseList expenses = new ExpenseList();
        Set<Tag> tags = model.retrieveTags(tagList);
        model.incrementCount(tags);

        for (int i = 0; i < iterations; i++) {
            expense = new Expense(
                    name.getClone(),
                    description.getClone(),
                    amount.getClone(),
                    date.plus(interval, i), tags);
            expenses.add(expense);
        }

        Recurrence recurrence = new Recurrence(expenses, interval);

        if (model.hasRecurrence(recurrence)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }
        requireNonNull(model);
        for (Expense e : expenses) {
            if (model.hasExpense(e)) {
                continue;
            }
            model.addExpense(e);
        }
        model.addRecurrence(recurrence);

        return new CommandResult(String.format(MESSAGE_SUCCESS, recurrence));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecurrenceCommand // instanceof handles nulls
                && name.equals(((AddRecurrenceCommand) other).name)
                && description.equals(((AddRecurrenceCommand) other).description)
                && amount.equals(((AddRecurrenceCommand) other).amount)
                && date.equals(((AddRecurrenceCommand) other).date)
                && tagList.equals(((AddRecurrenceCommand) other).tagList)
                && interval.equals(((AddRecurrenceCommand) other).interval)
                && iterations == ((AddRecurrenceCommand) other).iterations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, amount, date, tagList, interval, iterations);
    }
}
