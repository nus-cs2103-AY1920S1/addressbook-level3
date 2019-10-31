package seedu.address.ui.finance;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Panel containing the list of log entries.
 */
public class LogEntryListPanel extends UiPart<Region> {
    private static final String FXML = "LogEntryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LogEntryListPanel.class);

    @FXML
    private ListView<LogEntry> logEntryListView;

    public LogEntryListPanel(ObservableList<LogEntry> logEntryList) {
        super(FXML);
        logEntryListView.setItems(logEntryList);
        logEntryListView.setCellFactory(listView -> new LogEntryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code LogEntry} using a {@code LogEntryCard}.
     */
    class LogEntryListViewCell extends ListCell<LogEntry> {
        @Override
        protected void updateItem(LogEntry logEntry, boolean empty) {
            super.updateItem(logEntry, empty);

            if (empty || logEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                String logEntryType = logEntry.getLogEntryType();
                switch (logEntryType) {

                case SpendLogEntry.LOG_ENTRY_TYPE:
                    SpendLogEntry spendLogEntry = (SpendLogEntry) logEntry;
                    setGraphic(new SpendLogEntryCard(spendLogEntry, getIndex() + 1).getRoot());
                    break;

                case IncomeLogEntry.LOG_ENTRY_TYPE:
                    IncomeLogEntry incomeLogEntry = (IncomeLogEntry) logEntry;
                    setGraphic(new IncomeLogEntryCard(incomeLogEntry, getIndex() + 1).getRoot());
                    break;

                case BorrowLogEntry.LOG_ENTRY_TYPE:
                    BorrowLogEntry borrowLogEntry = (BorrowLogEntry) logEntry;
                    setGraphic(new BorrowLogEntryCard(borrowLogEntry, getIndex() + 1).getRoot());
                    break;

                default:
                    LendLogEntry lendLogEntry = (LendLogEntry) logEntry;
                    setGraphic(new LendLogEntryCard(lendLogEntry, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
