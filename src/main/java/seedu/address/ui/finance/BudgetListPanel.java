package seedu.address.ui.finance;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.budget.BudgetData;

/**
 * Panel containing the list of budgets.
 */
public class BudgetListPanel extends UiPart<Region> {
    private static final String FXML = "BudgetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BudgetListPanel.class);

    @FXML
    private ListView<BudgetData> budgetListView;

    public BudgetListPanel(ObservableList<BudgetData> budgetList) {
        super(FXML);
        budgetListView.setItems(budgetList);
        budgetListView.setCellFactory(listView -> new BudgetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code LogEntry} using a {@code LogEntryCard}.
     */
    class BudgetListViewCell extends ListCell<BudgetData> {
        @Override
        protected void updateItem(BudgetData budgetData, boolean empty) {
            super.updateItem(budgetData, empty);

            if (empty || budgetData == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (budgetData.isActive()) {
                    setGraphic(new ActiveBudgetCard(budgetData).getRoot());
                } else if (budgetData.hasEnded()) {
                    setGraphic(new EndedBudgetCard(budgetData).getRoot());
                } else {
                    // Budgets yet to start
                    setGraphic(new InactiveBudgetCard(budgetData).getRoot());
                }
            }
        }
    }

}
