package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.exceptions.NegativeLimitException;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.purchase.Purchase;

/**
 * Manages the overall functionality of the Finance Tracker feature of JARVIS. The finance tracker manages purchases
 * spent by the user and instalments paid.
 */
public class FinanceTracker {
    private MonthlyLimit monthlyLimit;
    private double totalSpending;

    private final PurchaseList purchaseList;
    private final FilteredList<Purchase> filteredPurchases;

    private final InstallmentList installmentList;
    private final FilteredList<Installment> filteredInstallments;

    {
        purchaseList = new PurchaseList();
        filteredPurchases = new FilteredList<>(getPurchaseList(),
                FinanceTrackerModel.PREDICATE_SHOW_ALL_PURCHASES);
        installmentList = new InstallmentList();
        filteredInstallments = new FilteredList<>(getInstallmentList(),
                FinanceTrackerModel.PREDICATE_SHOW_ALL_INSTALLMENTS);
    }

    /**
     * Constructs an empty FinanceTracker to store data from the user.
     */
    public FinanceTracker() {

    }

    //=========== Reset Methods ==================================================================================

    /**
     * Constructs a FinanceTracker with reference from another FinanceTracker,
     * updating all existing fields from another FinanceTracker.
     *
     * @param financeTracker {@code HistoryManager} whose data this {@code HistoryManager} is taking reference from.
     */
    public FinanceTracker(FinanceTracker financeTracker) {
        requireNonNull(financeTracker);
        resetData(financeTracker);
    }

    /**
     * Resets all data from {@code purchaseList} and {@code installmentList} from the given {@code financeTracker}.
     *
     * @param financeTracker
     */
    public void resetData(FinanceTracker financeTracker) {
        requireNonNull(financeTracker);
        setPurchaseList(financeTracker.getPurchaseList());
        setInstallmentList(financeTracker.getInstallmentList());
    }

    //=========== Purchase List =======================================================================

    // ========== Setter Methods ======================================================================

    public void setPurchaseList(List<Purchase> purchaseList) {
        requireNonNull(purchaseList);

        this.purchaseList.setPurchases(purchaseList);
    }

    /**
     * Replaces the given purchase {@code target} in the list with {@code editedPurchase}.
     * {@code target} must exist in the finance tracker.
     * The identity of {@code editedPurchase} must not be the same as another existing purchase in the finance tracker.
     */
    public void setPurchase(Purchase target, Purchase editedPurchase) {
        requireNonNull(editedPurchase);

        purchaseList.setPurchase(target, editedPurchase);
    }

    //=========== Getter Methods ======================================================================

    public Purchase getPurchase(int paymentIndex) {
        return purchaseList.getPurchase(paymentIndex);
    }

    public int getNumPurchases() {
        return purchaseList.getNumPurchases();
    }

    /**
     * Returns the purchase list.
     */
    public ObservableList<Purchase> getPurchaseList() {
        return purchaseList.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Purchase} backed by its internal list.
     */
    public ObservableList<Purchase> getFilteredPurchaseList() {
        return filteredPurchases;
    }

    /**
     * Updates {@code Predicate} to be applied to filter {@code filteredPurchases}.
     */
    public void updateFilteredPurchaseList(Predicate<Purchase> predicate) {
        requireNonNull(predicate);
        filteredPurchases.setPredicate(predicate);
    }

    //=========== Command Methods =====================================================================

    /**
     * Adds single use payment.
     *
     * @param purchase to be added to the finance tracker
     */
    public void addSinglePurchase(Purchase purchase) {
        requireNonNull(purchase);

        purchaseList.addSinglePurchase(purchase);
    }

