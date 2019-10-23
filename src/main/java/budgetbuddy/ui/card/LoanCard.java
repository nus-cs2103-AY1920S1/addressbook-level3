package budgetbuddy.ui.card;

import budgetbuddy.model.loan.Loan;
import budgetbuddy.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information of a {@code Loan}.
 */
public class LoanCard extends UiPart<Region> {

    private static final String FXML = "LoanCard.fxml";

    public final Loan loan;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label amountDirectionPerson;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label status;

    public LoanCard(Loan loan, int displayedIndex) {
        super(FXML);
        this.loan = loan;
        id.setText(displayedIndex + ". ");
        amountDirectionPerson.setText(loan.getEssentialInfo());
        date.setText(loan.getDateString());
        description.setText(loan.getDescription().toString());
        status.setText(loan.getStatus().getStatusIcon());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanCard)) {
            return false;
        }

        LoanCard otherCard = (LoanCard) other;
        return id.getText().equals(otherCard.id.getText())
                && loan.equals(otherCard.loan);
    }
}
