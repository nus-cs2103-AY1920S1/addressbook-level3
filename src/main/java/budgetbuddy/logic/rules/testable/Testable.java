package budgetbuddy.logic.rules.testable;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a predicate with hidden internal logic and the ability to be tested.
 */
@FunctionalInterface
public interface Testable {
    /**
     * Tests if the transaction and account satisfy the predicate.
     */
    boolean test(Transaction txn, Account account);
}
