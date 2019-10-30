package seedu.address.overview.model;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;

/**
 * Manages the data model stored in the Overview tab.
 */
public class ModelManager implements Model {
    private final Logger logger = LogsCenter.getLogger(getClass());

    private double budgetTarget;
    private double expenseTarget;
    private double salesTarget;

    private double budgetThreshold;
    private double salesThreshold;
    private double expenseThreshold;

    private boolean isNotifiedBudget = true;
    private boolean isNotifiedExpense = true;
    private boolean isNotifiedSales = true;

    public ModelManager(double[] values) {
        budgetTarget = values[0];
        expenseTarget = values[1];
        salesTarget = values[2];
        budgetThreshold = values[3];
        expenseThreshold = values[4];
        salesThreshold = values[5];
    }

    public double getBudgetTarget () {
        return this.budgetTarget;
    }

    public void setBudgetTarget(double budgetTarget) {
        this.isNotifiedBudget = true;
        this.budgetTarget = budgetTarget;
    }

    public double getExpenseTarget() {
        return this.expenseTarget;
    }

    public void setExpenseTarget(double expenseTarget) {
        this.isNotifiedExpense = true;
        this.expenseTarget = expenseTarget;
    }

    public double getSalesTarget() {
        return this.salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        this.isNotifiedSales = true;
        this.salesTarget = salesTarget;
    }

    public double getBudgetThreshold() {
        return budgetThreshold;
    }

    public void setBudgetThreshold(double budgetThreshold) {
        this.isNotifiedBudget = true;
        this.budgetThreshold = budgetThreshold;
    }

    public double getExpenseThreshold() {
        return expenseThreshold;
    }

    public void setExpenseThreshold(double expenseThreshold) {
        this.isNotifiedExpense = true;
        this.expenseThreshold = expenseThreshold;
    }


    public double getSalesThreshold() {
        return salesThreshold;
    }

    public void setSalesThreshold(double salesThreshold) {
        this.isNotifiedSales = true;
        this.salesThreshold = salesThreshold;
    }

    public boolean checkBudgetNotif() {
        return isNotifiedBudget;
    }

    public boolean checkExpenseNotif() {
        return isNotifiedExpense;
    }

    public boolean checkSalesNotif() {
        return isNotifiedSales;
    }

    public void setBudgetNotif(boolean notify) {
        this.isNotifiedBudget = notify;
    }

    public void setExpenseNotif(boolean notify) {
        this.isNotifiedExpense = notify;
    }

    public void setSalesNotif(boolean notify) {
        this.isNotifiedSales = notify;
    }

}
