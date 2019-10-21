package budgetbuddy.logic.rules.actionable;

import budgetbuddy.model.Model;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents an action with hidden internal logic and the ability to be performed.
 */
public interface Actionable {
    /**
     * Executes the action and returns the completion message.
     */
    String perform(Model model, Transaction txn);
}
