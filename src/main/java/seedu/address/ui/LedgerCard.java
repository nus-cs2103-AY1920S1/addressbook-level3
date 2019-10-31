package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.LedgerOperation;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public class LedgerCard extends UiPart<Region> {
    private static final String FXML = "LedgerListCard.fxml";

    public final LedgerOperation ledgerOperation;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label balance;
    // @FXML
    // private FlowPane people;

    public LedgerCard(LedgerOperation ledgerOperation, int displayedIndex) {
        super(FXML);
        this.ledgerOperation = ledgerOperation;
        id.setText(displayedIndex + ". ");
        amount.setText(ledgerOperation.getAmount().toString());
        date.setText(ledgerOperation.getDate().toString());
        // TODO FIX
        balance.setText(ledgerOperation.getAmount().toString());
        // ledgerOperation.getPeopleInvolved().asUnmodifiableObservableList().stream()
        //     .sorted(Comparator.comparing(person -> person.getName().fullName))
        //     .forEach(person -> people.getChildren().add(new Label(person.getName().fullName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LedgerCard)) {
            return false;
        }

        // state check
        LedgerCard card = (LedgerCard) other;
        return id.getText().equals(card.id.getText())
            && ledgerOperation.equals(card.ledgerOperation);
    }
}
