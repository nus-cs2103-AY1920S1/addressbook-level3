package budgetbuddy.logic.rules.testable;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;

/**
 * Represents a predicate with hidden internal logic and the ability to be tested.
 */
@FunctionalInterface
public interface Testable {
    /**
     * Tests if the transaction in this account satisfy the predicate.
     */
    boolean test(Index txnIndex, Account account);
}
