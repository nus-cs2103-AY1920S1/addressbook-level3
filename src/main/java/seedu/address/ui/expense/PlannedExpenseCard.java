package seedu.address.ui.expense;

import javafx.fxml.FXML;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expense.EnterDayOfExpenseCommand;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.ui.MainWindow;

/**
 * A component for displaying the details of a singular {@code PlannedExpense}.
 */
public class PlannedExpenseCard extends ExpenseCard {

    private static final String FXML = "expense/ExpenseCard.fxml";

    private Index displayedIndex;

    private MainWindow mainWindow;

    public PlannedExpenseCard(Expense expense, Index displayedIndex, Model model, MainWindow mainWindow) {
        super(FXML, expense, displayedIndex, model);
        this.mainWindow = mainWindow;
        this.displayedIndex = displayedIndex;

    }

    @FXML
    private void handleEnterEventPage() {
        mainWindow.executeGuiCommand(EnterDayOfExpenseCommand.COMMAND_WORD
                + " " + displayedIndex.getOneBased());
    }

}
