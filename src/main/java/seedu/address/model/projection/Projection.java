package seedu.address.model.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.logic.commands.ProjectCommand;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.model.category.Category;
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
    private List<Budget> budgets = new ArrayList<>();
    private List<Amount> budgetProjections = new ArrayList<>();
    private Amount projection;
    private GradientDescent projector;
    private Category category;


    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date) {
        this.transactionHistory = transactionHistory;
        this.date = date;
        this.category = Category.GENERAL;
        this.project();
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date,
                      List<Budget> budgets) {
        this.transactionHistory = transactionHistory.sorted(new DateComparator());
        this.date = date;
        this.budgets = budgets;
        this.budgetProjections = new ArrayList<>();
        this.category = Category.GENERAL;
        this.project();
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory, Date date,
                      List<Budget> budgets, Category category) {
        this.transactionHistory = transactionHistory.sorted(new DateComparator());
        this.date = date;
        this.budgets = budgets;
        this.budgetProjections = new ArrayList<>();
        this.category = category;
        this.project();
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory,
                      Amount amount, Date date, Category category) {
        this.transactionHistory = transactionHistory.sorted(new DateComparator());
        this.projection = amount;
        this.date = date;
        this.category = category;
    }

    public Projection(ObservableList<BankAccountOperation> transactionHistory,
                      Amount amount, Date date, ObservableList<Budget> budgets, Category category) {
        this.transactionHistory = transactionHistory.sorted(new DateComparator());
        this.projection = amount;
        this.date = date;
        this.budgets = budgets;
        this.budgetProjections = new ArrayList<>();
        this.category = category;
        this.budgets.forEach(x -> {
            this.budgetProjections.add(this.projection.subtractAmount(x.getBudget()));
        });
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
        if (this.budgets != null) {
            this.budgets.forEach(x -> {
                int daysToBudgetDeadline = Date.daysBetween(Date.now(), x.getDeadline());
                this.budgetProjections.add(new Amount((int) Math.round(this.projector.predict(daysToBudgetDeadline)))
                        .subtractAmount(x.getBudget()));
            });
        }
    }

    GradientDescent getProjector() {
        return this.projector;
    }

    public List<Budget> getBudgets() {
        return this.budgets;
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

    public Category getCategory() {
        return this.category;
    }

    public boolean isGeneral() {
        return this.category.equals(Category.GENERAL);
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

    public String getBudgetForecastText(int idx) {
        if (this.getBudgets().isEmpty()) {
            return "";
        }
        return this.budgetProjections.get(idx).getIntegerValue() > 0
                ? String.format(ProjectCommand.MESSAGE_BUDGET_SUCCESS,
                this.budgets.toString(), this.budgetProjections.get(idx).toString())
                : String.format(ProjectCommand.MESSAGE_BUDGET_CAUTION,
                this.budgets.toString(), this.budgetProjections.get(idx).toString());
    }

    public Amount getBudgetProjection(int idx) {
        return this.budgetProjections.get(idx);
    }

    public String getAllBudgetForecastText() {
        StringBuilder text = new StringBuilder();
        IntStream.range(0, budgetProjections.size()).forEach(x -> {
            if (this.budgetProjections.get(x).getIntegerValue() > 0) {
                text.append(String.format(ProjectCommand.MESSAGE_BUDGET_SUCCESS, this.budgets.get(x).toString(),
                        this.budgetProjections.get(x).toString()));
            } else {
                text.append(String.format(ProjectCommand.MESSAGE_BUDGET_CAUTION, this.budgets.get(x).toString(),
                        this.budgetProjections.get(x).toString()));
            }
        });
        return text.toString();
    }

    public String getBudgetForecastAbbreviatedText(int idx) {
        if (this.budgets == null || this.budgets.get(idx) == null) {
            return "";
        }
        return this.budgetProjections.get(idx).getIntegerValue() > 0
                ? String.format("SURPLUS: %s", this.budgetProjections.get(idx).toString())
                : String.format("DEFICIT: %S", this.budgetProjections.get(idx).toString());
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
                && other.getDate().equals(getDate())
                && ((other.getCategory() == null && this.category == null)
                || (other.getCategory() != null && this.category != null
                && other.getCategory().equals(getCategory())));
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
