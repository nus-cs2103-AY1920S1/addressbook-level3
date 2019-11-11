package seedu.jarvis.model.finance;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.purchase.Purchase;

/**
 * The API of Finance Tracker component.
 */
public interface FinanceTrackerModel {

    Predicate<Purchase> PREDICATE_SHOW_ALL_PURCHASES = unused -> true;
    Predicate<Installment> PREDICATE_SHOW_ALL_INSTALLMENTS = unused -> true;

    /**
     * Gets the {@code FinanceTracker}.
     *
     * @return {@code FinanceTracker} object.
     */
    FinanceTracker getFinanceTracker();

    /**
     * Retrieves purchase at a particular index as seen on the list of finance tracker.
     */
    Purchase getPurchase(int paymentIndex);

    /**
     * Updates the filter of the purchase list to be viewed with the new predicate.
     *
     * @param predicate to filter purchases
     */
    void updateFilteredPurchaseList(Predicate<Purchase> predicate);

    /**
     * Retrieves list of purchases with current predicate applied
     *
     * @return ObservableList
     */
    ObservableList<Purchase> getFilteredPurchaseList();

    /**
     * Adds single use payment.
     *
     * @param purchase
     */
    void addPurchase(Purchase purchase);

    /**
     * Adds single use payment back into the list.
     *
     * @param zeroBasedIndex
     * @param newPurchase
     */
    void addPurchase(int zeroBasedIndex, Purchase newPurchase);

    /**
     * Deletes single use payment.
     *
     * @param itemNumber
     */
    Purchase deletePurchase(int itemNumber);

    /**
     * Undoes the adding of an purchase in finance tracker.
     *
     * @param purchase to be deleted
     */
    Purchase deletePurchase(Purchase purchase);

    /**
     * Checks for the existence of the purchase.
     *
     * @param purchase
     */
    boolean hasPurchase(Purchase purchase);

    /**
     * Retrieves installment at a particular index as seen on the list of finance tracker.
     */
    Installment getInstallment(int instalIndex);

    /**
     * Updates the filter of the installment list to be viewed with the new predicate.
     *
     * @param predicate to filter installments
     */
    void updateFilteredInstallmentList(Predicate<Installment> predicate);

    /**
     * Retrieves list of all installments.
     *
     * @return ObservableList
     */
    ObservableList<Installment> getFilteredInstallmentList();

    /**
     * Adds instalment.
     *
     * @param installment
     */
    void addInstallment(Installment installment);

    /**
     * Adds instalment.
     *
     * @param installment
     * @param zeroBasedIndex index where the purchase should be added
     */
    void addInstallment(int zeroBasedIndex, Installment installment);

    /**
     * Deletes instalment.
     *
     * @param instalNumber
     */
    Installment deleteInstallment(int instalNumber);

    /**
     * Undoes the adding of an installment in finance tracker.
     *
     * @param installment to be deleted
     */
    Installment deleteInstallment(Installment installment);

    /**
     * Checks for the existence of the same installment in the finance tracker.
     *
     * @param installment to be checked
     * @return boolean
     */
    boolean hasInstallment(Installment installment);

    /**
     * Checks for the existence of an installment with the same description in the finance tracker.
     *
     * @param installment to be checked
     */
    boolean hasSimilarInstallment(Installment installment);

    /**
     * Replaces the installment in the list with {@code editedInstallment}.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     *
     * @param target installment to be replaced
     * @param editedInstallment installment with all fields edited according to command
     */
    void setInstallment(Installment target, Installment editedInstallment);

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    void setMonthlyLimit(MonthlyLimit limit);

    /**
     * Retrieves monthly limit if it has been set by the user.
     *
     * @return Optional containing the monthly limit
     */
    Optional<MonthlyLimit> getMonthlyLimit();

    /**
     * Calculates total expenditure by user for this month.
     */
    double calculateTotalSpending();

    /**
     * Calculates total expenditure by user for this month to be rendered onto Ui.
     */
    String getTotalSpending();

    /**
     * Calculates remaining available amount by user.
     */
    double calculateRemainingAmount();

    /**
     * Calculates the remaining amount that is available by user to be rendered onto Ui.
     */
    String getRemainingAmount();
}
