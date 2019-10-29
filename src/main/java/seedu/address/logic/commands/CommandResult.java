package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Used for access expenseList inside a budget.
     */
    private final ObservableList<Expense> expenseList;

    /**
     * Used for access budgetList.
     */
    private final ObservableList<Budget> budgetList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.budgetList = null;
        this.expenseList = null;
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(ObservableList<Expense> expenseList, ObservableList<Budget> budgetList,
                         String feedbackToUser, boolean showHelp,
                         boolean exit) {
        this.budgetList = budgetList;
        this.expenseList = expenseList;
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code expenseList}, {@code viewState}
     * and other fields set to their default value.
     */
    public CommandResult(ObservableList<Expense> expenseList, ObservableList<Budget> budgetList,
                         String feedbackToUser) {
        this(expenseList, budgetList, feedbackToUser, false, false);
    }

    public ObservableList<Expense> getExpenseList() {
        return expenseList;
    }

    public ObservableList<Budget> getBudgetList() {
        return budgetList;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && showHelp == otherCommandResult.showHelp
            && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }
}
