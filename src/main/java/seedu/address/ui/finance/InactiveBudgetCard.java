package seedu.address.ui.finance;

import seedu.address.model.finance.budget.BudgetData;

/**
 * An UI component that displays information of a {@code Budget}
 * that is inactive (not yet started).
 */
public class InactiveBudgetCard extends BudgetCard {

    private static final String FXML = "InactiveBudgetCard.fxml";

    public InactiveBudgetCard(BudgetData budgetData) {
        super(budgetData, FXML);
        setBudgetTypeValue();
        setProgressBar();
        setAmounts();
    }

    /**
     * Set the style of label for budget value type.
     */
    protected void setBudgetTypeValue() {
        switch (budgetData.getBudgetType()) {
        case "met":
            budgetTypeValue.getStyleClass().add("transaction_method_label");
            break;
        case "cat":
            budgetTypeValue.getStyleClass().add("budget-cat-label");
            break;
        case "all":
        case "place":
        default:
        }
    }

    protected void setProgressBar() {
        Double proportion = budgetData.getProportion();
        progressBar.setProgress(proportion);
    }

    protected void setAmounts() {
        currAmt.setText(budgetData.getCurrAmt().toString());
        limitAmt.setText("/ " + budgetData.getLimitAmt().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InactiveBudgetCard)) {
            return false;
        }

        // state check
        InactiveBudgetCard card = (InactiveBudgetCard) other;
        return budgetData.equals(card.budgetData);
    }
}
