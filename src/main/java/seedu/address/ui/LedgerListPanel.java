package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transaction.LedgerOperation;

import java.util.logging.Logger;

/**
 * Panel containing the list of ledgers set.
 */
public class LedgerListPanel extends UiPart<Region> {
    private static final String FXML = "LedgerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LedgerListPanel.class);

    @FXML
    private ListView<LedgerOperation> ledgerListView;

    public LedgerListPanel(ObservableList<LedgerOperation> ledgerOperations) {
        super(FXML);
        ledgerListView.setItems(ledgerOperations);
        ledgerListView.setCellFactory(listView -> new LedgerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Ledger} using a {@code LedgerCard}.
     */
    class LedgerListViewCell extends ListCell<LedgerOperation> {
        @Override
        protected void updateItem(LedgerOperation ledgerOperation, boolean empty) {
            super.updateItem(ledgerOperation, empty);

            if (empty || ledgerOperation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LedgerCard(ledgerOperation, getIndex() + 1).getRoot());
            }
        }
    }

}
