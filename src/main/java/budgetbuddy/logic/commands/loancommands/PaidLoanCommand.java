package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.util.PersonLoanIndexPair;

/**
 * Marks one or more loans as paid.
 */
public class PaidLoanCommand extends UpdateStatusCommand {

    public static final String COMMAND_WORD = "loan paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks one or more loans as paid.\n"
            + "Parameters: "
            + "<person number>[.(<loan numbers...>)] "
            + "...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1.(1 2 5) "
            + "3";

    public static final String MESSAGE_SUCCESS = "Loan(s) marked as paid.";
    public static final String MESSAGE_FAILURE = "One or more targeted loans could not be found.";

    public PaidLoanCommand(
            List<PersonLoanIndexPair> personLoanIndexPairs, List<Index> personIndices) throws CommandException {
        super(personLoanIndexPairs, personIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        try {
            updateStatuses(model.getLoansManager(), Status.PAID);
        } catch (CommandException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String result = constructMultiLoanResult(MESSAGE_SUCCESS, MESSAGE_FAILURE);
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PaidLoanCommand)) {
            return false;
        }

        return super.equals(other);
    }
}
