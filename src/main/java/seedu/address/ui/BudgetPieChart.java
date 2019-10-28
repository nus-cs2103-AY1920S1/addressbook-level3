package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Spending;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class BudgetPieChart extends UiPart<Region> {

    private static final String FXML = "BudgetPieChart.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Budget budget;

    @FXML
    private HBox cardPane;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label id;
    @FXML
    private VBox wrapper;

    public BudgetPieChart(Budget budget, int displayedIndex) {
        super(FXML);
        this.budget = budget;
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        Double remaining = budget.getRemainingAmount().doubleValue();
        if (remaining < 0) {
            pieChartData.add(new PieChart.Data("Overshot by: $", Math.abs(remaining)));
        } else {
            pieChartData.add(new PieChart.Data("Remaining: $", remaining));
        }
        for (Spending spending : budget.getSpendings()) {
            pieChartData.add(new PieChart.Data(spending.getDescription() + ": $" + spending.getSpending().doubleValue(), spending.getSpending().doubleValue()));
        }
        wrapper.setAlignment(Pos.CENTER);
        cardPane.setAlignment(Pos.CENTER);
        pieChart.setData(pieChartData);
        pieChart.setTitle(budget.getName());
        pieChart.setLabelsVisible(false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BudgetPieChart)) {
            return false;
        }

        // state check
        BudgetPieChart pieChart = (BudgetPieChart) other;
        return budget.equals(pieChart.budget);
    }
}
