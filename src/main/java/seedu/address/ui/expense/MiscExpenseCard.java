package seedu.address.ui.expense;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * A component for displaying the details of a singular {@code MiscExpense}.
 */
public class MiscExpenseCard extends ExpenseCard {

    private static final String FXML = "expense/MiscExpenseCard.fxml";

    public MiscExpenseCard(Expense expense, Index displayedIndex, Model model) {
        super(FXML, expense, displayedIndex, model);
    }

}
