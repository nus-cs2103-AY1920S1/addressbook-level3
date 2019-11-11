package seedu.ichifund.logic.commands.loan;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_LOAN_SPAN;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;

/**
 * Adds a loan to IchiFund.
 */
public class AddLoanCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_DUPLICATE_LOAN = "This loan already exists in IchiFund";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a loan to IchiFund "
            + "and switches view to show new loan. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_START_DAY + "DAY loan is taken] "
            + "[" + PREFIX_START_MONTH + "MONTH loan is taken] "
            + "[" + PREFIX_START_YEAR + "YEAR loan is taken] "
            + "[" + PREFIX_END_DAY + "DAY loan is due] "
            + "[" + PREFIX_END_MONTH + "MONTH loan is due] "
            + "[" + PREFIX_END_YEAR + "YEAR loan is due] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "420.42 "
            + PREFIX_NAME + "Felix Kjellberg "
            + PREFIX_START_DAY + "12 "
            + PREFIX_START_MONTH + "12 "
            + PREFIX_START_YEAR + "2019 "
            + PREFIX_END_DAY + "23 "
            + PREFIX_END_MONTH + "2 "
            + PREFIX_END_YEAR + "2020 "
            + PREFIX_DESCRIPTION + "Borrowed for apple earpods ";

    public static final String MESSAGE_SUCCESS = "New loan added: %1$s";


    private final Loan toAdd;

    /**
     * Creates an AddRepeaterCommand to add the specified {@code Repeater}
     */
    public AddLoanCommand(Loan loan) {
        requireNonNull(loan);
        toAdd = loan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toAdd.getEndDate().compareTo(toAdd.getStartDate()) > 0) {
            throw new CommandException(MESSAGE_INVALID_LOAN_SPAN);
        }
        if (countMonths(toAdd.getStartDate(), toAdd.getEndDate()) > 60) {
            throw new CommandException(MESSAGE_INVALID_LOAN_SPAN);
        }

        // Get current repeater unique id.
        LoanId loanId = model.getCurrentLoanId();

        if (model.hasLoan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }

        model.addLoan(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLoanCommand // instanceof handles nulls
                && toAdd.equals(((AddLoanCommand) other).toAdd));
    }

    /**
     * Counts number of months between 2 dates.
     * @param startDate
     * @param endDate
     * @return
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
}
