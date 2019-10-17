package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.person.Person;

/**
 * Lists loans.
 */
public class ListLoansCommand extends Command {

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

        // TODO Display the list in the main window instead of in the command result text box.
        StringBuilder builder = new StringBuilder();
        builder.append("Current Loans:");
        for (Person person : model.getLoansManager().getPersonsList()) {
            builder.append("\n");
            builder.append(person.toString()).append("\n");
            for (Loan loan : person.getLoans()) {
                builder.append("  ").append(loan).append("\n");
            }
        }

        return new CommandResult(builder.toString());
    }
}
