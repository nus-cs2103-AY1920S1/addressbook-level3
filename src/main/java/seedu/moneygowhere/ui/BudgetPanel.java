package seedu.moneygowhere.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.moneygowhere.model.budget.Budget;

/**
 * The UI component that is responsible for Showing the current monthly budget and total spending.
 */
public class BudgetPanel extends UiPart<Region> {

    private static final String FXML = "BudgetPanel.fxml";

    @FXML
    private HBox panePlaceHolder;

    @FXML
    private Label remainingBudget;

    @FXML
    private Label titleText;

    @FXML
    private Label budgetAmount;

    private double amount;
    private double sum;

    public BudgetPanel(Budget budget) {
        super(FXML);

        this.amount = budget.getAmount();
        this.sum = budget.getSum();

        update();
    }

    /**
     * sets the values of Labels and progress bar.
     */
    private void update() {
        remainingBudget.setText(getRemainingBudgetText());
        budgetAmount.setText(getBudgetAmount());

        updateTitleText();
    }

    /**
     * Updates the values of amount and sum.
     * Based on the new {@code Budget}
     * @param budget the budget to get data to update.
     */
    public void update(Budget budget) {
        if (this.amount != budget.getAmount() || this.sum != budget.getSum()) {
            this.amount = budget.getAmount();
            this.sum = budget.getSum();
            update();
        }
    }

    private String getRemainingBudgetText() {
        double percentDiff = (amount - sum) / amount;
        double remainingAmount = Math.abs(amount - sum);

        String defaultOutput = String.format("$%.2f", remainingAmount);
        if (percentDiff < 0) {
            remainingBudget.setTextFill(Color.web("#FF0000"));
            return "-" + defaultOutput;
        }

        if (percentDiff == 0) {
            remainingBudget.setTextFill(Color.web("#8B2300"));
        } else if (percentDiff <= 0.5) {
            remainingBudget.setTextFill(Color.web("#FF8C00"));
        } else {
            remainingBudget.setTextFill(Color.web("#00698B"));
        }

        return defaultOutput;
    }

    /**
     * Sets the colour of the titleText to red.
     * If user has exceeded the budget.
     */
    private void updateTitleText() {
        if (amount - sum < 0) {
            titleText.setTextFill(Color.web("#FF0000"));
        } else {
            titleText.setTextFill(Color.web("#00FF00"));
        }
    }

    private String getBudgetAmount() {
        return "This month's budget: $" + String.format("%.02f", amount);
    }
}
