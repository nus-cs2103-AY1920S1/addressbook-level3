package seedu.jarvis.model.financetracker;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * The API of Finance Tracker component.
 */
public interface FinanceTrackerModel {

    Predicate<Purchase> PREDICATE_SHOW_ALL_PURCHASES = unused -> true;

    /**
     * Gets the {@code FinanceTracker}.
     *
     * @return {@code FinanceTracker} object.
     */
    FinanceTracker getFinanceTracker();

    /**
     * Replaces {@code FinanceTracker} data with the data in {@code FinanceTracker} given as argument.
     *
     * @param financeTracker {@code FinanceTracker} data to be used.
     */
    void setFinanceTracker(FinanceTracker financeTracker);

    public Purchase getPurchase(int paymentIndex);

    public Installment getInstallment(int instalIndex);
    /**
     * Adds single use payment.
     *
     * @param purchase
     */
    void addPurchase(Purchase purchase);

    /**
     * Deletes single use payment.
     *
     * @param itemNumber
     */
    void deletePurchase(int itemNumber);

    boolean hasPurchase(Purchase purchase);

    /**
     * Adds instalment.
     *
     * @param installment
     */
    void addInstallment(Installment installment);

    /**
     * Deletes instalment.
     *
     * @param instalNumber
     */
    void deleteInstallment(int instalNumber);

    boolean hasInstallment(Installment installment);

    /**
     * Edits an existing instalment by its value.
     *
     * @param installmentNumber
     * @param description
     * @param value
     */
    void editInstallmentByValue(int installmentNumber, String description, double value);

    /**
     * Sets the monthly limit for spending.
     *
     * @param value
     */
    void setMonthlyLimit(double value);

    /**
     * Lists all purchases and payments from this month.
     *
     */
    void listSpending();

    ObservableList<Purchase> getPurchasesList();

    /**
     * Updates the filter of the filtered purchase list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPurchaseList(Predicate<Purchase> predicate);

    ObservableList<Purchase> getFilteredPurchaseList();
}
