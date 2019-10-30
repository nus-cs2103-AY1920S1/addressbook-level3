package seedu.moneygowhere.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * The UI component that is responsible for Showing the current monthly budget and total spending.
 */
public class BudgetPanel extends UiPart<Region> {

    private static final String FXML = "BudgetPanel.fxml";

    @FXML
    private StackPane panePlaceHolder;

    @FXML
    private Label budgetAmount;

    @FXML
    private ProgressBar budgetProgressBar;

    @FXML
    private Label remainingBudget;

    private double amount;
    private double sum;

    public BudgetPanel(double amount, double sum) {
        super(FXML);

        this.amount = amount;
        this.sum = sum;

        budgetAmount.setFont(new Font("Arial", 36));

        remainingBudget.setFont(new Font("Arial", 24));

        update();
    }

    /**
     * sets the values of Labels and progress bar.
     */
    private void update() {
        budgetAmount.setText(getBudgetText());
        budgetProgressBar.setProgress(1 - sum / amount);
        remainingBudget.setText(getRemainingBudgetText());
    }

    /**
     * Updates the values of amount and sum.
     * Only updates if value changes.
     * @param amount the new amount value
     * @param sum the new sum value
     */
    public void update(double amount, double sum) {
        if (this.amount != amount || this.sum != sum) {
            this.amount = amount;
            this.sum = sum;
            update();
        }
    }

    private String getBudgetText() {
        return "Balance: $" + String.format("%.2f", amount);
    }

    private String getRemainingBudgetText() {
        return "Remaining: $" + String.format("%.2f", amount - sum);
    }
}
