package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Consumer;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.person.Person;

/**
 * Delete one or more loans.
 */
public class LoanDeleteCommand extends MultiLoanCommand {

    public static final String COMMAND_WORD = "loan delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes one or more loans.\n"
            + "Parameters: "
            + MULTI_LOAN_SYNTAX
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + MULTI_LOAN_SYNTAX_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Loan(s) %1$s deleted.";

    public LoanDeleteCommand(List<Index> loanIndices, List<Person> persons) throws CommandException {
        super(loanIndices, persons);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        LoansManager loansManager = model.getLoansManager();
        List<Index> targetLoanIndices = constructTargetLoanIndicesList(loansManager);
        Consumer<Index> deleteLoanOp = loansManager::deleteLoan;

        actOnTargetLoans(loansManager, targetLoanIndices, deleteLoanOp);

        String resultMessage = constructMultiLoanResult(MESSAGE_SUCCESS);
        return new CommandResult(resultMessage, CommandCategory.LOAN);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LoanDeleteCommand)) {
            return false;
        }

        return super.equals(other);
    }
}
