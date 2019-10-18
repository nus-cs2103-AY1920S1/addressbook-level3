package seedu.address.financialtracker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "ExpenseCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Expense expense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label desc;
    @FXML
    private Label country;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        id.setText(displayedIndex + ". ");
        amount.setText("Amount: " + expense.getAmount().value);
        desc.setText("Details: " + expense.getDesc().value);
        country.setText(expense.getCountry().value);
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
        ExpenseCard card = (ExpenseCard) other;
        return id.getText().equals(card.id.getText())
                && expense.equals(card.expense);
    }
}
