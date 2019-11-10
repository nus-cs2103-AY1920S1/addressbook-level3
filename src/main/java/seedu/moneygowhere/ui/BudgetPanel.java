package seedu.moneygowhere.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.moneygowhere.commons.util.MoneyUtil;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;

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

    private Currency currencyInUse;

    public BudgetPanel(Budget budget, Currency currencyInUse) {
        super(FXML);

        this.amount = budget.getAmount();
        this.sum = budget.getSum();
        this.currencyInUse = currencyInUse;

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
    public void update(Budget budget, Currency currencyInUse) {
        if (this.amount != budget.getAmount() || this.sum != budget.getSum()
            || !this.currencyInUse.equals(currencyInUse)) {
            this.amount = budget.getAmount();
            this.sum = budget.getSum();
            this.currencyInUse = currencyInUse;
            update();
        }
    }

    private String getRemainingBudgetText() {
        double percentDiff = (amount - sum) / amount;
        double remainingAmount = Math.abs(amount - sum) * currencyInUse.rate;

        String defaultOutput = String.format("%s %s", currencyInUse.symbol, MoneyUtil.format(remainingAmount));
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
            titleText.setTextFill(Color.web("#23B35B"));
        }
    }

    private String getBudgetAmount() {
        return String.format("This month's budget: %s %s", currencyInUse.symbol,
                MoneyUtil.format(amount * currencyInUse.rate));
    }
}
