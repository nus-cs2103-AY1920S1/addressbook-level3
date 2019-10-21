package seedu.algobase.model.searchrule.problemsearchrule.exceptions;

/**
 * Signals that the operation would result in duplicate find rules.
 */
public class DuplicateProblemSearchRuleException extends RuntimeException {
    public DuplicateProblemSearchRuleException() {
        super("Operation would result in duplicate find rules.");
    }
}
