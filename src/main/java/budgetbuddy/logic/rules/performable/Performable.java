package budgetbuddy.logic.rules.performable;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;

/**
 * Represents an action with hidden internal logic and the ability to be performed.
 */
public interface Performable {
    /**
     * Executes the action.
     */
    void perform(Model model, Index txnIndex, Account account);
}
