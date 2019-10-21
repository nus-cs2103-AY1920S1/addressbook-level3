package thrift.logic.commands;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.ui.TransactionListPanel;

/**
 * Represents a command with hidden internal logic and the ability to be executed; upon execution, command makes display
 * scroll to modified item.
 */
public abstract class ScrollingCommand extends Command {

    /**
     * Executes the command that scrolls to modified item and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param transactionListPanel {@code TransactionListPanel} which displays the list of transactions.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, TransactionListPanel transactionListPanel)
            throws CommandException;
}
