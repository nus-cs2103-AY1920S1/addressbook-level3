package seedu.address.overview.model;

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

    boolean checkBudgetNotif();
    boolean checkExpenseNotif();
    boolean checkSalesNotif();

    void setBudgetNotif(boolean notify);
    void setExpenseNotif(boolean notify);
    void setSalesNotif(boolean notify);
}
