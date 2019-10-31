package budgetbuddy.logic.rules.performable;

import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents an action with hidden internal logic and the ability to be performed.
 */
public interface Performable {
    /**
     * Executes the action.
     */
    void perform(Model model, Transaction txn, Account account);
}
