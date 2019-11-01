package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;

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
    public void perform(Model model, Transaction txn, Account account) {
        evaluator.run(txn, account);
    }

    /**
     * Represents a function that can execute scripts.
     */
    @FunctionalInterface
    public interface ScriptEvaluator {
        /**
         * Executes the script and returns the result.
         */
        void run(Transaction txn, Account account);
    }
}
