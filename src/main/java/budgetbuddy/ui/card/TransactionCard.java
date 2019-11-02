package budgetbuddy.ui.card;

import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Account}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionCard.fxml";

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label description;
    @FXML
    private Label direction;
    @FXML
    private Label categories;
    @FXML
    private Label date;


    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        amount.setText(transaction.getAmount().toString());
        description.setText(transaction.getDescription().toString());
        amount.setText(transaction.getAmount().toString());
        direction.setText(transaction.getDirection().toString());
        categories.setText(transaction.getCategories().toString());
        date.setText(transaction.getDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard card = (TransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}

