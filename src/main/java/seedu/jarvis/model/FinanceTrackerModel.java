package seedu.jarvis.model;

import seedu.jarvis.model.financetracker.Installment;
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
     * @param installment
     */
    void addInstallment(Installment installment);

    /**
     * Deletes instalment.
     *
     * @param instalNumber
     */
    void deleteInstallment(int instalNumber);

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
}
