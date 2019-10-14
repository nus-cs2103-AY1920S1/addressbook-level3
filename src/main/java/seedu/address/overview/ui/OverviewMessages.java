package seedu.address.overview.ui;

/**
 * Formats the Ui return responses for different commands to be shown to user.
 */
public class OverviewMessages {

    public static final String MESSAGE_SET_BUDGET_SUCCESS = "Successfully set budget to %s.";

    public static final String MESSAGE_SET_EXPENSE_SUCCESS = "Successfully set expense goal to %s.";

    public static final String MESSAGE_SET_SALES_SUCCESS = "Successfully set sales target to %s.";

    public static final String MESSAGE_NOTIFY_BUDGET_SUCCESS = "I'll notify you when you reach "
            + "%s%% of your total budget.";

    public static final String MESSAGE_NOTIFY_EXPENSE_SUCCESS = "I'll notify you when you reach "
            + "%s%% of your expense goal.";

    public static final String MESSAGE_NOTIFY_SALES_SUCCESS = "I'll notify you when you reach "
            + "%s%% of your sales target.";

    public static final String MESSAGE_INVALID_SET_AMOUNT = "Sorry! That's an invalid amount.";

    public static final String MESSAGE_INVALID_NOTIFY_AMOUNT = "Sorry! That's an invalid percentage.\n Please enter a "
            + "number between 0 and 100.";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Sorry! You need to specify an argument with your "
            + "command.";

    public static final String MESSAGE_NO_SUCH_COMMAND = "Sorry! There's no such command here. You can try the "
            + "following: 'set' or 'notify";

    public static final String EXPENSE_SUMMARY_TEXT = "Total spent: $%s/%s";

    public static final String INVENTORY_SUMMARY_TEXT = "Inventory value: $%s";

    public static final String SALES_SUMMARY_TEXT = "Total sales: $%s/%s";

    public static final String BUDGET_SUMMARY_TEXT = "Amount remaining: $%s/%s";

    public static final String EXPENSE_PIE_CHART_TITLE = "Expenses by category";

}
