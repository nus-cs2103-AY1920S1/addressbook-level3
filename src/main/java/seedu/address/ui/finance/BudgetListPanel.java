package seedu.address.ui.finance;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.logentry.Budget;

/**
 * Panel containing the list of budgets.
 */
public class BudgetListPanel extends UiPart<Region> {
    private static final String FXML = "BudgetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BudgetListPanel.class);

    @FXML
    private ListView<Budget> budgetListView;

    public BudgetListPanel(ObservableList<Budget> budgetList) {
        super(FXML);
        budgetListView.setItems(budgetList);
        budgetListView.setCellFactory(listView -> new BudgetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code LogEntry} using a {@code LogEntryCard}.
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
