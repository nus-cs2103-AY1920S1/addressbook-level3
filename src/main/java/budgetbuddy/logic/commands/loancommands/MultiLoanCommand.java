package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.hasDuplicates;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;
import budgetbuddy.model.loan.util.PersonLoanIndexPair;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a command that can handle multiple targeted loans.
 */
public abstract class MultiLoanCommand extends Command {

    protected List<PersonLoanIndexPair> pairsNotFound;
    protected List<Index> personsNotFound;

    private List<PersonLoanIndexPair> personLoanIndexPairs;
    private List<Index> personIndices;

    /**
     * Creates a command that will act on multiple loans, based on two given lists.
     * @param personLoanIndexPairs A list of person-loan index pairs.
     *                             Each loan (belonging to the corresponding person) will be acted upon.
     * @param personIndices A list of person indices. All loans in ach person's loan list will be acted upon.
     * @throws CommandException If errors are present in the lists.
     */
    public MultiLoanCommand(
            List<PersonLoanIndexPair> personLoanIndexPairs, List<Index> personIndices) throws CommandException {
        requireAllNonNull(personLoanIndexPairs, personIndices);
        checkArguments(personLoanIndexPairs, personIndices);
        this.personLoanIndexPairs = personLoanIndexPairs;
        this.personIndices = personIndices;
    }

    /**
     * Checks for errors in the given arguments during construction.
     * @throws CommandException If both lists are empty, if either list has repeated entries,
     * or if a person's index appears in both lists.
     */
    private static void checkArguments(
            List<PersonLoanIndexPair> personLoanIndexPairs, List<Index> personIndices) throws CommandException {
        requireAllNonNull(personLoanIndexPairs, personIndices);

        if (personLoanIndexPairs.isEmpty() && personIndices.isEmpty()) {
            throw new CommandException("No person or loan indices given to multi-loan command.");
        }

        if (hasDuplicates(personLoanIndexPairs) || hasDuplicates(personIndices)) {
            throw new CommandException("At least one list contains duplicate indices.");
        }

        List<Index> allPersonIndices = new ArrayList<Index>();
        List<Index> otherPersonIndices = personLoanIndexPairs
                .stream()
                .map(PersonLoanIndexPair::getPersonIndex)
                .distinct() // duplicate persons is ok here, since it indicates a subset of that person's loans
                .collect(Collectors.toUnmodifiableList());

        allPersonIndices.addAll(otherPersonIndices);
        allPersonIndices.addAll(personIndices);
        if (hasDuplicates(allPersonIndices)) {
            throw new CommandException("Person's loans marked for deletion more than once.");
        }
    }

    /**
     * Constructs a list of target loans to be acted upon by this command.
     * Both the person-loan index pair list and the person index list are used in the construction.
     * @return The final list of loans to be acted upon (as a {@code LoanList}).
     */
    protected LoanList constructTargetLoanList(LoansManager loansManager) {
        LoanList targetLoans = new LoanList();

        for (PersonLoanIndexPair pair : personLoanIndexPairs) {
            try {
                Person targetPerson = loansManager.getPersonsList().get(pair.getPersonIndex().getZeroBased());
                Loan targetLoan = targetPerson.getLoans().get(pair.getLoanIndex().getZeroBased());
                targetLoans.add(targetLoan);
            } catch (IndexOutOfBoundsException e) {
                pairsNotFound.add(pair);
            }
        }

        for (Index personIndex : personIndices) {
            try {
                Person targetPerson = loansManager.getPersonsList().get(personIndex.getZeroBased());
                targetPerson.getLoans().forEach(targetLoans::add);
            } catch (IndexOutOfBoundsException e) {
                personsNotFound.add(personIndex);
            }
        }

        return targetLoans;
    }

    /**
     * Executes a given operation on each loan in the given list of loans.
     * @throws CommandException If the operation fails on any loan.
     */
    protected void actOnTargetLoans(LoanList targetLoans, Consumer<Loan> operation) throws CommandException {
        for (Loan targetLoan : targetLoans) {
            try {
                operation.accept(targetLoan);
            } catch (PersonNotFoundException | LoanNotFoundException e) {
                throw new CommandException("Execution failure");
            }
        }
    }

    /**
     * Assembles a result message that takes into account targeted loans not found in the loans manager.
     * @param successMessage Message for when at least one target was found.
     * @param failureMessage Message for when no targets were found at all.
     * @return The result message.
     */
    protected String constructMultiLoanResult(String successMessage, String failureMessage) {

        if (pairsNotFound.isEmpty() && personsNotFound.isEmpty()) {
            return successMessage;
        }

        if (pairsNotFound.size() == personLoanIndexPairs.size()
                && personsNotFound.size() == personIndices.size()) {
            return failureMessage;
        }

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(successMessage).append(" ").append("The following loans were not found:\n");
        for (PersonLoanIndexPair missingPair : pairsNotFound) {
            resultMessage.append("  Loan ")
                    .append(missingPair.getPersonIndex().getOneBased())
                    .append(".")
                    .append(missingPair.getLoanIndex().getOneBased())
                    .append("\n");
        }
        for (Index personIndex : personsNotFound) {
            resultMessage.append("  Loans for Person ").append(personIndex.getOneBased());
        }

        return resultMessage.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MultiLoanCommand)) {
            return false;
        }

        MultiLoanCommand otherCommand = (MultiLoanCommand) other;
        return personLoanIndexPairs.equals(otherCommand.personLoanIndexPairs)
                && personIndices.equals(otherCommand.personIndices);
    }
}
