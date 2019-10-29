package seedu.address.model;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.util.Date;

/**
 * Represents a projection of user's balance at a set date in the future
 */
public class Projection {

    public final ObservableList<BankAccountOperation> transactionHistory;
    public final Date date;
    public final Model model;
    private Amount projection;

    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date, Model model) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.model = model;
        this.project();
    }

    /**
     * Computes projection for specified date based on transactionHistory
     * TODO: Consider using Gradient Descent / Linear Regression to project income
     * TODO: Explore matrix operations using json, considering switching Tx history to csv
     */
    public void project() {
        Amount totalPrev = new Amount(0);
        int totalDaysElapsed = (int) DAYS.between(transactionHistory.get(0)
                .getDate().toLocalDate(), LocalDate.now());

        for (BankAccountOperation transaction : transactionHistory) {
            totalPrev = totalPrev.addAmount(transaction.getAmount());
        }
        int daysAfter = (int) DAYS.between(LocalDate.now(), date.toLocalDate());
        projection = new Amount((totalPrev.getAmount() / totalDaysElapsed) * daysAfter
                + this.model.getBankAccount().getBalance().getAmount());
    }

    public Amount getProjection() {
        return this.projection;
    }

    public String toString() {
        return this.projection.toString();
    }
}
