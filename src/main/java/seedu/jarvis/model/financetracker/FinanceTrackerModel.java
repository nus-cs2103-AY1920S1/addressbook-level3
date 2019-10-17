package seedu.jarvis.model.financetracker;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.purchase.Purchase;

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

    Purchase getPurchase(int paymentIndex);

    Installment getInstallment(int instalIndex);
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
    Installment deleteInstallment(int instalNumber);

    boolean hasInstallment(Installment installment);

    /**
     * Replaces the person {@code target} in the list with {@code editedInstallment}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     */
    void setInstallment(Installment target, Installment editedInstallment);

    /**
     * Retrieves list of all installments
     *
     * @return ArrayList
     */
    ArrayList<Installment> getInstallmentList();

    /**
     * Retrieves list of all purchases
     *
     * @return ArrayList
     */
    ArrayList<Purchase> getPurchaseList();

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

}
