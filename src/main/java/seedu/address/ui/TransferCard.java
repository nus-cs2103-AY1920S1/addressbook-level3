package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.ui.util.UiUtil;

/**
 * An UI component that displays a required transfer between two participants in an
 * {@code Activity}.
 */
public class TransferCard extends UiPart<Region> {
    private static final String FXML = "TransferCard.fxml";

    private final Person source;
    private final Person destination;
    private final double amount;

    @FXML
    private Label fromPerson;
    @FXML
    private Label toPerson;
    @FXML
    private Label transferAmt;

    public TransferCard(Person source, Person destination, double amount) {
        super(FXML);

        this.source = source;
        this.destination = destination;
        this.amount = amount;

        fromPerson.setText(source.getName().toString());
        toPerson.setText(destination.getName().toString());
        transferAmt.setText(UiUtil.formatAmount(amount));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransferCard)) {
            return false;
        }

        // state check
        TransferCard card = (TransferCard) other;
        return source.equals(card.source)
                && destination.equals(card.destination)
                && amount == card.amount;
    }
}
