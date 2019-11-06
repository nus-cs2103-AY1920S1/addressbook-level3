package seedu.address.ui.expenditure;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;

/**
 * TODO: Implement display for inventory and booking labels.
 */
public class MiscExpenditureCard extends ExpenditureCard {
    private static final String FXML = "expenses/MiscExpenditureCard.fxml";

    public MiscExpenditureCard(Expenditure expenditure, Index displayedIndex, Model model) {
        super(FXML, expenditure, displayedIndex, model);
    }

}
