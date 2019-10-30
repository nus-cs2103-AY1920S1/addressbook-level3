package budgetbuddy.ui.tab;

import budgetbuddy.model.account.Account;
import budgetbuddy.ui.panel.AccountListPanel;
import javafx.collections.ObservableList;

/**
 * Represents a tab component that displays the account panel when selected.
 */
public class AccountTab extends PanelTab {

    public AccountTab(ObservableList<Account> accountList) {
        super(new AccountListPanel(accountList), "/images/accountTab.png");
    }
}
