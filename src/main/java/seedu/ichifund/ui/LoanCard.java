package seedu.ichifund.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import seedu.ichifund.model.loan.Loan;

/**
 * An UI component that displays information of a {@code loan}.
 */
public class LoanCard extends UiPart<Region> {
    private static final String FXML = "LoanListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Loan loan;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label date;
    @FXML
    private Label name;


    public LoanCard(Loan loan, int displayedIndex) {
        super(FXML);
        this.loan = loan;
        id.setText(displayedIndex + ". ");
        description.setText(loan.getDescription().toString());
        amount.setText("$" + loan.getAmount().toString());
        date.setText(loan.getEndDate().toFullString());
        name.setText(loan.getName().toString().toUpperCase());
        amount.setTextFill(Paint.valueOf("#4caf50"));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LoanCard)) {
            return false;
        }

        // state check
        LoanCard card = (LoanCard) other;
        return id.getText().equals(card.id.getText())
                && loan.equals(card.loan);
    }
}
