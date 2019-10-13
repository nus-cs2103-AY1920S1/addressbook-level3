package seedu.address.overview.logic;

import seedu.address.overview.commands.CommandResult;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws Exception If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws Exception;

    double getTotalExpenses();
    double getTotalInventory();
    double getTotalSales();
    double getRemainingBudget();
    double getExpenseTarget();
    double getSalesTarget();
    double getBudgetTarget();
}
