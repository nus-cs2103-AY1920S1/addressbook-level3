package budgetbuddy.ui.card;

import java.util.Comparator;

import budgetbuddy.model.loan.Debtor;
import budgetbuddy.ui.UiPart;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information of a {@code Debtor}.
 */
public class LoanSplitCard extends UiPart<Region> {

    private static final String FXML = "LoanSplitCard.fxml";

    public final Debtor debtor;

    @FXML
    private HBox cardPane;

    @FXML
    private Label person;

    @FXML
    private FlowPane debts;

    public LoanSplitCard(Debtor debtor) {
        super(FXML);
        this.debtor = debtor;
        this.person.setText(debtor.getDebtor().getName().toString());
        this.debts.setOrientation(Orientation.VERTICAL);
        debtor.getCreditors().entrySet().stream()
                .sorted(Comparator.comparingLong(pa -> pa.getValue().toLong()))
                .forEach(pa -> debts.getChildren().add(
                        new Label(String.format("owes %s to %s", pa.getValue(), pa.getKey()))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanSplitCard)) {
            return false;
        }

        LoanSplitCard otherCard = (LoanSplitCard) other;
        return debtor.equals(otherCard.debtor);
    }
}
