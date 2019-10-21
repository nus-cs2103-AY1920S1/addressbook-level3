package budgetbuddy.logic.rules.testable;

import java.util.function.Predicate;

import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a predicate with hidden internal logic and the ability to be tested.
 */
public interface Testable extends Predicate<Transaction> {
}
