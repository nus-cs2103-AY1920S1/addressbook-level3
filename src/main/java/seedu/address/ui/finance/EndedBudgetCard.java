package seedu.address.ui.finance;

import javafx.scene.control.Label;
import seedu.address.model.finance.budget.BudgetData;

/**
 * An UI component that displays information of a {@code Budget}
 * that is active.
 */
public class EndedBudgetCard extends BudgetCard {

    private static final String FXML = "EndedBudgetCard.fxml";

    @javafx.fxml.FXML
    protected Label exceeded;

    public EndedBudgetCard(BudgetData budgetData) {
        super(budgetData, FXML);
        setBudgetTypeValue();
        setProgressBar();
        setAmounts();
        setBalance();
        setExceeded();
    }

    /**
     * Set the style of label for budget value type.
     */
    protected void setBudgetTypeValue() {
        switch (budgetData.getBudgetType()) {
        case "met":
            budgetTypeValue.getStyleClass().add("budget-ended-met-label");
            break;
        case "cat":
            budgetTypeValue.getStyleClass().add("budget-ended-cat-label");
            break;
        case "all":
        case "place":
        default:
        }
    }

    protected void setProgressBar() {
        Double proportion = budgetData.getProportion();
        progressBar.setProgress(proportion);
        progressBar.setStyle("-fx-background-color: rgba(235,227,212,0.74) !important;");
        // Different colour based on proportion
        if (proportion >= 1) {
            progressBar.setStyle("-fx-accent: rgba(85,80,73,0.74)");
        } else if (proportion >= 0.8) {
            progressBar.setStyle("-fx-accent: rgba(131,125,115,0.74)");
        } else {
            progressBar.setStyle("-fx-accent: rgba(165,159,146,0.74)");
        }
    }

    protected void setAmounts() {
        currAmt.setText(budgetData.getCurrAmt().toString());
        limitAmt.setText("/ " + budgetData.getLimitAmt().toString());
        currAmt.setStyle("-fx-text-fill: rgba(125,119,107,0.74);");
        limitAmt.setStyle("-fx-text-fill: rgba(125,119,107,0.74);");
    }

    protected void setBalance() {
        balance.setStyle("-fx-text-fill: rgba(125,119,107,0.74);");
    }

    /**
     * Show exceeded status when over limit amount.
     */
    protected void setExceeded() {
        if (budgetData.hasExceeded()) {
            exceeded.setVisible(true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EndedBudgetCard)) {
            return false;
        }

        // state check
        EndedBudgetCard card = (EndedBudgetCard) other;
        return budgetData.equals(card.budgetData);
    }
}
