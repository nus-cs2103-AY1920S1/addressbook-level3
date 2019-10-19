package seedu.jarvis.model.financetracker;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.purchase.Purchase;

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

    Purchase getPurchase(int paymentIndex) throws CommandException;

    void updateFilteredPurchaseList(Predicate<Purchase> predicate);

    /**
     * Retrieves list of all purchases
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
     * Deletes single use payment.
     *
     * @param itemNumber
     */
    Purchase deletePurchase(int itemNumber);

    Installment getInstallment(int instalIndex) throws CommandException;

    void updateFilteredInstallmentList(Predicate<Installment> predicate);

    /**
     * Retrieves list of all installments
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
     * Deletes instalment.
     *
     * @param instalNumber
     */
    Installment deleteInstallment(int instalNumber);

    /**
     * Checks for the existence of the same installment in the finance tracker.
     *
     * @param installment to be checked
     * @return boolean
     */
    boolean hasInstallment(Installment installment);

    /**
     * Replaces the installment {@code target} in the list with {@code editedInstallment}.
     * {@code target} must exist in the list.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     */
    void setInstallment(Installment target, Installment editedInstallment);

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
