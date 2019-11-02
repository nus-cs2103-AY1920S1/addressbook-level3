package budgetbuddy.ui.panel;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.ui.card.TransactionCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Panel containing a list of transactions.
 */
public class TransactionListPanel extends DisplayPanel {
    private static final String FXML = "TransactionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private ListView<Transaction> transactionListView;

    public TransactionListPanel(ObservableList<Transaction> transactionList) {
        super(FXML);
        this.transactionListView.setItems(transactionList);
        this.transactionListView.setCellFactory(listView -> new TransactionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Account} using a {@code TransactionCard}.
     */
    class TransactionListViewCell extends ListCell<Transaction> {
        @Override
        protected void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);

            if (empty || transaction == null) {
                setGraphic(null);
                setText(null);
            } else {
                setMouseTransparent(true);
                setFocusTraversable(false);
                setGraphic(new TransactionCard(transaction, getIndex() + 1).getRoot());
            }
        }
    }
}
