package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.util.Date;
import seedu.address.model.util.GradientDescent;

/**
 * Represents a projection of user's balance at a set date in the future\
 * TODO: implement processData() to process data into 2D matrix before passing into gradient descent
 */
public class Projection {

    private final ObservableList<BankAccountOperation> transactionHistory;
    private final Date date;
    private Amount projection;
    private GradientDescent projector;

    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.project();
    }

    /**
     * Computes projection for specified date based on transactionHistory
     * TODO: Consider using Gradient Descent / Linear Regression to project income
     * TODO: Explore matrix operations using json, considering switching Tx history to csv
     */
    public void project() {
        double [] balances = extractBalances();
        // TODO: pass in double[] for input values (or use list.toarray(double))
        double [] dates = extractDates();
        this.projector = new GradientDescent(balances, dates);
        int daysToProject = Date.daysBetween(Date.now(), this.date);
        double projectionAmount = Math.round(projector.predict(daysToProject)) / 100.0;
        System.out.println(projectionAmount);
        projection = new Amount(projectionAmount);
        Stage graph = new ProjectionLineGraph(this);
        graph.show();
    }

    public GradientDescent getProjector() {
        return this.projector;
    }

    public String toString() {
        return this.projection.toString();
    }

    /**
     * Populates an array with the number of days from each transaction till the date which this projection is
     * initialized
     */
    private double[] extractDates() {
        double[] daysFromNow = new double[this.transactionHistory.size()];
        for (int i = 0; i < daysFromNow.length; i++) {
            daysFromNow[i] = Date.daysBetween(Date.now(), transactionHistory.get(i).getDate());
        }
        return daysFromNow;
    }

    /**
     * Populates an array with the cumulative balance values
     * at the point of each transaction in {@code transactionHistory}
     */
    private double[] extractBalances() {
        double[] balances = new double[this.transactionHistory.size()];
        double cumulativeBalance = 0;
        for (int i = 0; i < balances.length; i++) {
            cumulativeBalance += transactionHistory.get(i).getAmount().getIntegerValue();
            balances[i] = cumulativeBalance;
        }
        return balances;
    }
}
