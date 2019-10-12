package seedu.address.overview.model;

import seedu.address.overview.util.TransactionList;

/**
 * The API of the Model component.
 */
public interface Model {

    double getBudgetTarget ();
    void setBudgetTarget(double budgetTarget);

    double getExpenseTarget();
    void setExpenseTarget(double expenseTarget);

    double getSalesTarget();
    void setSalesTarget(double salesTarget);

    double getBudgetThreshold();
    void setBudgetThreshold(double budgetThreshold);

    double getExpenseThreshold();
    void setExpenseThreshold(double expenseThreshold);

    double getSalesThreshold();
    void setSalesThreshold(double salesThreshold);
}
