package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";

    public final BankAccountOperation transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

    public TransactionCard(BankAccountOperation transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        amount.setText(transaction.getAmount().toString());
        date.setText(transaction.getDate().toString());
        transaction.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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
