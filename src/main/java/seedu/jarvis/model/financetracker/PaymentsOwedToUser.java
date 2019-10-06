package seedu.jarvis.model.financetracker;

import java.util.ArrayList;

/**
 * Manages a list of payments owed to the user by others.
 */
public class PaymentsOwedToUser {
    private ArrayList<PendingPayment> pendingPaymentsToUser;

    /**
     * Empty constructor to be used when there are no pending payments owed by the user.
     */
    public PaymentsOwedToUser() {
        pendingPaymentsToUser = new ArrayList<PendingPayment>();
    }

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public PaymentsOwedToUser(ArrayList<PendingPayment> paymentsOwed) {
        if (paymentsOwed.size() == 0) {
            //todo throw exception for no existing payments owed
        } else {
            this.pendingPaymentsToUser = paymentsOwed;
        }
    }

    /**
     * Adds a new pending payment that another person owes the user.
     * todo check arguments for this command (might need to change)
     */
    public void newPaymentOwed(PendingPayment newPendingPayment) {
        pendingPaymentsToUser.add(newPendingPayment);
    }

    /**
     * Removes the pending payment after user has been paid. todo returns what tab has been paid
     */
    public PendingPayment userHasBeenPaid(int debtNumber) {
        if (debtNumber > pendingPaymentsToUser.size()) {
            //todo throw error
        }
        return pendingPaymentsToUser.remove(debtNumber - 1);
    }

    @Override
    public String toString() {
        String lstPaymentsOwed = "Here are the payments that others currently owe you: + \n";
        int index = 1;
        for (PendingPayment pendingPayment : pendingPaymentsToUser) {
            lstPaymentsOwed += index + ". " + pendingPayment.toString() + "\n";
            index++;
        }
        return lstPaymentsOwed;
    }
}
