package seedu.address.model.budget;

import org.jfree.data.xy.XYSeries;

/**
 *  Represents the budget portion of the Budget graph
 */

public class BudgetPlotter {

    private XYSeries incomeSeries;
    private XYSeries claimSeries;
    private XYSeries budgetSeries = new XYSeries("Budget");


    BudgetPlotter(XYSeries incomeSeries, XYSeries claimSeries) {
        this.incomeSeries = incomeSeries;
        this.claimSeries = claimSeries;
    }

    /**
     * Plots the points of budget values
     */

    XYSeries plotBudget() {
        double currentBudget;
        for (int day = 1; day <= 30; day++) {
            currentBudget = incomeSeries.getDataItem(day - 1).getYValue()
                    - claimSeries.getDataItem(day - 1).getYValue();
            currentBudget = Math.round(currentBudget * 100) / 100.0;
            budgetSeries.add(day, currentBudget);
        }
        return budgetSeries;
    }
}
