package seedu.address.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.logentry.Budget;
import seedu.address.model.finance.logentry.BudgetData;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {

    private static final String FXML = "BudgetCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final BudgetData budgetData;

    @FXML
    private HBox cardPane;
    @FXML
    private Label budgetType;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label isActive;
    @FXML
    private Label currAmt;
    @FXML
    private Label limitAmt;

    public BudgetCard(BudgetData budgetData) {
        super(FXML);
        this.budgetData = budgetData;
        budgetType.setText(this.budgetData.getBudgetType());
        startDate.setText(Budget.toStringDate(this.budgetData.getStartDate()));
        endDate.setText(Budget.toStringDate(this.budgetData.getEndDate()));
        isActive.setText(Boolean.toString(this.budgetData.isActive()));
        Double currAmount = this.budgetData.getCurrAmt();
        Double limitAmount = this.budgetData.getLimitAmt();
        currAmt.setText(currAmount.toString());
        limitAmt.setText(limitAmount.toString());
        progressBar.setProgress(currAmount / limitAmount);
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
