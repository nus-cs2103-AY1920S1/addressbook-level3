package budgetbuddy.logic.rules.testable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a predicate written as a script.
 */
public class TestableScript implements Testable {

    private final ScriptEvaluator evaluator;

    /**
     * Constructs a TestableScript given a script evaluator.
     */
    public TestableScript(ScriptEvaluator evaluator) {
        requireAllNonNull(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public boolean test(Transaction txn, Account account) {
        return evaluator.run(txn, account);
    }

    /**
     * Represents a function that can execute scripts.
     */
    @FunctionalInterface
    public interface ScriptEvaluator {
        /**
         * Executes the script and returns the result.
         */
        boolean run(Transaction txn, Account account);
    }
}
