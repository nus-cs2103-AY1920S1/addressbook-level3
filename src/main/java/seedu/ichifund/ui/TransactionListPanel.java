package seedu.ichifund.ui;

import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Panel containing the list of transactions.
 */
public class TransactionListPanel extends UiPart<Region> {
    private static final String FXML = "TransactionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private ListView<Transaction> transactionListView;

    @FXML
    private Label transactionContextView;

    public TransactionListPanel(ObservableList<Transaction> transactionList,
                                ObservableValue<TransactionContext> transactionContext) {
        super(FXML);
        transactionListView.setItems(transactionList);
        transactionListView.setCellFactory(listView -> new TransactionListViewCell());
        transactionContextView.setText(transactionContext.getValue().toString());
        transactionContext.addListener(new TransactionContextInvalidationListener(transactionContextView));
    }

    /**
     * Listener for invalidation of the transition context.
     */
    class TransactionContextInvalidationListener implements InvalidationListener {
        private Label label;

        public TransactionContextInvalidationListener(Label label) {
            this.label = label;
        }

        @Override
        public void invalidated(Observable observable) {
            label.setText(((Property) observable).getValue().toString());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Transaction} using a {@code TransactionCard}.
     */
    class TransactionListViewCell extends ListCell<Transaction> {
        @Override
        protected void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);

            if (empty || transaction == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TransactionCard(transaction, getIndex() + 1).getRoot());
            }
        }
    }

}
