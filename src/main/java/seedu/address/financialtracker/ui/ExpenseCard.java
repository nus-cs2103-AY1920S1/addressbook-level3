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

    public final Expense expense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label amount;
    @FXML
    private Label desc;
    @FXML
    private Label country;
    @FXML
    private Label type;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        id.setText(displayedIndex + ". ");
        country.setText(expense.getCountry().value);
        date.setText("Date: " + expense.getDate().value);
        time.setText("Time: " + expense.getTime().value);
        amount.setText("Amount: " + expense.getAmount().value);
        desc.setText("Details: " + expense.getDescription().value);
        type.setText("Type of expenditure: " + expense.getType().value);
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
