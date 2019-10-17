package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Consumer;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.loan.util.PersonLoanIndexPair;

/**
 * Delete one or more loans.
 */
public class DeleteLoanCommand extends MultiLoanCommand {

    public static final String COMMAND_WORD = "loan delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes one or more loans.\n"
            + "Parameters: "
            + MULTI_LOAN_SYNTAX
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + MULTI_LOAN_SYNTAX_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Loan(s) deleted.";
    public static final String MESSAGE_FAILURE = "One or more targeted loans could not be found.";

    public DeleteLoanCommand(
            List<PersonLoanIndexPair> personLoanIndexPairs, List<Index> personIndices) throws CommandException {
        super(personLoanIndexPairs, personIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        LoansManager loansManager = model.getLoansManager();
        LoanList targetLoans = constructTargetLoanList(loansManager);
        Consumer<Loan> operation = loansManager::deleteLoan;

        try {
            actOnTargetLoans(targetLoans, operation);
        } catch (CommandException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String resultMessage = constructMultiLoanResult(MESSAGE_SUCCESS, MESSAGE_FAILURE);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DeleteLoanCommand)) {
            return false;
        }

        return super.equals(other);
    }
}
