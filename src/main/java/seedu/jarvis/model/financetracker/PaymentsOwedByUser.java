package seedu.jarvis.model.financetracker;

import java.util.ArrayList;

/**
 * Manages a list of debts owed by the user to others.
 */
public class PaymentsOwedByUser {
    private ArrayList<PendingPayment> pendingPaymentsByUser;

    /**
     * Empty constructor to be used when there are no pending payments owed to the user.
     */
    public PaymentsOwedByUser() {
        pendingPaymentsByUser = new ArrayList<PendingPayment>();
    }

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public PaymentsOwedByUser(ArrayList<PendingPayment> paymentsOwed) {
        if (paymentsOwed.size() == 0) {
            //todo throw exception for no existing payments owed
        } else {
            this.pendingPaymentsByUser = paymentsOwed;
        }
    }

    /**
     * Adds a new pending payment that the user owes to another person.
     * todo check arguments for this command (might need to change)
     */
    public void newDebtOwed(PendingPayment newPendingPayment) {
        /*PendingPayment newPendingPayment = new PendingPayment(name, moneyAmount);
        newPendingPayment.setUserInDebt();*/
        pendingPaymentsByUser.add(newPendingPayment);
    }

    /**
     *  Removes the pending payment after user has paid. Amount paid will be returned to create a new payment in the
     *  spending list.
     * @return double the amount that has been paid by the user
     */
    public double userHasPaid(int debtNumber) {
        if (debtNumber > pendingPaymentsByUser.size()) {
            //todo throw error
        }
        double amountPaid = pendingPaymentsByUser.get(debtNumber - 1).getMoneyAmount();
        pendingPaymentsByUser.remove(debtNumber - 1);
        return amountPaid;
    }

    @Override
    public String toString() {
        String lstPaymentsOwed = "Here are the payments that you currently owe to others: + \n";
        int index = 1;
        for (PendingPayment pendingPayment : pendingPaymentsByUser) {
            lstPaymentsOwed += index + ". " + pendingPayment.toString() + "\n";
            index++;
        }
        return lstPaymentsOwed;
    }
}
