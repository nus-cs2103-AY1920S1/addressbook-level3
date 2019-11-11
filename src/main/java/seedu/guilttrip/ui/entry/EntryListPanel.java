package seedu.guilttrip.ui.entry;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.ui.UiPart;
import seedu.guilttrip.ui.expense.ExpenseCard;
import seedu.guilttrip.ui.income.IncomeCard;

/**
 * Panel containing the list of entries.
 */
public class EntryListPanel extends UiPart<Region> {
    private static final String FXML = "entry/EntryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EntryListPanel.class);

    @FXML
    private ListView<Entry> entryListView;

    public EntryListPanel(ObservableList<Entry> entryList) {
        super(FXML);
        entryListView.setItems(entryList);
        entryListView.setCellFactory(listView -> new EntryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class EntryListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {

                setGraphic(null);
                setText(null);
            } else {
                if (entry instanceof Expense) {
                    Expense expense = (Expense) entry;
                    setGraphic(new ExpenseCard(expense, getIndex() + 1).getRoot());
                } else if (entry instanceof Income) {
                    Income income = (Income) entry;
                    setGraphic(new IncomeCard(income, getIndex() + 1).getRoot());
                } else {
                    setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
