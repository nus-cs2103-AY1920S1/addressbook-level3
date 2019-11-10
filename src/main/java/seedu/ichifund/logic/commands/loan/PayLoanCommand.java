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
public class PayLoanCommand extends Command {

    public static final String COMMAND_WORD = "pay";
    public static final String MESSAGE_LOAN_NOT_FOUND = "This loan does not exist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the loan with given index\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Paid off Loan: %1$s";

    private final Index target;
    
    public PayLoanCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Loan> lastShownList = model.getFilteredLoanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_INDEX);
        }

        Loan loanToDelete = lastShownList.get(index.getZeroBased());
        model.payLoan(loanToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LOAN_SUCCESS, loanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLoanCommand // instanceof handles nulls
                && index.equals(((DeleteLoanCommand) other).index)); // state check
    }
}