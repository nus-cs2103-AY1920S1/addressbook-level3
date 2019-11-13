package budgetbuddy.ui.tab;

import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.ui.panel.TransactionPanel;
import javafx.collections.ObservableList;

/**
 * Represents a tab component that displays the transaction panel when selected.
 */
public class TransactionTab extends PanelTab {

    public TransactionTab(ObservableList<Transaction> transactionList) {
        super(new TransactionPanel(transactionList), "Transaction");
    }
}
