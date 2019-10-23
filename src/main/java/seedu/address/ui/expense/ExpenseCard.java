package seedu.address.ui.expense;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "ExpenseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Expense expense;

    @FXML
    private AnchorPane expenseCardPane;
    @FXML
    private Label description;
    @FXML
    private Label index;
    @FXML
    private Label price;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane categories;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        index.setText(Integer.toString(displayedIndex));
        description.setText(expense.getDescription().fullDescription);
        price.setText("$" + expense.getPrice().value);
        categories.getChildren().add(new Label(expense.getCategory().getCategoryName()));
        date.setText(expense.getTimestamp().timestamp.format(DateTimeFormatter.ISO_DATE));
        time.setText(null);
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
        return index.getText().equals(card.index.getText())
                && expense.equals(card.expense);
    }
}
