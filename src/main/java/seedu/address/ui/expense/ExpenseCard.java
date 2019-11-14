package seedu.address.ui.expense;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * A component for displaying the details of a singular {@code Expense}.
 */
public abstract class ExpenseCard extends UiPart<HBox> {

    private static final String FXML = "expense/ExpenseCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private VBox propertiesContainer;

    private Expense expense;
    private Index displayedIndex;
    private Model model;

    public ExpenseCard(String fxmlFileName, Expense expense, Index displayedIndex, Model model) {
        super(fxmlFileName);
        this.expense = expense;
        this.displayedIndex = displayedIndex;
        this.model = model;
        fillExpenseCardLabels();
    }

    /**
     * Fills the labels of this expense card.
     */
    private void fillExpenseCardLabels() {
        CustomisedCurrency currency = model.getTravelPal().getCurrencies().get(0);
        idLabel.setText(displayedIndex.getOneBased() + ".");
        nameLabel.setText(expense.getName().toString());
        budgetLabel.setText(expense.getBudget().getValueStringInCurrency(currency));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseCard)) {
            return false;
        }

        // state check
        ExpenseCard otherCard = (ExpenseCard) other;
        return expense.equals(otherCard.expense)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }

    public Expense getExpense() {
        return expense;
    }

}
