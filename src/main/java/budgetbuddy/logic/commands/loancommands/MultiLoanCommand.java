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
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;
import budgetbuddy.model.person.Person;

/**
 * Represents a command that can handle multiple targeted loans.
 */
public abstract class MultiLoanCommand extends Command {

    protected static final String MULTI_LOAN_SYNTAX = "<loan number... > [p/<person> ...]";
    protected static final String MULTI_LOAN_SYNTAX_EXAMPLE = "1 3 4 p/Peter p/Mary";

    private static final String MESSAGE_NO_TARGETS =
            "No loan indices or persons given as targets.";
    private static final String MESSAGE_DUPLICATE_TARGETS =
            "Duplicate loan targets detected.";
    private static final String MESSAGE_NO_TARGETS_HIT =
            "None of the targeted loans could be found.";

    protected List<Index> hitLoanIndices;
    protected List<Index> missingLoanIndices;

    private List<Index> loanIndices;
    private List<Person> persons;

    /**
     * Constructs a command capable of targeting multiple loans to act upon.
     * @param loanIndices A list of loan indices to target.
     * @param persons A list of persons; loans belonging to any of these persons will be targeted.
     * @throws CommandException If {@link #checkTargetLists} throws a {@code CommandException}.
     */
    public MultiLoanCommand(List<Index> loanIndices, List<Person> persons) throws CommandException {
        requireAllNonNull(loanIndices, persons);

        this.loanIndices = new ArrayList<Index>();
        this.persons = new ArrayList<Person>();
        this.hitLoanIndices = new ArrayList<Index>();
        this.missingLoanIndices = new ArrayList<Index>();

        checkTargetLists(loanIndices, persons);
        this.loanIndices.addAll(loanIndices);
        this.persons.addAll(persons);
    }

    /**
     * Checks for errors in the given lists during construction.
     * @throws CommandException If both lists are empty, if either list has repeated entries.
     */
    private static void checkTargetLists(List<Index> loanIndices, List<Person> persons) throws CommandException {
        requireAllNonNull(loanIndices, persons);

        if (loanIndices.isEmpty() && persons.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TARGETS);
        }

        if (hasDuplicates(loanIndices) || hasDuplicates(persons)) {
            throw new CommandException(MESSAGE_DUPLICATE_TARGETS);
        }
    }

    /**
     * Constructs and a sorted list of target loan indices to be acted upon by this command.
     * Both the loan indices and persons list are used in the construction.
     * Duplicates detected in the final list are removed.
     */
    protected List<Index> constructTargetLoanIndicesList(LoansManager loansManager) {
        List<Index> targetLoanIndices = new ArrayList<Index>(loanIndices);

        for (Person person : persons) {
            for (int i = 0; i < loansManager.getLoansCount(); i++) {
                Index index = Index.fromZeroBased(i);
                if (loansManager.getLoan(index).getPerson().isSamePerson(person)) {
                    targetLoanIndices.add(index);
                }
            }
        }

        targetLoanIndices = targetLoanIndices.stream().distinct().collect(Collectors.toList());
        return targetLoanIndices;
    }


    /**
     * Executes a given operation on loans targeted using the given list of indices.
     * @param operation A `Consumer` that takes an index, gets the loan with that index, and acts on the loan.
     */
    protected void actOnTargetLoans(List<Index> targetLoanIndices, Consumer<Index> operation) {
        for (Index index : targetLoanIndices) {
            try {
                operation.accept(index);
                hitLoanIndices.add(index);
            } catch (LoanNotFoundException e) {
                missingLoanIndices.add(index);
            }
        }
    }

    /**
     * Assembles a result message that takes into account targeted loans not found in the loans manager.
     * @param successMessage Message for when at least one target was found and acted upon.
     * @return The result message.
     */
    protected String constructMultiLoanResult(String successMessage) {

        successMessage = String.format(
                successMessage,
                hitLoanIndices.stream()
                        .map(index -> String.format("%d", index.getOneBased()))
                        .collect(Collectors.joining(", ")));

        if (missingLoanIndices.isEmpty()) {
            return successMessage;
        }

        if (hitLoanIndices.isEmpty()) {
            return MESSAGE_NO_TARGETS_HIT;
        }

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(successMessage).append("\n").append("However, the following loans were not found: ");

        for (Index missingIndex : missingLoanIndices) {
            resultMessage
                    .append(String.format("%d", missingIndex.getOneBased()))
                    .append(", ");
        }

        resultMessage.delete(resultMessage.length() - 2, resultMessage.length() - 1); // remove ", " at end
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
        return loanIndices.equals(otherCommand.loanIndices)
                && persons.equals(otherCommand.persons);
    }
}
