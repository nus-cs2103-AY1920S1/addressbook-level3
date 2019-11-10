package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.tag.Tag;

/**
 * Class to display the statistics.
 */
public class StatsDisplay extends UiPart<Region> {

    private static final String FXML = "StatsDisplay.fxml";

    @FXML
    private PieChart piechart;

    public StatsDisplay() {
        super(FXML);
        piechart.setPrefWidth(500);
        piechart.setPrefHeight(500);
    }

    public void setDisplayData(ObservableList<Expense> displayData) {
        ObservableList<PieChart.Data> pieChartData = getPieChartData(displayData);
        piechart.setData(pieChartData);
        piechart.setLabelLineLength(10.00);
        piechart.setLabelsVisible(true);
        piechart.setTitle("Expenses");
    }

    public void setDisplayDataBudget(ObservableList<Expense> displayData, Budget budget) {
        ObservableList<PieChart.Data> pieChartData = getPieChartData(displayData);

        if (budget.isBudgetPositive()) {
            PieChart.Data data1 = new PieChart.Data("Amount Left", budget.getAmountLeft().getValue());
            pieChartData.add(data1);
        }

        piechart.setData(pieChartData);
        piechart.setLabelLineLength(10.00);
        piechart.setLabelsVisible(true);
        piechart.setTitle(budget.getName().fullName);
    }

    public ObservableList<PieChart.Data> getPieChartData(ObservableList<Expense> displayData) {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList();

        HashMap<Tag, Double> taggedExpenses = new HashMap<>();

        for (Expense expense : displayData) {
            Tag expenseTag = expense.getTag();
            if (taggedExpenses.containsKey(expenseTag)) {
                Double currentTotalAmount = taggedExpenses.get(expenseTag);
                Double newTotalAmount = currentTotalAmount + expense.getAmount().getValue();
                taggedExpenses.put(expenseTag, newTotalAmount);
            } else {
                taggedExpenses.put(expenseTag, expense.getAmount().getValue());
            }
        }

        for (Map.Entry<Tag, Double> entry : taggedExpenses.entrySet()) {
            String tagName = entry.getKey().tagName.equals("") ? "Untagged" : entry.getKey().tagName;
            Double totalAmount = entry.getValue();
            PieChart.Data data = new PieChart.Data(tagName, totalAmount);
            pieChartData.add(data);
        }

        return pieChartData;
    }
}


