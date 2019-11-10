package seedu.address.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.finance.budget.BudgetData;

/**
 * An UI component that displays information of a {@code Budget}
 * that is active.
 */
public class ActiveBudgetCard extends BudgetCard {

    private static final String FXML = "ActiveBudgetCard.fxml";

    @FXML
    protected Label exceeded;

    public ActiveBudgetCard(BudgetData budgetData) {
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
        // Different colour based on proportion
        if (proportion >= 1) {
            progressBar.setStyle("-fx-accent: rgba(186, 37, 20, 0.74);");
        } else if (proportion >= 0.8) {
            progressBar.setStyle("-fx-accent: rgba(186, 108, 18, 0.74);");
        } else {
            progressBar.setStyle("-fx-accent: rgba(12, 164, 60, 0.74);");
        }
    }

    protected void setAmounts() {
        Double proportion = budgetData.getProportion();
        currAmt.setText(budgetData.getCurrAmt().toString());
        limitAmt.setText("/ " + budgetData.getLimitAmt().toString());
        // Different colour based on proportion
        if (proportion >= 1) {
            String cssRed = "-fx-text-fill: rgba(200,41,21,0.74);";
            currAmt.setStyle(cssRed);
            limitAmt.setStyle(cssRed);
        } else if (proportion >= 0.8) {
            String cssOrange = "-fx-text-fill: rgba(186, 108, 18, 0.74);";
            currAmt.setStyle(cssOrange);
            limitAmt.setStyle(cssOrange);
        } else {
            String cssGreen = "-fx-text-fill: rgba(12, 164, 60, 0.74)";
            currAmt.setStyle(cssGreen);
            limitAmt.setStyle(cssGreen);
        }
    }

    protected void setBalance() {
        Double proportion = budgetData.getProportion();
        String cssRed = "-fx-text-fill: rgba(200,41,21,0.74);";
        String cssOrange = "-fx-text-fill: rgba(186, 108, 18, 0.74);";
        String cssGreen = "-fx-text-fill: rgba(12, 164, 60, 0.74)";
        if (proportion >= 1) {
            balance.setStyle(cssRed);
        } else if (proportion >= 0.8) {
            balance.setStyle(cssOrange);
        } else {
            balance.setStyle(cssGreen);
        }
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
        if (!(other instanceof ActiveBudgetCard)) {
            return false;
        }

        // state check
        ActiveBudgetCard card = (ActiveBudgetCard) other;
        return budgetData.equals(card.budgetData);
    }
}
