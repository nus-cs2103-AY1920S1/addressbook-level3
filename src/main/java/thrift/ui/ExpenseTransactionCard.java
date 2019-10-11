package thrift.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import thrift.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseTransactionCard extends UiPart<Region> {

    private static final String FXML = "ExpenseTransactionCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Thrift level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label expenseDescription;
    @FXML
    private Label id;
    @FXML
    private Label expenseValue;
    @FXML
    private Label expenseDate;
    @FXML
    private Label expenseRemark;
    @FXML
    private FlowPane tags;

    public ExpenseTransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        expenseDescription.setText(transaction.getDescription().toString());
        expenseValue.setText("-$" + transaction.getValue().toString());
        expenseDate.setText(transaction.getDate().toString());
        expenseRemark.setText(transaction.getRemark().toString());
        transaction.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        expenseDescription.setWrapText(true);
        expenseValue.setWrapText(true);
        expenseRemark.setWrapText(true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseTransactionCard)) {
            return false;
        }

        // state check
        ExpenseTransactionCard card = (ExpenseTransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}
