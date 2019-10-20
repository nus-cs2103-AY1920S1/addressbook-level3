package seedu.address.ui.expenditure;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.expenditure.DayNumber;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.UiPart;


/**
 * TODO: Implement display for inventory and booking labels.
 */
public class ExpenditureCard extends UiPart<HBox> {
    private static final String FXML = "expenses/ExpenditureCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label budgetLabel;

    @FXML
    private VBox propertiesContainer;

    private Expenditure expenditure;
    private Index displayedIndex;

    public ExpenditureCard(Expenditure expenditure, Index displayedIndex) {
        super(FXML);
        this.expenditure = expenditure;
        this.displayedIndex = displayedIndex;
        fillExpenditureCardLabels();
    }

    /**
     * Fills the labels of this expenditure card.
     */
    private void fillExpenditureCardLabels() {
        idLabel.setText(displayedIndex.getOneBased() + ".");
        nameLabel.setText(expenditure.getName().toString());
        budgetLabel.setText(" $" + expenditure.getBudget().toString());
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenditureCard)) {
            return false;
        }

        // state check
        ExpenditureCard otherCard = (ExpenditureCard) other;
        return expenditure.equals(otherCard.expenditure)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }

    public Expenditure getExpenditure() {
        return expenditure;
    }
}
