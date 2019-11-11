package seedu.guilttrip.ui.autoexpense;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.ui.UiPart;

/**
 * Side panel for AutoExpenses.
 */
public class AutoExpensesPanel extends UiPart<Region> {
    private static final String FXML = "autoexpense/AutoExpenseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AutoExpensesPanel.class);

    @FXML
    private ListView<AutoExpense> autoExpensesListView;

    public AutoExpensesPanel(ObservableList<AutoExpense> autoExpensesList) {
        super(FXML);
        autoExpensesListView.setItems(autoExpensesList);
        autoExpensesListView.setCellFactory(listView -> new AutoExpensesListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class AutoExpensesListViewCell extends ListCell<AutoExpense> {
        @Override
        protected void updateItem(AutoExpense entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AutoExpensesCard(entry, getIndex() + 1).getRoot());
            }
        }
    }
}
