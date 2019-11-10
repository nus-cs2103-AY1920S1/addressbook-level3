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

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;

/**
 * Adds a repeater to IchiFund.
 */
public class AddRepeaterCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a repeater to IchiFund.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE] "
            + "[" + PREFIX_MONTH_START_OFFSET + "MONTH_START_OFFSET] "
            + "[" + PREFIX_MONTH_END_OFFSET + "MONTH_END_OFFSET] "
            + PREFIX_START_MONTH + "START_MONTH "
            + PREFIX_START_YEAR + "START_YEAR "
            + PREFIX_END_MONTH + "END_MONTH "
            + PREFIX_END_YEAR + "END_YEAR "
            + "\nConstraints:\n"
            + "- Repeater end must not occur before repeater start.\n"
            + "- Repeater start and end can span at most 60 months (5 years).\n"
            + "- At least one of month start offset or month end offset must not be ignored.\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Phone bills "
            + PREFIX_AMOUNT + "42.15 "
            + PREFIX_CATEGORY + "Utilities "
            + PREFIX_TRANSACTION_TYPE + "exp "
            + PREFIX_MONTH_START_OFFSET + "3 "
            + PREFIX_MONTH_END_OFFSET + "2 "
            + PREFIX_START_MONTH + "1 "
            + PREFIX_START_YEAR + "2019 "
            + PREFIX_END_MONTH + "12 "
            + PREFIX_END_YEAR + "2020";

    public static final String MESSAGE_ADD_REPEATER_SUCCESS = "New repeater added: %1$s";
    public static final String MESSAGE_DUPLICATE_REPEATER = "This repeater already exists in IchiFund";

    private final Repeater toAdd;

    /**
     * Creates an AddRepeaterCommand to add the specified {@code Repeater}
     */
    public AddRepeaterCommand(Repeater repeater) {
        requireNonNull(repeater);
        toAdd = repeater;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRepeater(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REPEATER);
        }

        // Check repeater span.
        if (toAdd.getEndDate().compareTo(toAdd.getStartDate()) > 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_SPAN);
        }
        if (countMonths(toAdd.getStartDate(), toAdd.getEndDate()) > 60) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_SPAN);
        }

        // Check offsets.
        if (toAdd.getMonthStartOffset().isIgnored() && toAdd.getMonthEndOffset().isIgnored()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_MONTH_OFFSETS);
        }

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
        model.createRepeaterTransactions(newRepeater);

        return new CommandResult(String.format(MESSAGE_ADD_REPEATER_SUCCESS, toAdd));
    }

    /**
     * Counts the number of months spanned by two dates.
     */
    private static int countMonths(Date startDate, Date endDate) {
        if (endDate.compareTo(startDate) > 0) {
            return 0;
        }

        if (endDate.compareTo(startDate) == 0) {
            return 1;
        }

        int startMonth = startDate.getMonth().monthNumber;
        int startYear = startDate.getYear().yearNumber;
        int currentMonth = startDate.getMonth().monthNumber;
        int currentYear = startDate.getYear().yearNumber;
        int endMonth = endDate.getMonth().monthNumber;
        int endYear = endDate.getYear().yearNumber;

        int months = 0;
        while ((currentYear < endYear) || (currentYear == endYear && currentMonth <= endMonth)) {
            months++;
            currentMonth++;
            if (currentMonth == 13) {
                currentMonth = 1;
                currentYear++;
            }
        }

        return months;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRepeaterCommand // instanceof handles nulls
                && toAdd.equals(((AddRepeaterCommand) other).toAdd));
    }
}
