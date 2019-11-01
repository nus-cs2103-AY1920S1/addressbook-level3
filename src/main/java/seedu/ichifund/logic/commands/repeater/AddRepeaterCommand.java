package seedu.ichifund.logic.commands.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_END_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_START_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Adds a repeater to IchiFund.
 */
public class AddRepeaterCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a repeater to IchiFund. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE "
            + PREFIX_MONTH_START_OFFSET + "MONTH_START_OFFSET "
            + PREFIX_MONTH_END_OFFSET + "MONTH_END_OFFSET "
            + PREFIX_START_MONTH + "START_MONTH "
            + PREFIX_START_YEAR + "START_YEAR "
            + PREFIX_END_MONTH + "END_MONTH "
            + PREFIX_END_YEAR + "END_YEAR "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Phone bills "
            + PREFIX_AMOUNT + "42.15 "
            + PREFIX_CATEGORY + "Utilities "
            + PREFIX_TRANSACTION_TYPE + "exp "
            + PREFIX_MONTH_START_OFFSET + "3 "
            + PREFIX_MONTH_END_OFFSET + "2 "
            + PREFIX_START_MONTH + "1 "
            + PREFIX_START_YEAR + "2019 "
            + PREFIX_END_MONTH + "12 "
            + PREFIX_END_YEAR + "2025";

    public static final String MESSAGE_ADD_REPEATER_SUCCESS = "New repeater added: %1$s";

    private final Repeater toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Repeater}
     */
    public AddRepeaterCommand(Repeater repeater) {
        requireNonNull(repeater);
        toAdd = repeater;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get current repeater unique id.
        RepeaterUniqueId repeaterUniqueId = model.getCurrentRepeaterUniqueId();

        // Create new repeater.
        Repeater newRepeater = new Repeater(
                    repeaterUniqueId,
                    toAdd.getDescription(), toAdd.getAmount(), toAdd.getCategory(),
                    toAdd.getTransactionType(), toAdd.getMonthStartOffset(), toAdd.getMonthEndOffset(),
                    toAdd.getStartDate(), toAdd.getEndDate());

        // Update current repeater unique id.
        model.setCurrentRepeaterUniqueId(new RepeaterUniqueId(String.valueOf(
                    repeaterUniqueId.id + 1)));

        // Add repeater.
        model.addRepeater(newRepeater);

        // Create repeater transactions.
        createRepeaterTransactions(model, newRepeater);

        return new CommandResult(String.format(MESSAGE_ADD_REPEATER_SUCCESS, toAdd));
    }

    /**
     * Creates the transactions associated with the specified {@code Repeater}.
     */
    private void createRepeaterTransactions(Model model, Repeater repeater) {
        int currentMonth = repeater.getStartDate().getMonth().monthNumber;
        int currentYear = repeater.getStartDate().getYear().yearNumber;
        int endMonth = repeater.getEndDate().getMonth().monthNumber;
        int endYear = repeater.getEndDate().getYear().yearNumber;

        while ((currentYear < endYear) || (currentYear == endYear && currentMonth <= endMonth)) {
            if (!repeater.getMonthStartOffset().isIgnored()) {
                Transaction transaction = new Transaction(
                        repeater.getDescription(),
                        repeater.getAmount(),
                        repeater.getCategory(),
                        new Date(
                            new Day(repeater.getMonthStartOffset().toString()),
                            new Month(String.valueOf(currentMonth)),
                            new Year(String.valueOf(currentYear))),
                        repeater.getTransactionType(),
                        repeater.getUniqueId());
                model.addTransaction(transaction);
            }

            if (!repeater.getMonthEndOffset().isIgnored()) {
                int daysInMonth;
                if ((new Month(String.valueOf(currentMonth))).has30Days()) {
                    daysInMonth = 30;
                } else if ((new Month(String.valueOf(currentMonth))).has31Days()) {
                    daysInMonth = 31;
                } else if ((new Year(String.valueOf(currentYear))).isLeapYear()) {
                    daysInMonth = 29;
                } else {
                    daysInMonth = 28;
                }

                Transaction transaction = new Transaction(
                        repeater.getDescription(),
                        repeater.getAmount(),
                        repeater.getCategory(),
                        new Date(
                            new Day(String.valueOf(daysInMonth - (repeater.getMonthEndOffset().value - 1))),
                            new Month(String.valueOf(currentMonth)),
                            new Year(String.valueOf(currentYear))),
                        repeater.getTransactionType(),
                        repeater.getUniqueId());
                model.addTransaction(transaction);
            }

            currentMonth++;
            if (currentMonth == 12) {
                currentMonth = 1;
                currentYear++;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRepeaterCommand // instanceof handles nulls
                && toAdd.equals(((AddRepeaterCommand) other).toAdd));
    }
}
