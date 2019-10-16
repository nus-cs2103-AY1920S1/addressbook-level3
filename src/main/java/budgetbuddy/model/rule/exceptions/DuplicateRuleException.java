package budgetbuddy.model.rule.exceptions;

/**
 * Signals that the operation will result in duplicate Rules (Rules are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRuleException extends RuntimeException {
    public DuplicateRuleException() {
        super("Operation would result in duplicate rules");
    }
}
