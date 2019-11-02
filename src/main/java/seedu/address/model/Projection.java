package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.util.Date;
import seedu.address.model.util.GradientDescent;

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

        GradientDescent projector = new GradientDescent(this.transactionHistory);
        int daysToProject = Date.daysBetween(this.date, Date.now());
        // TODO: SLAP or find appropriate rounding method in java library
        double projectionAmount = Math.floor(projector.predict(daysToProject) * 100) / 100;
//        System.out.println(projectionAmount);
        projection = new Amount(projectionAmount);
    }

    public Amount getProjection() {
        return this.projection;
    }

    public String toString() {
        return this.projection.toString();
    }
}
