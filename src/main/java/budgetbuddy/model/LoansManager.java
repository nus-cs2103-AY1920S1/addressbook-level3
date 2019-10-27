package budgetbuddy.model;

import static budgetbuddy.model.loan.LoanFilters.FILTER_ALL;
import static budgetbuddy.model.loan.LoanSorters.DATE_NEWEST;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.loan.Debtor;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Maintains a list of loans.
 */
public class LoansManager {

    private final ObservableList<Loan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Loan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * A list to store the results of the {@code loan split} command.
     */
    private final ObservableList<Debtor> debtors = FXCollections.observableArrayList();
    private final ObservableList<Debtor> unmodifiableDebtors =
            FXCollections.unmodifiableObservableList(debtors);
    private final FilteredList<Loan> filteredLoans = new FilteredList<Loan>(internalUnmodifiableList);

    private Comparator<Loan> sorter;

    public LoansManager() {}

    /**
     * Creates and fills a new list of loans.
     * @param loans A list of loans with which to fill the new list.
     */
    public LoansManager(List<Loan> loans) {
        requireNonNull(loans);
        this.internalList.setAll(loans);
        this.sorter = DATE_NEWEST;
    }

    /**
     * Updates the predicate of {@code filteredLoans} with the given predicate.
     * @param predicate
     */
    public void updateFilteredList(Predicate<Loan> predicate) {
        requireNonNull(predicate);
        filteredLoans.setPredicate(predicate);
    }

    //========================================= Loan Methods ===========================================

    /**
     * Sorts the {@code internalList} using the given {@code sorter}.
     * Also sets the loan manager to use the given {@code sorter} for sorting.
     */
    public void sortLoans(Comparator<Loan> sorter) {
        this.sorter = this.sorter.equals(sorter) ? sorter.reversed() : sorter;
        internalList.sort(this.sorter);
    }

    /**
     * Retrieves the list of loans.
     */
    public ObservableList<Loan> getLoans() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the filtered list of loans.
     */
    public ObservableList<Loan> getFilteredLoans() {
        return filteredLoans;
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
        internalList.add(0, toAdd);
        internalList.sort(sorter);
        updateFilteredList(FILTER_ALL);
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

    /**
     * Returns a {@code Comparator} that sorts loans by their person's name in alphabetical order.
     */
    public Comparator<Loan> personAlphabeticalSorter() {
        return Comparator.comparing(loan -> loan.getPerson().getName().toString());
    }

    //========================================= Split/Debtor Methods ===================================

    /**
     * Sets the elements of the list of debtors to the given list of debtors.
     */
    public void setDebtors(List<Debtor> debtors) {
        requireNonNull(debtors);
        this.debtors.setAll(debtors);
    }

    /**
     * Returns an unmodifiable {@code SortedList} of debtors.
     * The list is sorted by the debtors' names in alphabetical order.
     */
    public SortedList<Debtor> getDebtors() {
        return unmodifiableDebtors.sorted(Comparator.comparing(debtor -> debtor.getDebtor().getName().toString()));
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
}
