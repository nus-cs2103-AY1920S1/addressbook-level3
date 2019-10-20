package seedu.address.ui.finance;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;


/**
 * Panel containing the list of log entries.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<LogEntry> personListView;

    public PersonListPanel(ObservableList<LogEntry> logEntryList) {
        super(FXML);
        personListView.setItems(logEntryList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<LogEntry> {
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
                    SpendLogEntry currLogEntry = (SpendLogEntry) logEntry;
                    setGraphic(new SpendLogEntryCard(currLogEntry, getIndex() + 1).getRoot());
                    break;
                default:
                    setGraphic(new LogEntryCard(logEntry, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
