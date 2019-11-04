package seedu.address.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSecWithApprovedClaims;

import java.util.List;

import org.jfree.data.xy.XYSeries;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;

public class PlotterTest {
    private XYSeries incomeSeries = new XYSeries("Income");
    private XYSeries claimSeries = new XYSeries("Claim");
    private XYSeries budgetSeries = new XYSeries("Budget");

    private Model model = new ModelManager(getTypicalFinSecWithApprovedClaims(), new UserPrefs());
    private List<Claim> claimList = model.getFilteredClaimList();
    private List<Income> incomeList = model.getFilteredIncomeList();


    @Test
    public void plot_income_seriesSuccess() {
        IncomePlotter incomePlotter = new IncomePlotter(incomeList);
        incomeSeries = incomePlotter.plotIncomes();
        for (int i = 0; i < 10; i++) {
            assertEquals(10000, incomeSeries.getDataItem(i).getYValue());
        }
        for (int j = 11; j <= 29; j++) {
            assertEquals(10100.10, incomeSeries.getDataItem(j).getYValue());
        }
    }

    @Test
    public void plot_claims_seriesSuccess() {
        ClaimPlotter claimPlotter = new ClaimPlotter(claimList);
        claimSeries = claimPlotter.plotClaims();

        for (int j = 0; j < 10; j++) {
            assertEquals(545.10, claimSeries.getDataItem(j).getYValue());
        }

        for (int j = 11; j < 21; j++) {
            assertEquals(866.20, claimSeries.getDataItem(j).getYValue());
        }

        for (int j = 22; j <= 29; j++) {
            assertEquals(911.30, claimSeries.getDataItem(j).getYValue());
        }
    }

    @Test
    public void plot_budget_seriesSuccess() {
        IncomePlotter incomePlotter = new IncomePlotter(incomeList);
        incomeSeries = incomePlotter.plotIncomes();
        ClaimPlotter claimPlotter = new ClaimPlotter(claimList);
        claimSeries = claimPlotter.plotClaims();
        BudgetPlotter budgetPlotter = new BudgetPlotter(incomeSeries, claimSeries);
        budgetSeries = budgetPlotter.plotBudget();
        for (int i = 0; i < 10; i++) {
            assertEquals(9454.90, budgetSeries.getDataItem(i).getYValue());
        }
        for (int j = 11; j < 21; j++) {
            assertEquals(9233.90, budgetSeries.getDataItem(j).getYValue());
        }
        for (int j = 22; j <= 29; j++) {
            assertEquals(9188.80, budgetSeries.getDataItem(j).getYValue());
        }
    }

}
