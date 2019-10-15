package seedu.address.ui;

import static seedu.address.overview.ui.OverviewMessages.BUDGET_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.EXPENSE_PIE_CHART_TITLE;
import static seedu.address.overview.ui.OverviewMessages.EXPENSE_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.INVENTORY_PIE_CHART_TITLE;
import static seedu.address.overview.ui.OverviewMessages.INVENTORY_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.SALES_SUMMARY_TEXT;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.overview.logic.Logic;

/**
 * Defines the display for the overview tab in the user interface.
 */
public class Overview extends UiPart<Region> {

    private static final String FXML = "Overview.fxml";

    @FXML
    private Label totalExpenseValue;

    @FXML
    private Label totalInventoryValue;

    @FXML
    private Label totalSalesValue;

    @FXML
    private Label totalAmountRemaining;

    @FXML
    private PieChart expensePieChart;

    @FXML
    private PieChart inventoryPieChart;

    public Overview(Logic overviewLogic) {
        super(FXML);

        initialiseLabels(overviewLogic);
        initialiseExpensePieChart(overviewLogic);
        initialiseInventoryPieChart(overviewLogic);

    }

    /**
     * Initialises the Labels in the Overview tab.
     * @param overviewLogic the logic for the overview tab.
     */
    void initialiseLabels(Logic overviewLogic) {
        totalExpenseValue.setText(String.format(EXPENSE_SUMMARY_TEXT, overviewLogic.getTotalExpenses(),
                overviewLogic.getExpenseTarget()));
        totalInventoryValue.setText(String.format(INVENTORY_SUMMARY_TEXT, overviewLogic.getTotalInventory()));
        totalSalesValue.setText(String.format(SALES_SUMMARY_TEXT, overviewLogic.getTotalSales(),
                overviewLogic.getSalesTarget()));
        totalAmountRemaining.setText(String.format(BUDGET_SUMMARY_TEXT, overviewLogic.getRemainingBudget(),
                overviewLogic.getBudgetTarget()));
    }

    /**
     * Initialises the Pie Chart for expenses in the Overview tab.
     * @param overviewLogic the logic for the overview tab.
     */
    void initialiseExpensePieChart(Logic overviewLogic) {
        List<PieChart.Data> pieChartData = new ArrayList<>();
        List<String> categoryList = overviewLogic.getTransactionCategories();

        for (int i = 0; i < categoryList.size(); i++) {
            pieChartData.add(new PieChart.Data(categoryList.get(i),
                    overviewLogic.getTransactionTotalByCategory(categoryList.get(i))));
        }

        ObservableList<PieChart.Data> dataToDisplay = FXCollections.observableList(pieChartData);

        expensePieChart.setData(dataToDisplay);
        expensePieChart.setTitle(EXPENSE_PIE_CHART_TITLE);
        expensePieChart.setClockwise(true);
        expensePieChart.setLabelLineLength(20);
        expensePieChart.setLabelsVisible(true);
        expensePieChart.setStartAngle(180);

    }

    /**
     * Initialises the Pie Chart for inventory in the Overview tab.
     * @param overviewLogic the logic for the overview tab.
     */
    void initialiseInventoryPieChart(Logic overviewLogic) {
        List<PieChart.Data> pieChartData = new ArrayList<>();
        List<String> categoryList = overviewLogic.getInventoryCategories();

        for (int i = 0; i < categoryList.size(); i++) {
            pieChartData.add(new PieChart.Data(categoryList.get(i),
                    overviewLogic.getInventoryTotalByCategory(categoryList.get(i))));
        }

        ObservableList<PieChart.Data> dataToDisplay = FXCollections.observableList(pieChartData);

        inventoryPieChart.setData(dataToDisplay);
        inventoryPieChart.setTitle(INVENTORY_PIE_CHART_TITLE);
        inventoryPieChart.setClockwise(true);
        inventoryPieChart.setLabelLineLength(20);
        inventoryPieChart.setLabelsVisible(true);
        inventoryPieChart.setStartAngle(180);
    }

}
