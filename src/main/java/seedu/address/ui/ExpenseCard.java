package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Expense;

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
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExpenseList level 4</a>
     */

    public final Expense expense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label currency;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        id.setText(displayedIndex + ". ");
        name.setText(expense.getName().fullName);
        if (expense.isForeign()) {
            amount.setText(expense.getConvertedAmount().value + " SGD");
            currency.setText("(" + expense.getCurrency().toString() + ")");
        } else {
            amount.setText(expense.getAmount().value);
            currency.setText(expense.getCurrency().name);
        }

        date.setText(expense.getDate().value);
        if (expense.getTag().tagName == "") {
            return;
        }
        tags.getChildren().add(new Label(expense.getTag().tagName));
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
