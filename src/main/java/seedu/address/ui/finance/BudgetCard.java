package seedu.address.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.finance.budget.Budget;
import seedu.address.model.finance.budget.BudgetData;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public abstract class BudgetCard extends UiPart<Region> {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final BudgetData budgetData;

    @FXML
    protected HBox cardPane;
    @FXML
    protected Label budgetType;
    @FXML
    protected Label budgetTypeValue;
    @FXML
    protected ProgressBar progressBar;
    @FXML
    protected Label startDate;
    @FXML
    protected Label endDate;
    @FXML
    protected Label currAmt;
    @FXML
    protected Label limitAmt;
    @FXML
    protected Label balance;

    public BudgetCard(BudgetData budgetData, String fxml) {
        super(fxml);
        this.budgetData = budgetData;
        setText();
    }

    private void setText() {
        setBudgetType();
        setBudgetTypeValue();
        setStartDate();
        setEndDate();
        setBalance();
    }

    private void setBudgetType() {
        String type = budgetData.getBudgetType();
        switch (type) {
        case "all":
            budgetType.setText("All");
            break;
        case "place":
            budgetType.setText("Place");
            break;
        case "met":
            budgetType.setText("Transaction method");
            break;
        case "cat":
        default:
            budgetType.setText("Category");
        }
    }

    private void setBudgetTypeValue() {
        String value = budgetData.getBudgetTypeValue();
        if (value == null) {
            budgetTypeValue.setText("");
        } else {
            budgetTypeValue.setText(value);
        }
    }

    private void setStartDate() {
        String date = Budget.toStringDate(budgetData.getStartDate());
        startDate.setText("Start: " + date);
    }

    private void setEndDate() {
        String date = Budget.toStringDate(budgetData.getEndDate());
        endDate.setText("End: " + date);
    }

    abstract void setProgressBar();
    abstract void setAmounts();

    private void setBalance() {
        String amt = budgetData.getBalanceStr();
        String balanceText = "Balance: " + amt;
        balance.setText(balanceText);
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
        return budgetData.equals(card.budgetData);
    }
}
