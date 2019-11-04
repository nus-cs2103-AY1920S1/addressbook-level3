package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;
import seedu.address.model.util.GradientDescent;

/**
 * Represents a projection of user's balance at a set date in the future\
 */
public class Projection {

    private final ObservableList<BankAccountOperation> transactionHistory;
    private final Date date;
    private final ObservableList<Budget> budgetList;
    private Amount projection;
    private GradientDescent projector;


    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date,
                      ObservableList<Budget> budgetList) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.budgetList = budgetList;
        this.project();
    }

    /**
     * Computes projection for specified date based on transactionHistory
     */
    private void project() {
        double [] balances = extractBalances();
        double [] dates = extractDates();
        this.projector = new GradientDescent(balances, dates);
        int daysToProject = Date.daysBetween(Date.now(), this.date);
        int projectionAmount = (int) Math.round(projector.predict(daysToProject));
        projection = new Amount(projectionAmount);
        displayAsStage();
    }

    GradientDescent getProjector() {
        return this.projector;
    }

    ObservableList<Budget> getBudgetList() {
        return this.budgetList;
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

    public String toString() {
        return this.projection.toString();
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

    /**
     * Renders a graphical representation of this {@code Projection} in a separate JavaFX stage
     */
    private void displayAsStage() {
        Stage projectionWindow = new Stage();
        projectionWindow.setTitle("Projection Graph");
        projectionWindow.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/PalPay_32_1.png")));
        Scene scene = new Scene(new ProjectionGraph(this), 800, 600);
        projectionWindow.setScene(scene);
        projectionWindow.show();

    }
}