    /**
     * Adds a purchase to the list at the given index.
     *
     * @param newPurchase object from newly added single-use payment
     * @param zeroBasedIndex index where the purchase should be added
     */
    public void addSinglePurchase(int zeroBasedIndex, Purchase newPurchase) {
        requireNonNull(newPurchase);

        purchaseList.addSinglePurchase(zeroBasedIndex, newPurchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber of payment to be deleted
     */
    public Purchase deleteSinglePurchase(int itemNumber) throws PurchaseNotFoundException {
        return purchaseList.deletePurchase(itemNumber);
    }

    /**
     * Deletes single use payment.
     *
     * @param purchase  deleted
     */
    public Purchase deleteSinglePurchase(Purchase purchase) throws PurchaseNotFoundException {
        requireNonNull(purchase);

        return purchaseList.deletePurchase(purchase);
    }

    /**
     * Checks for the existence of an purchase in the finance tracker.
     *
     * @param purchase to be checked
     */
    public boolean hasPurchase(Purchase purchase) {
        requireNonNull(purchase);

        return purchaseList.hasPurchase(purchase);
    }

    /**
     * Returns the total number of purchases made.
     *
     * @return number of total purchases
     */
    public int getTotalPurchases() {
        return purchaseList.getNumPurchases();
    }

    //=========== Installment List ====================================================================

    //=========== Setter Methods ======================================================================

    public void setInstallmentList(List<Installment> installmentList) {
        requireNonNull(installmentList);

        this.installmentList.setInstallments(installmentList);
    }

    /**
     * Replaces the installment {@code target} in the list with {@code editedInstallment}.
     * {@code target} must exist in the list.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     */
    public void setInstallment(Installment target, Installment editedInstallment) {
        requireNonNull(target);
        requireNonNull(editedInstallment);

        installmentList.setInstallment(target, editedInstallment);
    }

    //=========== Getter Methods ======================================================================

    public Installment getInstallment(int instalIndex) throws InstallmentNotFoundException {
        return installmentList.getInstallment(instalIndex);
    }

    /**
     * Returns the total number of installments made.
     *
     * @return number of total installments
     */
    public int getNumInstallments() {
        return installmentList.getNumInstallments();
    }

    /**
     * Returns the installment list.
     */
    public ObservableList<Installment> getInstallmentList() {
        return installmentList.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Purchase} backed by its internal list.
     */
    public ObservableList<Installment> getFilteredInstallmentList() {
        return filteredInstallments;
    }

    /**
     * Updates {@code Predicate} to be applied to filter {@code filteredInstallments}.
     */
    public void updateFilteredInstallmentList(Predicate<Installment> installment) {
        requireNonNull(installment);

        filteredInstallments.setPredicate(installment);
    }

    //=========== Command Methods =====================================================================

    /**
     * Adds installment.
     *
     * @param installment to be added to the finance tracker
     */
    public void addInstallment(Installment installment) {
        requireNonNull(installment);

        installmentList.addInstallment(installment);
    }

    /**
     * Add installment to the list at the given index.
     *
     * @param newInstallment to be added
     * @param zeroBasedIndex index where the installment should be added
     */
    public void addInstallment(int zeroBasedIndex, Installment newInstallment) {
        requireNonNull(newInstallment);

        installmentList.addInstallment(zeroBasedIndex, newInstallment);
    }

    /**
     * Deletes an installment from the finance tracker based on the index of the list.
     *
     * @param instalNumber of installment to be removed
     */
    public Installment deleteInstallment(int instalNumber) throws InstallmentNotFoundException {
        return installmentList.deleteInstallment(instalNumber);
    }

    /**
     * Deletes an existing installment from the finance tracker.
     *
     * @param installment to be removed
     */
    public Installment deleteInstallment(Installment installment) throws InstallmentNotFoundException {
        requireNonNull(installment);

        return installmentList.deleteInstallment(installment);
    }

    /**
     * Checks for the existence of an installment in the finance tracker.
     *
     * @param installment to be checked
     */
    public boolean hasInstallment(Installment installment) {
        requireNonNull(installment);

        return installmentList.hasInstallment(installment);
    }

    //=========== General Finance Tracker =============================================================

    /**
     * Retrieves monthly limit if it has been set by the user.
     * @return Optional that contains the monthly limit if it exists
     */
    public Optional<MonthlyLimit> getMonthlyLimit() {
        return Optional.of(monthlyLimit);
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    public void setMonthlyLimit(MonthlyLimit limit) {
        requireNonNull(limit);

        if (limit.getMonthlyLimit() < 0) {
            throw new NegativeLimitException();
        }
        monthlyLimit = limit;
    }

    /**
     * Lists all purchases and payments from this month.
     */
    public void listSpending() {
        totalSpending = purchaseList.totalSpending() + installmentList.getTotalMoneySpentOnInstallments();
        System.out.println("Here are your expenditures this month! Your current expenses are at: $" + totalSpending);
        installmentList.toString();
        purchaseList.toString();
    }

    //=========== Common Methods ==================================================================================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && purchaseList.equals(((FinanceTracker) other).purchaseList)
                && installmentList.equals(((FinanceTracker) other).installmentList));
    }
}
