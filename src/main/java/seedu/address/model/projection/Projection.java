package seedu.address.model.projection;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.logic.commands.ProjectCommand;
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
    private Budget budget;
    private Amount projection;
    private Amount budgetProjection;
    private GradientDescent projector;
    private boolean onTrackToMeetBudget;

    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.project();
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date,
                      Budget budget) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.budget = budget;
        this.project();
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory,
                      Amount amount, Date date, Budget budget) {
        this.transactionHistory = transactionHistory;
        this.projection = amount;
        this.date = date;
        this.budget = budget;
        this.budgetProjection = new Amount(this.budget.getBudget().getIntegerValue()
                - this.projection.getIntegerValue());
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory,
                      Amount amount, Date date) {
        this.transactionHistory = transactionHistory;
        this.projection = amount;
        this.date = date;
    }

    /**
     * Computes projection for specified date based on transactionHistory
     */
    private void project() {
        double [] balances = extractBalances();
        double [] dates = extractDates();
        this.projector = new GradientDescent(balances, dates);
        int daysToProject = Date.daysBetween(Date.now(), this.date);
        this.projection = new Amount((int) Math.round(projector.predict(daysToProject)));
        if (this.budget != null) {
            int daysToBudgetDeadline = Date.daysBetween(Date.now(), this.budget.getDeadline());
            this.budgetProjection = new Amount(this.budget.getBudget().getIntegerValue()
                    - (int) Math.round(projector.predict(daysToBudgetDeadline)));
            this.onTrackToMeetBudget = budgetProjection.getActualValue() > 0;
        }
    }

    GradientDescent getProjector() {
        return this.projector;
    }

    public Optional<Budget> getBudget() {
        return Optional.ofNullable(this.budget);
    }

    public Amount getProjection() {
        return this.projection;
    }

    public Date getDate() {
        return this.date;
    }

    public ObservableList<BankAccountOperation> getTransactionHistory() {
        return this.transactionHistory;
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

    public String getBudgetForecastText() {
        if (this.getBudget().isEmpty()) {
            return "";
        }
        return this.budgetProjection.getIntegerValue() > 0
                ? String.format(ProjectCommand.MESSAGE_BUDGET_SUCCESS,
                this.budget.toString(), this.budgetProjection.toString())
                : String.format(ProjectCommand.MESSAGE_BUDGET_CAUTION,
                this.budget.toString(), this.budgetProjection.toString());
    }

    public String getBudgetForecastAbbreviatedText() {
        if (this.getBudget().isEmpty()) {
            return "";
        }
        return this.budgetProjection.getIntegerValue() > 0
                ? String.format("SURPLUS: %s", this.budgetProjection.toString())
                : String.format("DEFICIT: %S", this.budgetProjection.toString());
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
    public void displayAsStage() {
        this.project();
        Stage projectionWindow = new Stage();
        projectionWindow.setTitle("Projection Graph");
        projectionWindow.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/PalPay_32_1.png")));
        Scene scene = new Scene(new ProjectionGraph(this), 800, 600);
        projectionWindow.setScene(scene);
        projectionWindow.show();
    }

    /**
     * Checks if the given projection projects the same date as this Projection
     */
    public boolean isSameProjection(Projection other) {
        if (other == this) {
            return true;
        }
        return other != null
                && other.getDate().equals(getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Projection) {
            return this.isSameProjection((Projection) obj);
        }
        return false;
    }
}
