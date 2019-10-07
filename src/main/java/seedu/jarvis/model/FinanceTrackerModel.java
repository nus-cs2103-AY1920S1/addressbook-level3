package seedu.jarvis.model;

import seedu.jarvis.model.financetracker.Instalment;
import seedu.jarvis.model.financetracker.PendingPayment;
import seedu.jarvis.model.financetracker.Purchase;

/**
 * The API of Finance Tracker component.
 */
public interface FinanceTrackerModel {

    /**
     * Adds single use payment.
     *
     * @param purchase
     */
    void addPayment(Purchase purchase);

    /**
     * Deletes single use payment.
     *
     * @param itemNumber
     */
    void deletePayment(int itemNumber);

    /**
     * Adds instalment.
     *
     * @param instalment
     */
    void addInstalment(Instalment instalment);

    /**
     * Deletes instalment.
     *
     * @param instalNumber
     */
    void deleteInstalment(int instalNumber);

    /**
     * Edits an existing instalment by its value.
     *
     * @param instalmentNumber
     * @param description
     * @param value
     */
    void editInstalmentByValue(int instalmentNumber, String description, double value);

    /**
     * Sets the monthly limit for spending.
     *
     * @param value
     */
    void setMonthlyLimit(double value);

    /**
     * Compares the expenditure between two different months.
     *
     * @param monthA
     * @param monthB
     * todo consider removing this command
     */
    //void compareMonth(String monthA, String monthB);

    /**
     * Lists all purchases and payments from this month.
     *
     */
    void listSpending();

    /**
     * Adds tab to all people involved in the transaction which will be recorded as owed payments to the user.
     *
     * @param allPayments
     */
    //void addTab(PendingPayment ...allPayments);

    /**
     * Marks a tab owed to the user as paid.
     *
     * @param pendingPayment
     */
    //void paidTab(PendingPayment pendingPayment);

    /**
     * Adds a payment owed by the user to others.
     *
     * @param pendingPayment
     */
    //void addOwedPayment(PendingPayment pendingPayment);

    /**
     * Marks a payment owed by the user as done.
     *
     * @param pendingPayment
     */
    //void paidOwedPayment(PendingPayment pendingPayment);
}
