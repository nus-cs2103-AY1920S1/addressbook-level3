package seedu.ichifund.logic.commands.loan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.loan.Loan;

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

    public static final String MESSAGE_PAY_LOAN_SUCCESS = "Paid off Loan: %1$s";

    private final Index index;

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

        Loan loanToPay = lastShownList.get(index.getZeroBased());
        model.payLoan(loanToPay);
        return new CommandResult(String.format(MESSAGE_PAY_LOAN_SUCCESS, loanToPay));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PayLoanCommand // instanceof handles nulls
                && index.equals(((PayLoanCommand) other).index)); // state check
    }
}
