package thrift.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import thrift.commons.core.LogsCenter;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Transaction;

/**
 * Panel containing the list of transactions.
 */
public class TransactionListPanel extends UiPart<Region> {
    private static final String FXML = "TransactionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private ListView<Transaction> transactionListView;

    public TransactionListPanel(ObservableList<Transaction> transactionList) {
        super(FXML);
        transactionListView.setItems(transactionList);
        transactionListView.setCellFactory(listView -> new TransactionListViewCell());

        //Solution below adapted from
        //https://github.com/AY1920S1-CS2103T-T09-3/main/blob/master/src/main/java/seedu/address/ui/PersonListPanel.java
        transactionListView.setOnKeyPressed(event -> {
            int itemPosition = transactionListView.getSelectionModel().getSelectedIndex();
            if (event.getCode().equals(KeyCode.DOWN)) {
                transactionListView.scrollTo(itemPosition);
            } else if (event.getCode().equals(KeyCode.UP)) {
                transactionListView.scrollTo(itemPosition);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Transaction} using either a
     * {@code ExpenseTransactionCard} or a {@code IncomeTransactionCard}.
     */
    class TransactionListViewCell extends ListCell<Transaction> {
        @Override
        protected void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);

            if (empty || transaction == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (transaction instanceof Expense) {
                    setGraphic(new ExpenseTransactionCard(transaction, getIndex() + 1).getRoot());
                } else {
                    setGraphic(new IncomeTransactionCard(transaction, getIndex() + 1).getRoot());
                }
            }
        }
    }

    public ListView<Transaction> getTransactionListView() {
        return transactionListView;
    }
}
