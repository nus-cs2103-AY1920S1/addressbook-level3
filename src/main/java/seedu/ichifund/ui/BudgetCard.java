package seedu.ichifund.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.budget.ComputedBudget;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {

    private static final String FXML = "BudgetListCard.fxml";

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
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label criterion;
    @FXML
    private ProgressBar budgetProgress;

    public BudgetCard(Budget budget, int displayedIndex) {
        super(FXML);
        this.budget = budget;
        id.setText(displayedIndex + ". ");
        description.setText(budget.getDescription().toString());
        criterion.setText(budget.toCriterionString());

        if (budget instanceof ComputedBudget) {
            Amount spending = ((ComputedBudget) budget).getSpending();
            Amount limit = budget.getAmount();
            double ratio = (double) spending.valueInCents / limit.valueInCents;
            amount.setText("$" + spending.toString() + " / $" + limit.toString());
            budgetProgress.setProgress(ratio);
            budgetProgress.setStyle("-fx-accent: " + getBarColor(ratio));
        } else {
            amount.setText("$" + budget.getAmount().toString());
            budgetProgress.setProgress(0.0);
        }
    }

    private String getBarColor(double ratio) {
        return ratio > 0.8 ? "#ff7675" : "#00b894";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BudgetCard)) {
            return false;
        }

        // state check
        BudgetCard card = (BudgetCard) other;
        return id.getText().equals(card.id.getText())
                && budget.equals(card.budget);
    }
}
