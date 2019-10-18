package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.earnings.Earnings;

/**
 * A UI component that displays information of a {@code Earnings}.
 */
public class EarningsCard extends UiPart<Region> {

    private static final String FXML = "EarningsListCard.fxml";

    public final Earnings earnings;

    @FXML
    private HBox cardPane;
    @FXML
    private Label classId;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label amount;

    public EarningsCard(Earnings earnings, int displayedIndex) {
        super(FXML);
        this.earnings = earnings;
        id.setText(displayedIndex + ". ");
        classId.setText("ClassId: " + earnings.getClassId().value);
        date.setText("Date: " + earnings.getDate().dateNum);
        amount.setText("Amount: " + earnings.getAmount().amount);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EarningsCard)) {
            return false;
        }

        // state check
        EarningsCard card = (EarningsCard) other;
        return id.getText().equals(card.id.getText())
                && earnings.equals(card.earnings);
    }
}
