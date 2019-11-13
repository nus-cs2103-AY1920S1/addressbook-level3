package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.hasDuplicates;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
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

    protected static final String MESSAGE_NO_TARGETS =
            "No loan indices or persons given as targets.";
    protected static final String MESSAGE_DUPLICATE_TARGETS =
            "Duplicate loan targets detected.";
    protected static final String MESSAGE_NO_TARGETS_HIT =
            "None of the targeted loans could be found.";

    protected static final String MESSAGE_MISSING_LOAN_INDICES =
            "\nThe following loans were not found: %s.";
    protected static final String MESSAGE_MISSING_PERSONS =
            "\nThe following persons were not found: %s.";

    protected List<Index> hitLoanIndices;
    protected List<Index> missingLoanIndices;
    protected List<Person> missingPersons;

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
        this.missingPersons = new ArrayList<Person>();

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
            boolean isInLoanList = false;
            for (int i = 0; i < loansManager.getFilteredLoans().size(); i++) {
                if (loansManager.getFilteredLoans().get(i).getPerson().isSamePerson(person)) {
                    targetLoanIndices.add(Index.fromZeroBased(i));
                    isInLoanList = true;
                }
            }
            if (!isInLoanList) {
                missingPersons.add(person);
            }
        }

        return targetLoanIndices.stream().distinct().collect(Collectors.toList());
    }


    /**
     * Executes a given operation on loans targeted using the given list of indices.
     * This method takes into account the possibility that the list size might change after an operation.
     * To combat this, the target indices are decremented if the list size changes.
     * @param operation A `Consumer` that takes an index, gets the loan with that index, and acts on the loan.
     */
    protected void actOnTargetLoans(
            LoansManager loansManager, List<Index> targetLoanIndices, Consumer<Index> operation) {
        targetLoanIndices.sort(Comparator.comparingInt(Index::getZeroBased));

        int decrementCounter = 0;
        for (Index index : targetLoanIndices) {
            try {
                int originalListSize = loansManager.getFilteredLoans().size();
                operation.accept(Index.fromZeroBased(index.getZeroBased() - decrementCounter));
                if (loansManager.getFilteredLoans().size() != originalListSize) {
                    decrementCounter++;
                }
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
    protected String constructMultiLoanResult(String successMessage) throws CommandException {

        successMessage = String.format(successMessage,
                hitLoanIndices.stream()
                        .map(index -> String.format("%d", index.getOneBased()))
                        .collect(Collectors.joining(", ")));

        if (missingLoanIndices.isEmpty() && missingPersons.isEmpty()) {
            return successMessage;
        }

        if (hitLoanIndices.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TARGETS_HIT);
        }

        if (!missingLoanIndices.isEmpty()) {
            successMessage += String.format(MESSAGE_MISSING_LOAN_INDICES,
                    missingLoanIndices.stream()
                            .map(index -> String.format("%d", index.getOneBased()))
                            .collect(Collectors.joining(", ")));
        }

        if (!missingPersons.isEmpty()) {
            successMessage += String.format(MESSAGE_MISSING_PERSONS,
                    missingPersons.stream()
                            .map(person -> person.getName().toString())
                            .collect(Collectors.joining(", ")));
        }

        return successMessage;
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
