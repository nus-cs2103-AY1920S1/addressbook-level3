package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
//import seedu.address.model.person.BudgetStub;
import seedu.address.model.person.Entry;

/**
 * Side panel for budgets.
 */
public class BudgetPanel extends UiPart<Region> {
    private static final String FXML = "BudgetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BudgetPanel.class);

    @FXML
    private ListView<Entry> budgetListView;

    public BudgetPanel(ObservableList<Entry> budgetList) {
        super(FXML);
        budgetListView.setItems(budgetList);
        budgetListView.setCellFactory(listView -> new BudgetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class BudgetListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
            }
        }
    }
}
