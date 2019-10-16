package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.person.exceptions.PersonNotFoundException;
import budgetbuddy.model.person.loan.Loan;
import budgetbuddy.model.person.loan.exceptions.LoanNotFoundException;

/**
 * Delete one or more loans.
 */
public class DeleteLoanCommand extends MultiLoanCommand {

    public static final String COMMAND_WORD = "loan delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes one or more loans.\n"
            + "Parameters: "
            + "<person number>[.(<loan numbers...>)] "
            + "...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1.(1 3 4) "
            + "2";

    public static final String MESSAGE_SUCCESS = "Loan(s) deleted.";
    public static final String MESSAGE_FAILURE = "No such loan(s) found.";

    public DeleteLoanCommand(
            List<Index> targetPersonsIndices, List<Index> targetLoansIndices) throws CommandException {
        super(targetPersonsIndices, targetLoansIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        LoansManager loansManager = model.getLoansManager();
        List<PersonLoanIndexPair> pairsNotFound = new ArrayList<PersonLoanIndexPair>();
        for (int i = 0; i < personLoanIndexPairs.size(); i++) {
            try {
                Person targetPerson = loansManager.getPersonsList().get(
                        personLoanIndexPairs.get(i).getPersonIndex().getZeroBased()
                );

                Loan targetLoan = targetPerson.getLoans().get(
                        personLoanIndexPairs.get(i).getLoanIndex().getZeroBased()
                );

                loansManager.deleteLoan(targetLoan);
            } catch (IndexOutOfBoundsException | PersonNotFoundException | LoanNotFoundException e) {
                pairsNotFound.add(personLoanIndexPairs.get(i));
            }
        }

        String result = constructMultiLoanResult(pairsNotFound, MESSAGE_SUCCESS, MESSAGE_FAILURE);

        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteLoanCommand)) {
            return false;
        }

        DeleteLoanCommand otherCommand = (DeleteLoanCommand) other;
        return personLoanIndexPairs.equals(otherCommand.personLoanIndexPairs);
    }
}
