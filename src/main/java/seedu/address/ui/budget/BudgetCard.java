package seedu.address.ui.budget;

import java.time.format.DateTimeFormatter;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {

    private static final String FXML = "BudgetCard.fxml";
    private static final String CURRENCY_SYMBOL = "$";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exceptions will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private AnchorPane budgetCardPane;
    @FXML
    private Label budgetName;
    @FXML
    private Label budgetStart;
    @FXML
    private Label budgetEnd;
    @FXML
    private Label budgetTotalAmount;
    @FXML
    private Label budgetAllocatedAmount;
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
        budget.getExpenses()
                .stream()
                .mapToDouble(expense -> expense.getPrice().getAsDouble())
                .reduce((v, v1) -> v + v1)
                .ifPresentOrElse(this::updateBudgetCardTotalAmounts, () -> updateBudgetCardTotalAmounts(0));
        budgetAllocatedAmount.setText(String.format("%s%f", CURRENCY_SYMBOL, budget.getAmount().getAsDouble()));
    }


    private void updateBudgetCardTotalAmounts(double totalAmount) {
        budgetTotalAmount.setText(String.format("%s%f", CURRENCY_SYMBOL, totalAmount));
        budgetProgressBar.setProgress(totalAmount / budget.getAmount().getAsDouble());
    }

    private void updateBudgetCardPeriod() {
        budgetStart.setText(budget.getStartDate().timestamp.format(DateTimeFormatter.BASIC_ISO_DATE));
        budgetEnd.setText(budget.getEndDate().timestamp.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    private void updateBudgetCardDescription() {
        budgetName.setText(budget.getDescription().fullDescription);
    }

    /**
     * Sets the progress bar colour to the colour defined in the stylesheet {@code -progress-bar-colour}.
     * If the budget total amount exceeds the budget allocated amount, the progress bar colour is set to
     * {@code -progress-bar-overbudget}, else it is set to {@code -progress-bar-inbudget}, as defined in the
     * stylesheet.
     */
    private void updateBudgetCardProgressBarColour() {
        if (budget.isExceeded()) {
            budgetProgressBar.setStyle("-progress-bar-colour: -progress-bar-overbudget;");
        } else {
            budgetProgressBar.setStyle("-progress-bar-colour: -progress-bar-inbudget;");
        }
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
