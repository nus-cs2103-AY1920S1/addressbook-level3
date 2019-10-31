package seedu.moneygowhere.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private ProgressBar budgetProgressBar;

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
//        budgetProgressBar.setProgress(1 - sum / amount);
        budgetAmount.setText(getBudgetRemark());
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
        return "$" + String.format("%.2f", amount - sum) + " left to spend";
    }

    private String getBudgetRemark() {
        if (sum / amount < 0.2) {
            return "You are spending within your budget. Great Job!";
        } else {
            return "Your money go where ahh?";
        }
    }
}
