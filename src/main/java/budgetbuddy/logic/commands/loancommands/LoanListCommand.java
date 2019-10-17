package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.person.Person;

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

        // TODO Display the list in the main window instead of in the command result text box.
        StringBuilder builder = new StringBuilder();
        builder.append("Current Loans:");
        for (int i = 0; i < model.getLoansManager().getPersonsList().size(); i++) {
            builder.append("\n");
            Person person = model.getLoansManager().getPersonsList().get(i);
            builder.append(i + 1).append(". ").append(person.toString()).append("\n");
            for (int j = 0; j < person.getLoans().size(); j++) {
                builder.append("  ").append(j + 1).append(".").append(person.getLoans().get(j)).append("\n");
            }
        }

        return new CommandResult(builder.toString());
    }
}
