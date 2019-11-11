package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.budget.Budget;
import seedu.address.model.commons.Tag;
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

    public void setDisplayDataExpenses(ObservableList<Expense> displayData) {
        ObservableList<PieChart.Data> pieChartData = getPieChartDataFromExpenses(displayData);
        piechart.setData(pieChartData);
        piechart.setLabelLineLength(15.00);
        piechart.setTitle("Expenses");
    }

    public void setDisplayDataBudgets(ObservableList<Budget> displayData) {
        ObservableList<PieChart.Data> pieChartData = getPieChartDataFromBudgets(displayData);
        piechart.setData(pieChartData);
        piechart.setLabelLineLength(15.00);
        piechart.setTitle("Budgets");
    }

    public void setDisplayDataBudgetWithExpenses(ObservableList<Expense> displayData, Budget budget) {
        ObservableList<PieChart.Data> pieChartData = getPieChartDataFromExpensesInBudget(displayData, budget);

        piechart.setData(pieChartData);
        piechart.setLabelLineLength(15.00);
        piechart.setLabelsVisible(true);
        piechart.setTitle(budget.getName().fullName);
    }

    public ObservableList<PieChart.Data> getPieChartDataFromExpenses(ObservableList<Expense> displayData) {
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
            String name = tagName + "-" + String.format("%.1f",
                totalAmount / getOverallAmountFromExpenses(displayData) * 100) + "%";
            PieChart.Data data = new PieChart.Data(name, totalAmount);
            pieChartData.add(data);
        }

        return pieChartData;
    }

    public ObservableList<PieChart.Data> getPieChartDataFromExpensesInBudget(ObservableList<Expense> displayData,
                                                                             Budget budget) {
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

        if (budget.isBudgetPositive()) {
            double amountLeft = budget.getAmountLeft().getValue();
            for (Map.Entry<Tag, Double> entry : taggedExpenses.entrySet()) {
                String tagName = entry.getKey().tagName.equals("") ? "Untagged" : entry.getKey().tagName;
                Double totalAmount = entry.getValue();
                String name = tagName + "-" + String.format("%.1f", (
                    totalAmount / (getOverallAmountFromExpenses(displayData) + amountLeft)) * 100) + "%";
                PieChart.Data data = new PieChart.Data(name, totalAmount);
                pieChartData.add(data);
            }
            String nameLeft = "Amount Left" + "-" + String.format("%.1f", (
                amountLeft / (getOverallAmountFromExpenses(displayData) + amountLeft)) * 100) + "%";
            PieChart.Data dataLeft = new PieChart.Data(nameLeft, amountLeft);
            pieChartData.add(dataLeft);
        } else {
            for (Map.Entry<Tag, Double> entry : taggedExpenses.entrySet()) {
                String tagName = entry.getKey().tagName.equals("") ? "Untagged" : entry.getKey().tagName;
                Double totalAmount = entry.getValue();
                String name = tagName + "-" + String.format("%.1f", (
                    totalAmount / getOverallAmountFromExpenses(displayData)) * 100) + "%";
                PieChart.Data data = new PieChart.Data(name, totalAmount);
                pieChartData.add(data);
            }
        }

        return pieChartData;
    }

    public ObservableList<PieChart.Data> getPieChartDataFromBudgets(ObservableList<Budget> displayData) {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList();

        for (Budget budget : displayData) {
            String budgetName = budget.getName().fullName;
            double totalAmount = budget.getConvertedAmount().getValue();
            String name = budgetName + "-" + String.format("%.1f",
                totalAmount / getOverallAmountFromBudgets(displayData) * 100) + "%";
            PieChart.Data data = new PieChart.Data(name, totalAmount);
            pieChartData.add(data);
        }

        return pieChartData;
    }

    private double getOverallAmountFromExpenses(ObservableList<Expense> expenses) {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount().getValue();
        }
        return total;
    }

    private double getOverallAmountFromBudgets(ObservableList<Budget> budgets) {
        double total = 0;
        for (Budget budget : budgets) {
            total += budget.getConvertedAmount().getValue();
        }
        return total;
    }
}
