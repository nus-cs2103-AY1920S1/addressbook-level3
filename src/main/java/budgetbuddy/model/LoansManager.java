package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;
import budgetbuddy.model.person.Person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Maintains a list of loans.
 */
public class LoansManager {

    private final ObservableList<Loan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Loan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public LoansManager() {}

    /**
     * Creates and fills a new list of loans.
     * @param loans A list of loans with which to fill the new list.
     */
    public LoansManager(List<Loan> loans) {
        requireNonNull(loans);
        this.internalList.setAll(loans);
    }

    /**
     * Retrieves the list of loans.
     */
    public ObservableList<Loan> getLoans() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the list of loans sorted by each loan's person's name.
     */
    public ObservableList<Loan> getSortedLoans() {
        return internalList.sorted(new SortByPerson());
    }

    /**
     * Returns a filtered list of loans belonging to the given person.
     * @param person The person to filter the list by.
     */
    public List<Loan> getFilteredLoans(Person person) {
        return getLoans().stream()
                .filter(loan -> loan.getPerson().isSamePerson(person))
                .collect(Collectors.toList());
    }

    /**
     * Returns the loan at the specified index in the list.
     * @param toGet The index of the target loan.
     * @throws LoanNotFoundException If the loan is not in the list.
     */
    public Loan getLoan(Index toGet) throws LoanNotFoundException {
        checkIndexValidity(toGet);
        return getLoans().get(toGet.getZeroBased());
    }

    /**
     * Returns the current number of loans in the list.
     * @return The current number of loans in the list as an {@code int}.
     */
    public int getLoansCount() {
        return getLoans().size();
    }

    /**
     * Returns true if the list contains the given loan.
     * @param toCheck The loan to check the list for.
     */
    public boolean containsLoan(Loan toCheck) {
        return getLoans().contains(toCheck);
    }

    /**
     * Adds a given loan to the list.
     * @param toAdd The loan to add.
     */
    public void addLoan(Loan toAdd) {
        internalList.add(toAdd);
    }

    /**
     * Replaces a target loan with the given loan.
     * @param toEdit The index of the target loan to replace.
     * @param editedLoan The edited loan to replace the target loan with.
     */
    public void editLoan(Index toEdit, Loan editedLoan) throws LoanNotFoundException {
        checkIndexValidity(toEdit);
        internalList.set(toEdit.getZeroBased(), editedLoan);
    }

    /**
     * Updates the status of a target loan to the given status.
     * @param toUpdate The index of the target loan to update.
     * @param newStatus The new status to update the target loan to.
     */
    public void updateLoanStatus(Index toUpdate, Status newStatus) {
        checkIndexValidity(toUpdate);
        internalList.get(toUpdate.getZeroBased()).setStatus(newStatus);
    }

    /**
     * Deletes a target loan from the list.
     * @param toDelete The index of the target loan to delete.
     */
    public void deleteLoan(Index toDelete) {
        checkIndexValidity(toDelete);
        internalList.remove(toDelete.getZeroBased());
    }

    /**
     * Checks if a given index exceeds the number of loans currently in the list.
     * @param toCheck The index to check.
     * @throws LoanNotFoundException If the index exceeds the current number of loans.
     */
    private void checkIndexValidity(Index toCheck) throws LoanNotFoundException {
        if (toCheck.getOneBased() > getLoansCount()) {
            throw new LoanNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoansManager)) {
            return false;
        }

        LoansManager otherLoansManager = (LoansManager) other;
        return getLoans().equals(otherLoansManager.getLoans());
    }

    /**
     * A comparator to sort loans by each of their person's names in alphabetical order.
     */
    public static class SortByPerson implements Comparator<Loan> {
        @Override
        public int compare(Loan first, Loan second) {
            return first.getPerson().getName().toString().compareTo(
                    second.getPerson().getName().toString());
        }
    }
}
