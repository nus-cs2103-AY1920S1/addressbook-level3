package seedu.moolah.ui.budget;

import java.time.format.DateTimeFormatter;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.ui.UiPart;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {

    private static final String FXML = "BudgetCard.fxml";
    private static final String CURRENCY_SYMBOL = "$";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exceptions will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MooLah level 4</a>
     */

    @FXML
    private AnchorPane budgetCardPane;
    @FXML
    private Label budgetName;
    @FXML
    private Label budgetStart;
    @FXML
    private Label separator;
    @FXML
    private Label budgetEnd;
    @FXML
    private Label budgetTotalAmount;
    @FXML
    private Label divider;
    @FXML
    private Label budgetAllocatedAmount;
    @FXML
    private Label proportionUsed;
    @FXML
    private ProgressBar budgetProgressBar;

    private Budget budget;

    public BudgetCard(Budget budget) {
        super(FXML);

        this.budget = budget;

        // budget name
        updateBudgetCardDescription();
        // budget period
        updateBudgetCardPeriod();
        // budget total over allocated
        updateBudgetCardProgressBarText();
        // budget proportion used
        updateBudgetProportionUsed();
        // progress bar colour
        updateBudgetCardProgressBarColour();

        budget.getExpenses().addListener(new ListChangeListener<Expense>() {
            @Override
            public void onChanged(Change<? extends Expense> change) {
                change.next();
                if (change.wasAdded() || change.wasRemoved() || change.wasReplaced() || change.wasUpdated()) {
                    // update total
                    updateBudgetCardProgressBarText();
                    updateBudgetCardProgressBarColour();
                }
            }
        });
    }

    /**
     * Updates the text displayed on the budget progress bar.
     */
    private void updateBudgetCardProgressBarText() {
        if (!budget.isDefaultBudget()) {
            updateBudgetCardTotalAmount(budget.calculateExpenseSum());
            budgetAllocatedAmount.setText(String.format("%s%.2f", CURRENCY_SYMBOL, budget.getAmount().getAsDouble()));
            updateBudgetProportionUsed();
        }
    }


    private void updateBudgetCardTotalAmount(double totalAmount) {
        budgetTotalAmount.setText(String.format("%s%.2f", CURRENCY_SYMBOL, totalAmount));
        divider.setText("/");
        budgetProgressBar.setProgress(totalAmount / budget.getAmount().getAsDouble());
    }

    /**
     * Updates period shown in budget card.
     */
    private void updateBudgetCardPeriod() {
        if (!budget.isDefaultBudget()) {
            budgetStart.setText(budget.getWindowStartDate().fullTimestamp.format(formatter));
            separator.setText(" to ");
            budgetEnd.setText(budget.getWindowEndDate().fullTimestamp.format(formatter));
        }
    }

    private void updateBudgetCardDescription() {
        budgetName.setText(budget.getDescription().fullDescription);
    }

    private void updateBudgetProportionUsed() {
        if (!budget.isDefaultBudget()) {
            proportionUsed.setText("[" + budget.calculateProportionUsed().toString() + "]");
        }
    }

    /**
     * Sets the progress bar colour to the colour defined in the stylesheet {@code -progress-bar-colour}.
     * If the budget total amount exceeds the budget allocated amount, the progress bar colour is set to
     * {@code -progress-bar-overbudget}.
     * If the budget total amount is near the budget allocated amount, the progress bar colour is set to
     * {@code -progress-bar-nearbudget}.
     * If the budget total amount is half the budget allocated amount, the progress bar colour is set to
     * {@code -progress-bar-halfbudget}.
     * Else the progress bar colour is set to {@code -progress-bar-inbudget}.
     */
    private void updateBudgetCardProgressBarColour() {
        if (budget.isDefaultBudget()) {
            return; // default budget's progress bar is not shown
        }
        if (budget.isExceeded()) {
            budgetProgressBar.setStyle("-progress-bar-colour: -progress-bar-overbudget;");
        } else if (budget.isNear()) {
            budgetProgressBar.setStyle("-progress-bar-colour: -progress-bar-nearbudget;");
        } else if (budget.isHalf()) {
            budgetProgressBar.setStyle("-progress-bar-colour: -progress-bar-halfbudget;");
        } else {
            budgetProgressBar.setStyle("-progress-bar-colour: -progress-bar-inbudget;");
        }
    }

    public void setBorder(Border border) {
        budgetCardPane.setBorder(border);
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
        return budgetName.equals(card.budgetName)
                && budgetStart.equals(card.budgetStart)
                && budgetEnd.equals(card.budgetEnd)
                && budget.equals(card.budget);
    }
}
