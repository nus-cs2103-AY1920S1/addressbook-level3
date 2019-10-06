package seedu.jarvis.model.financetracker;

import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.Instalment;

public class FinanceTracker {
    private PurchaseList purchaseList;
    private InstalmentList instalmentList;
    private PaymentsOwedToUser paymentsOwedToUser;
    private PaymentsOwedByUser paymentsOwedByUser;
    private double monthlyLimit;

    public FinanceTracker() {

    }

    /**
     * Adds single use payment.
     * @param purchase
     */
    void addSinglePayment(Purchase purchase) {
        purchaseList.addSinglePurchase(purchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber of payment to be deleted
     */
    void deleteSinglePayment(int itemNumber) {
        purchaseList.deletePurchase(itemNumber);
    }

    /**
     * Adds instalment.
     *
     * @param instalment
     */
    void addInstalment(Instalment instalment) {
        instalmentList.addInstalment(instalment);
    }

    /**
     * Deletes instalment.
     *
     * @param instalmentNumber of instalment to be deleted
     * @param description to be edited
     * @param value to be edited
     */
    void editInstalment(int instalmentNumber, String description, double value) {
        instalmentList.editInstalment(instalmentNumber, description, value);
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    void setMonthlyLimit(double limit) {
        this.monthlyLimit = limit;
    }

    /**
     * Lists all purchases and payments from this month.
     */
    void listSpending() {
        purchaseList.toString(); //todo print this out nicely on UI
    }

    /**
     * Adds tab to all people involved in the transaction which will be recorded as owed payments to the user. User is
     * included in calculation of individual tab owed.
     *
     * @param description of tab paid
     * @param value paid entirely by user
     * @param persons involved in transaction
     */
    void addTab(String description, double value, String ... persons) {
        double amountOwed = calculateTabs(value, persons);
        createTabs(description, amountOwed, persons);
        createPersonalTab(amountOwed, description);
    }

    /**
     * Calculates tabs owed by persons listed.
     *
     * @param value paid entirely by the user
     * @param persons involved in the transaction
     * @return amount owed by each person
     */
    private double calculateTabs(double value, String ... persons) {
        int numPersons = persons.length + 1;
        return value / numPersons;
    }

    /**
     * Creates tabs for all persons listed.
     *
     * @param description of tab
     * @param amountOwed by each person
     * @param persons who owe tabs
     */
    private void createTabs(String description, double amountOwed, String ... persons) {
        for (String person : persons) {
            createIndividualTab(description, person, amountOwed);
        }
    }

    /**
     * Creates a tab for a person who owes money to the user.
     *
     * @param description of tab
     * @param personName of person owed
     * @param amountOwed by that person
     */
    private void createIndividualTab(String description, String personName, double amountOwed) {
        PendingPayment newPendingPayment = new PendingPayment(personName, amountOwed, description);
        newPendingPayment.setPersonOwesUser();
        paymentsOwedToUser.newPaymentOwed(newPendingPayment);
    }

    /**
     * Adds a payment by the user from the tab he paid into his list of purchases.
     *
     * @param amount spent by the user for himself
     * @param description of payment
     */
    private void createPersonalTab(double amount, String description) {
        Purchase newPurchase = new Purchase(description, amount);
        purchaseList.addSinglePurchase(newPurchase);
    }

    /**
     * Marks a tab owed to the user as paid.
     *
     * @param debtNumber
     */
    void paidTab(int debtNumber) {
        paymentsOwedToUser.userHasBeenPaid(debtNumber);
    }

    /**
     * Adds a payment owed by the user.
     *
     * @param description of tab
     * @param personName of person that user owes
     * @param amountOwed by user
     */
    void addOwedPayment(String description, String personName, double amountOwed) {
        PendingPayment newPendingPayment = new PendingPayment(personName, amountOwed, description);
        newPendingPayment.setUserInDebt();
        paymentsOwedByUser.newDebtOwed(newPendingPayment);
    }

    /**
     * Marks a payment owed by the user to someone else as paid. This will also add the user's spending to his list of
     * purchases for the month.
     *
     * @param debtNumber
     */
    void paidOwedPayment(int debtNumber) {
        PendingPayment userHasPaid = paymentsOwedByUser.userHasPaid(debtNumber);
        addOwedPayment(userHasPaid);
    }

    /**
     * Adds a payment that was previously owed to someone else to the user's spending.
     * @param pendingPayment
     */
    private void addOwedPayment(PendingPayment pendingPayment) {
        PurchaseToPerson newPayment = new PurchaseToPerson(pendingPayment.getDescription(),
                    pendingPayment.getMoneyAmount(), pendingPayment.getPersonName());
        purchaseList.addSinglePayment(newPayment);
    }
}
