package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;

import javafx.collections.ObservableList;

/**
 * Lists loans.
 */
public class LoanListCommand extends Command {

    public static final String COMMAND_WORD = "loan list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all loans.\n"
            + "Parameters: "
            + "[<person number ...>]"
            + "[o|i] "
            + String.format("[%s<d|a>]", PREFIX_SORT) + "\n"
            + "Example: "
            + "loan list 1 2 o a";

    public static final String MESSAGE_SUCCESS = "Listed loans.";

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());

        LoansManager loansManager = model.getLoansManager();
        ObservableList<Loan> loans = loansManager.getSortedLoans();

        // TODO Display the list in the main window instead of in the command result text box.
        StringBuilder builder = new StringBuilder();
        builder.append("Current Loans:\n");
        for (int i = 0; i < loansManager.getLoansCount(); i++) {
            builder.append(loans.get(i)).append("\n");
        }

        return new CommandResult(builder.toString(), null);
    }
}
