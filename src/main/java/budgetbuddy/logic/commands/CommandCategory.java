package budgetbuddy.logic.commands;

/**
 * Represents the category that a command belongs to.
 */

public enum CommandCategory {
    RULE, ACCOUNT, TRANSACTION, LOAN, SCRIPT,
    LOAN_SPLIT;

    public boolean isSecondaryCategory() {
        return this == LOAN_SPLIT;
    }

    public CommandCategory getPrimaryCategory(CommandCategory secondary) {
        if (secondary == LOAN_SPLIT) {
            return LOAN;
        }
        return this;
    }
}
