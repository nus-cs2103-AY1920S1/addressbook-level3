package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;

/**
 * Class to display the statistics.
 */
public class StatsDisplay extends UiPart<Region> {

    private static final String FXML = "StatsDisplay.fxml";

    @FXML
    private PieChart piechart;

    public StatsDisplay() {
        super(FXML);
    }

    public void setDisplayData(ObservableList<Expense> displayData) {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList();
        for (Expense expense : displayData) {
            PieChart.Data data = new PieChart.Data(expense.getName().fullName, expense.getAmount().getValue());
            pieChartData.add(data);
        }
        piechart.setData(pieChartData);
        piechart.setLabelLineLength(10.00);
        piechart.setLabelsVisible(true);
        piechart.setTitle("Expenses");
    }

    public void setDisplayDataBudget(ObservableList<Expense> displayData, Budget budget) {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList();
        for (Expense expense : displayData) {
            PieChart.Data data = new PieChart.Data(expense.getName().fullName, expense.getAmount().getValue());
            pieChartData.add(data);
        }
        PieChart.Data data1 = new PieChart.Data("amountLeft", budget.getAmountLeft().getValue());
        pieChartData.add(data1);
        piechart.setData(pieChartData);
        piechart.setLabelLineLength(10.00);
        piechart.setLabelsVisible(true);
        piechart.setTitle("Expenses");
    }
}
