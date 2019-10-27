package seedu.address.model.budget;

import org.jfree.data.xy.XYSeries;

public class BudgetPlotter {

    private XYSeries incomeSeries;
    private XYSeries claimSeries;
    private XYSeries budgetSeries = new XYSeries("Budget");


    BudgetPlotter(XYSeries incomeSeries, XYSeries claimSeries) {
        this.incomeSeries = incomeSeries;
        this.claimSeries = claimSeries;
    }

    /**
     * Plots points of budget values
     */

    XYSeries plotBudget() {
        double currentBudget;
            for (int day = 1; day <= 31; day++) {
                currentBudget = incomeSeries.indexOf(day) - claimSeries.indexOf(day);
                budgetSeries.add(day, currentBudget);
            }
        return budgetSeries;
    }
}