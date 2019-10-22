package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;

/**
 * Marks one or more loans as unpaid.
 */
public class LoanUnpaidCommand extends UpdateStatusCommand {

    public static final String COMMAND_WORD = "loan unpaid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks one or more loans as unpaid.\n"
            + "Parameters: "
            + MULTI_LOAN_SYNTAX
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + MULTI_LOAN_SYNTAX_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Loan(s) marked as unpaid.";

    public LoanUnpaidCommand(List<Index> loanIndices, List<Person> persons) throws CommandException {
        super(loanIndices, persons);
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());

        updateStatuses(model.getLoansManager(), Status.UNPAID);

        String result = constructMultiLoanResult(MESSAGE_SUCCESS);
        return new CommandResult(result, null);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LoanUnpaidCommand)) {
            return false;
        }

        return super.equals(other);
    }
}
