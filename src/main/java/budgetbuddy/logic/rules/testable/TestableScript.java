package budgetbuddy.logic.rules.testable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.account.Account;

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
    public boolean test(Index txnIndex, Account account) {
        return evaluator.run(txnIndex, account);
    }

    /**
     * Represents a function that can execute scripts.
     */
    @FunctionalInterface
    public interface ScriptEvaluator {
        /**
         * Executes the script and returns the result.
         */
        boolean run(Index txnIndex, Account account);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TestableScript
                && evaluator.equals(((TestableScript) other).evaluator));
    }

    @Override
    public int hashCode() {
        return evaluator.hashCode();
    }
}
