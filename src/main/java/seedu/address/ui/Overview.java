package seedu.address.ui;

import static seedu.address.overview.ui.OverviewMessages.BUDGET_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.EXPENSE_PIE_CHART_TITLE;
import static seedu.address.overview.ui.OverviewMessages.EXPENSE_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.INVENTORY_PIE_CHART_TITLE;
import static seedu.address.overview.ui.OverviewMessages.INVENTORY_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.SALES_SUMMARY_TEXT;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.overview.logic.Logic;

/**
 * Defines the display for the overview tab in the user interface.
 */
public class Overview extends UiPart<Region> {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
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

    @FXML
    private BarChart<String, Double> salesBarChart;

    @FXML
    private CategoryAxis salesXAxis;

    @FXML
    private NumberAxis salesYAxis;

    @FXML
    private LineChart<String, Double> budgetBarChart;

    private Logic overviewLogic;

    public Overview(Logic overviewLogic) {
        super(FXML);

        this.overviewLogic = overviewLogic;

        initialiseLabels();
        initialiseExpensePieChart();
        initialiseInventoryPieChart();
        initialiseSalesBarChart();
        initialiseBudgetLineChart();

    }

    /**
     * Initialises the Labels in the Overview tab.
     */
    void initialiseLabels() {
        totalExpenseValue.setText(String.format(EXPENSE_SUMMARY_TEXT,
                DECIMAL_FORMAT.format(overviewLogic.getTotalExpenses()),
                DECIMAL_FORMAT.format(overviewLogic.getExpenseTarget())));
        totalInventoryValue.setText(String.format(INVENTORY_SUMMARY_TEXT,
                DECIMAL_FORMAT.format(overviewLogic.getTotalInventory())));
        totalSalesValue.setText(String.format(SALES_SUMMARY_TEXT,
                DECIMAL_FORMAT.format(overviewLogic.getTotalSales()),
                DECIMAL_FORMAT.format(overviewLogic.getSalesTarget())));
        totalAmountRemaining.setText(String.format(BUDGET_SUMMARY_TEXT,
                DECIMAL_FORMAT.format(overviewLogic.getRemainingBudget()),
                DECIMAL_FORMAT.format(overviewLogic.getBudgetTarget())));
    }

    /**
     * Initialises the Pie Chart for expenses in the Overview tab.
     */
    void initialiseExpensePieChart() {
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
        expensePieChart.setLegendVisible(false);
        //expensePieChart.setLegendSide(Side.LEFT);

    }

    /**
     * Initialises the Pie Chart for inventory in the Overview tab.
     */
    void initialiseInventoryPieChart() {
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
        inventoryPieChart.setLegendVisible(false);
        //inventoryPieChart.setLegendSide(Side.LEFT);

    }

    /**
     * Initialises the Bar CHart for Sales in the Overview tab.
     */
    void initialiseSalesBarChart() {
        LocalDate currentDate = LocalDate.now();

        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(2).getMonth().toString(),
                overviewLogic.getSalesTotalByMonth(currentDate.minusMonths(2))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(1).getMonth().toString(),
                overviewLogic.getSalesTotalByMonth(currentDate.minusMonths(1))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.getMonth().toString(),
                overviewLogic.getSalesTotalByMonth(currentDate)));

        /*XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(2).getMonth().toString(),
                Double.parseDouble(DECIMAL_FORMAT.format(
                        overviewLogic.getSalesTotalByMonth(currentDate.minusMonths(2))))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(1).getMonth().toString(),
                Double.parseDouble(DECIMAL_FORMAT.format(
                        overviewLogic.getSalesTotalByMonth(currentDate.minusMonths(1))))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.getMonth().toString(),
                Double.parseDouble(DECIMAL_FORMAT.format(overviewLogic.getSalesTotalByMonth(currentDate)))));*/

        salesBarChart.getData().add(dataSeries);
        salesBarChart.setLegendVisible(false);

    }

    /**
     * Initialises the Line Chart for Sales in the Overview tab.
     */
    void initialiseBudgetLineChart() {
        LocalDate currentDate = LocalDate.now();

        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(2).getMonth().toString(),
                overviewLogic.getBudgetLeftByMonth(currentDate.minusMonths(2))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(1).getMonth().toString(),
                overviewLogic.getBudgetLeftByMonth(currentDate.minusMonths(1))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.getMonth().toString(),
                overviewLogic.getBudgetLeftByMonth(currentDate)));

        /*XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(2).getMonth().toString(),
                Double.parseDouble(DECIMAL_FORMAT.format(
                        overviewLogic.getBudgetLeftByMonth(currentDate.minusMonths(2))))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.minusMonths(1).getMonth().toString(),
                Double.parseDouble(DECIMAL_FORMAT.format(
                     overviewLogic.getBudgetLeftByMonth(currentDate.minusMonths(1))))));
        dataSeries.getData().add(new XYChart.Data<String, Double>(currentDate.getMonth().toString(),
                Double.parseDouble(DECIMAL_FORMAT.format(overviewLogic.getBudgetLeftByMonth(currentDate)))));*/

        budgetBarChart.getData().add(dataSeries);
        budgetBarChart.setLegendVisible(false);

    }


}
