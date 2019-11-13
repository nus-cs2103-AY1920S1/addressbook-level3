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
import budgetbuddy.model.loan.exceptions.DuplicateLoanException;
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

    /**
     * The filtered list wraps the observable {@code internalList}.
     * It changes with the filters used with {@code LoanListCommand}, leaving {@code internalList} unchanged.
     */
    private final FilteredList<Loan> filteredLoans = new FilteredList<Loan>(internalUnmodifiableList);

    private Comparator<Loan> sorter;

    public LoansManager() {}

    /**
     * Creates the loans manager with a given list of loans.
     * @param loans The list of loans to initialize the loans manager with.
     */
    public LoansManager(List<Loan> loans) {
        requireNonNull(loans);
        this.internalList.setAll(loans);
        this.sorter = DATE_NEWEST;
    }

    /**
     * Creates the loans manager with a given list of loans and a given list of debtors.
     * @param loans The list of loans to initialize the loans manager with.
     * @param debtors The list of debtors to initialize the loans manager with.
     */
    public LoansManager(List<Loan> loans, List<Debtor> debtors) {
        this(loans);
        requireNonNull(debtors);
        setDebtors(debtors);
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
        requireNonNull(sorter);
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
     * Returns the loan at the specified index in the filtered list.
     * @param toGet The index of the target loan in the filtered list.
     * @throws LoanNotFoundException If the loan is not in the filtered list.
     */
    public Loan getLoan(Index toGet) throws LoanNotFoundException {
        checkIndexValidityInFilteredList(toGet);
        return filteredLoans.get(toGet.getZeroBased());
    }

    /**
     * Returns the current number of loans in the list.
     * @return The current number of loans in the list as an {@code int}.
     */
    public int getLoansCount() {
        return getLoans().size();
    }

    /**
     * Adds a given loan to the list.
     * @param toAdd The loan to add.
     */
    public void addLoan(Loan toAdd) throws DuplicateLoanException {
        if (internalList.stream().anyMatch(loan -> loan.isSameLoan(toAdd))) {
            throw new DuplicateLoanException();
        }
        internalList.add(0, toAdd);
        internalList.sort(sorter);
        updateFilteredList(FILTER_ALL);
    }

    /**
     * Replaces a target loan in the filtered list with the given loan.
     * @param toEdit The index of the target loan to replace in the filtered list
     * @param editedLoan The edited loan to replace the target loan with.
     */
    public void editLoan(Index toEdit, Loan editedLoan) throws LoanNotFoundException, DuplicateLoanException {
        if (internalList.stream().anyMatch(loan -> loan.isSameLoan(editedLoan))) {
            throw new DuplicateLoanException();
        }
        checkIndexValidityInFilteredList(toEdit);
        internalList.set(filteredLoans.getSourceIndex(toEdit.getZeroBased()), editedLoan);
        internalList.sort(sorter);
    }

    /**
     * Updates a target loan in the filtered list to the given loan.
     * @param toUpdate The index of the loan in the filtered list to update.
     * @param updatedLoan The updated loan; it should be identical to the target loan except for (maybe) its status.
     * @throws LoanNotFoundException If the loan is not found in the filtered list.
     */
    public void updateStatus(Index toUpdate, Loan updatedLoan) throws LoanNotFoundException {
        checkIndexValidityInFilteredList(toUpdate);
        internalList.set(filteredLoans.getSourceIndex(toUpdate.getZeroBased()), updatedLoan);
    }

    /**
     * Deletes a target loan from the filtered list.
     * @param toDelete The index of the target loan to delete in the filtered list.
     */
    public void deleteLoan(Index toDelete) throws LoanNotFoundException {
        checkIndexValidityInFilteredList(toDelete);
        Loan inFilteredList = filteredLoans.get(toDelete.getZeroBased());
        internalList.remove(inFilteredList);
    }

    /**
     * Checks if a given index exceeds the number of loans currently in the filtered list.
     * @param toCheck The index to check.
     * @throws LoanNotFoundException If the index exceeds the current number of loans in the filtered list.
     */
    private void checkIndexValidityInFilteredList(Index toCheck) throws LoanNotFoundException {
        if (toCheck.getOneBased() > getFilteredLoans().size()) {
            throw new LoanNotFoundException();
        }
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
        return getLoans().equals(otherLoansManager.getLoans())
                && getDebtors().equals(otherLoansManager.getDebtors())
                && getFilteredLoans().equals(otherLoansManager.getFilteredLoans())
                && sorter.equals(otherLoansManager.sorter);
    }
}
