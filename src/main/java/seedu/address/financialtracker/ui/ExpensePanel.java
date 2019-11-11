package seedu.address.financialtracker.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.financialtracker.logic.FinancialTrackerLogic;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of expenses.
 */
public class ExpensePanel extends UiPart<Region> {
    private static final String FXML = "ExpensePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpensePanel.class);

    @FXML
    private ListView<Expense> expenseListView;

    private final FinancialTrackerLogic financialTrackerLogic;

    public ExpensePanel(FinancialTrackerLogic financialTrackerLogic) {
        super(FXML);
        this.financialTrackerLogic = financialTrackerLogic;
        expenseListView.setItems(financialTrackerLogic.getExpenseList());
        expenseListView.setCellFactory(listView -> new ExpenseListViewCell());
    }


    /**
     * Performs ListView update whenever the {@code CountriesDropdown } changed country.
     */
    public void update() {
        expenseListView.setItems(financialTrackerLogic.getExpenseList());
        expenseListView.setCellFactory(listView -> new ExpenseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ExpenseListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenseCard(expense, getIndex() + 1).getRoot());
            }
        }
    }
}
