package seedu.address.overview.model;

/**
 * Manages the data model stored in the Overview tab.
 */
public class ModelManager implements Model {

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
        assert(values[0] >= 0 && values[0] <= 10000000);
        assert(values[1] >= 0 && values[1] <= 10000000);
        assert(values[2] >= 0 && values[2] <= 10000000);
        assert(values[3] >= 0 && values[3] <= 100);
        assert(values[4] >= 0 && values[4] <= 100);
        assert(values[5] >= 0 && values[5] <= 100);

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
        assert(budgetTarget >= 0 && budgetTarget <= 10000000);
        this.isNotifiedBudget = true;
        this.budgetTarget = budgetTarget;
    }

    public double getExpenseTarget() {
        return this.expenseTarget;
    }

    public void setExpenseTarget(double expenseTarget) {
        assert(expenseTarget >= 0 && expenseTarget <= 10000000);
        this.isNotifiedExpense = true;
        this.expenseTarget = expenseTarget;
    }

    public double getSalesTarget() {
        return this.salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        assert(salesTarget >= 0 && salesTarget <= 10000000);
        this.isNotifiedSales = true;
        this.salesTarget = salesTarget;
    }

    public double getBudgetThreshold() {
        return budgetThreshold;
    }

    public void setBudgetThreshold(double budgetThreshold) {
        assert(budgetThreshold >= 0 && budgetThreshold <= 100);
        this.isNotifiedBudget = true;
        this.budgetThreshold = budgetThreshold;
    }

    public double getExpenseThreshold() {
        return expenseThreshold;
    }

    public void setExpenseThreshold(double expenseThreshold) {
        assert(expenseThreshold >= 0 && expenseThreshold <= 100);
        this.isNotifiedExpense = true;
        this.expenseThreshold = expenseThreshold;
    }


    public double getSalesThreshold() {
        return salesThreshold;
    }

    public void setSalesThreshold(double salesThreshold) {
        assert(salesThreshold >= 0 && salesThreshold <= 100);
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
