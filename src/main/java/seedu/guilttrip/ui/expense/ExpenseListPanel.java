package seedu.guilttrip.ui.expense;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.ui.UiPart;

/**
 * Panel containing the list of expenses.
 */
public class ExpenseListPanel extends UiPart<Region> {
    private static final String FXML = "expense/ExpenseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpenseListPanel.class);

    @FXML
    private ListView<Expense> expenseListView;

    public ExpenseListPanel(ObservableList<Expense> expenseList) {
        super(FXML);
        expenseListView.setItems(expenseList);
        expenseListView.setCellFactory(listView -> new ExpenseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expense} using a {@code ExpenseCard}.
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
