package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;

/**
 * Represents a projection of user's balance at a set date in the future
 */
public class Projection {

    public final ObservableList<Transaction> transactionHistory;
    public final Date date;
    private Amount projection;

    public Projection(ObservableList<Transaction> transactionHistory, Date date) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.project();
    }

    /**
     * Computes projection for specified date based on transactionHistory
     */
    public void project() {
        Amount totalPrev = new Amount(0);
        for (Transaction transaction : transactionHistory) {
            totalPrev = totalPrev.addAmount(transaction.getAmount());
        }
        projection = totalPrev;
    }

    public Amount getProjection() {
        return this.projection;
    }

    public String toString() {
        return this.projection.toString();
    }
}
