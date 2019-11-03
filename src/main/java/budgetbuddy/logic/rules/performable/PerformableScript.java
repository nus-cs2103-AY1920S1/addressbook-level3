package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;

/**
 * Represents an action written as a script.
 */
public class PerformableScript implements Performable {

    private final ScriptEvaluator evaluator;

    /**
     * Constructs a PerformableScript given a script evaluator.
     */
    public PerformableScript(ScriptEvaluator evaluator) {
        requireAllNonNull(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public void perform(Model model, Index txnIndex, Account account) {
        evaluator.run(txnIndex, account);
    }

    /**
     * Represents a function that can execute scripts.
     */
    @FunctionalInterface
    public interface ScriptEvaluator {
        /**
         * Executes the script and returns the result.
         */
        void run(Index txnIndex, Account account);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PerformableScript
                && evaluator.equals(((PerformableScript) other).evaluator));
    }

    @Override
    public int hashCode() {
        return evaluator.hashCode();
    }
}
