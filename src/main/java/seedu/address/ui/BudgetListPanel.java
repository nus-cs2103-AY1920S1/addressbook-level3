package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transaction.Budget;

/**
 * Panel containing the list of budgets set.
 */
public class BudgetListPanel extends UiPart<Region> {
    private static final String FXML = "BudgetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private ListView<Budget> budgetListView;

    public BudgetListPanel(ObservableList<Budget> budgets) {
        super(FXML);
        budgetListView.setItems(budgets);
        budgetListView.setCellFactory(listView -> new BudgetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Budget} using a {@code BudgetCard}.
     */
    class BudgetListViewCell extends ListCell<Budget> {
        @Override
        protected void updateItem(Budget budget, boolean empty) {
            super.updateItem(budget, empty);

            if (empty || budget == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BudgetCard(budget, getIndex() + 1).getRoot());
            }
        }
    }

}
