package seedu.address.overview.model;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.overview.storage.Storage;
import seedu.address.person.commons.core.LogsCenter;

public class ModelManager implements Model {
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Optional<Double> budgetTarget;
    private Optional<Double> expenseTarget;
    private Optional<Double> salesTarget;

    private Optional<Double> budgetThreshold;
    private Optional<Double> salesThreshold;
    private Optional<Double> expenseThreshold;

    public ModelManager(Storage storage) {
        double[] values = storage.readFromFile();

        budgetTarget = Optional.ofNullable(values[0]);
        expenseTarget = Optional.ofNullable(values[1]);
        salesTarget = Optional.ofNullable(values[2]);
        budgetThreshold = Optional.ofNullable(values[3]);
        expenseThreshold = Optional.ofNullable(values[4]);
        salesThreshold = Optional.ofNullable(values[5]);
    }

    public double getBudgetTarget () {
        return this.budgetTarget.get();
    }

    public void setBudgetTarget(double budgetTarget) {
        this.budgetTarget = Optional.of(budgetTarget);
    }

    public double getExpenseTarget() {
        return this.expenseTarget.get();
    }

    public void setExpenseTarget(double expenseTarget) {
        this.expenseTarget = Optional.of(expenseTarget);
    }

    public double getSalesTarget() {
        return this.salesTarget.get();
    }

    public void setSalesTarget(double salesTarget) {
        this.salesTarget = Optional.of(salesTarget);
    }

    public double getBudgetThreshold() {
        return budgetThreshold.get();
    }

    public void setBudgetThreshold(double budgetThreshold) {
        this.budgetThreshold = Optional.of(budgetThreshold);
    }

    public double getExpenseThreshold() {
        return expenseThreshold.get();
    }

    public void setExpenseThreshold(double expenseThreshold) {
        this.expenseThreshold = Optional.of(expenseThreshold);
    }

    public double getSalesThreshold() {
        return salesThreshold.get();
    }

    public void setSalesThreshold(double salesThreshold) {
        this.salesThreshold = Optional.of(salesThreshold);
    }

}
