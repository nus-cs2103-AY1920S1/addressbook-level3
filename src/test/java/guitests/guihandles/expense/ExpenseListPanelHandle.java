package guitests.guihandles.expense;

import guitests.guihandles.ListHandle;
import javafx.scene.control.ListView;
import seedu.moolah.model.expense.Expense;

/**
 * Provides a handle for {@code ExpenseListPanel} containing the list of {@code Expense}.
 *
 */
public class ExpenseListPanelHandle extends ListHandle<ExpenseCardHandle, Expense> {

    public static final String EXPENSE_LIST_VIEW_ID = "#listView";
    public static final String EXPENSE_LIST_CARD_ID = "#expenseCardPane";

    public ExpenseListPanelHandle(ListView<Expense> listViewPanel) {
        super(listViewPanel, EXPENSE_LIST_CARD_ID, ExpenseCardHandle::new);
    }
}
