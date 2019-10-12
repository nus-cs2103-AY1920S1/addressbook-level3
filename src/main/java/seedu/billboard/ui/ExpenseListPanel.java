package seedu.billboard.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.model.expense.Expense;

/**
 * Panel containing the list of expenses.
 */
public class ExpenseListPanel extends UiPart<Region> {
    private static final String FXML = "ExpenseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpenseListPanel.class);

    @FXML
    private ListView<Expense> expenseListView;

    public ExpenseListPanel(ObservableList<Expense> expenseList) {
        super(FXML);
        expenseListView.setItems(expenseList);
        expenseListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Changes the list of expenses being displayed on the panel.
     *
     * @param expenseList New list of expenses to be displayed on the panel.
     */
    public void setExpenseListView(ObservableList<Expense> expenseList) {
        expenseListView.setItems(expenseList);
        expenseListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expense} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(expense, getIndex() + 1).getRoot());
            }
        }
    }

}
