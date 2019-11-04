package seedu.address.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.finance.logentry.Budget;

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

    public final Budget budget;

    @FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label budgetType;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label isActive;

    public BudgetCard(Budget budget, int displayedIndex) {
        super(FXML);
        this.budget = budget;
        id.setText(displayedIndex + ". ");
        amount.setText(this.budget.getAmount().toString());
        budgetType.setText(this.budget.getBudgetType());
        startDate.setText(Budget.toStringDate(this.budget.getStartDate()));
        endDate.setText(Budget.toStringDate(this.budget.getEndDate()));
        amount.setText(Boolean.toString(this.budget.isActive()));
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
