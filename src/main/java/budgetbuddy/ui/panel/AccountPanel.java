package budgetbuddy.ui.panel;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.account.Account;
import budgetbuddy.ui.card.AccountCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Panel containing the list of accounts.
 */
public class AccountPanel extends DisplayPanel {
    private static final String FXML = "AccountPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AccountPanel.class);

    @FXML
    private ListView<Account> accountListView;

    public AccountPanel(ObservableList<Account> accountList) {
        super(FXML);
        accountListView.setItems(accountList);
        accountListView.setCellFactory(listView -> new AccountListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Account} using a {@code AccountCard}.
     */
    class AccountListViewCell extends ListCell<Account> {

        @Override
        protected void updateItem(Account account, boolean empty) {
            super.updateItem(account, empty);
            if (empty || account == null) {
                setGraphic(null);
                setText(null);
            } else {
                setMouseTransparent(true);
                setFocusTraversable(false);
                AccountCard accountCard = new AccountCard(account, getIndex() + 1);
                setGraphic(accountCard.getRoot());
                if (account.isActive()) {
                    accountCard.getRoot().getStyleClass().add("account-active");
                }
            }
        }
    }

}
