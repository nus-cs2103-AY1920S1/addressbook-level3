package seedu.moneygowhere.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import seedu.moneygowhere.model.budget.Budget;

/**
 * The UI component that is responsible for Showing the current monthly budget and total spending.
 */
public class BudgetPanel extends UiPart<Region> {

    private static final String FXML = "BudgetPanel.fxml";

    @FXML
    private StackPane panePlaceHolder;

    @FXML
    private Label remainingBudget;

    @FXML
    private Label budgetRemark;

    @FXML
    private Label budgetAmount;

    private double amount;
    private double sum;

    public BudgetPanel(Budget budget) {
        super(FXML);

        this.amount = budget.getValue();
        this.sum = budget.getSum();

        update();
    }

    /**
     * sets the values of Labels and progress bar.
     */
    private void update() {
        remainingBudget.setText(getRemainingBudgetText());
        budgetRemark.setText(getBudgetRemark());
        budgetAmount.setText(getBudgetAmount());
    }

    /**
     * Updates the values of amount and sum.
     * Based on the new {@code Budget}
     * @param budget the budget to get data to update.
     */
    public void update(Budget budget) {
        if (this.amount != budget.getValue() || this.sum != budget.getSum()) {
            this.amount = budget.getValue();
            this.sum = budget.getSum();
            update();
        }
    }

    private String getRemainingBudgetText() {
        if (amount - sum < 0) {
            remainingBudget.setTextFill(Color.web("#FF0000"));
            return "$" + String.format("%.2f", Math.abs(amount - sum)) + " exceeded";
        } else {
            remainingBudget.setTextFill(Color.web("#000000"));
            return "$" + String.format("%.02f", amount - sum) + " left to spend";
        }
    }

    private String getBudgetRemark() {
        if (amount - sum < 0) {
            budgetRemark.setTextFill(Color.web("#FF0000"));
            return "Your money go where ahh?";
        } else {
            budgetRemark.setTextFill(Color.web("#00FF00"));
            return "You are spending within your budget. Great Job!";
        }
    }

    private String getBudgetAmount() {
        return "Your budget for this month is $" + String.format("%.02f", amount);
    }
}
