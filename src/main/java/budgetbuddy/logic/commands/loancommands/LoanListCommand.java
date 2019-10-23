package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;

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

    public static final String MESSAGE_SUCCESS = "Loans listed.";

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());
        return new CommandResult(MESSAGE_SUCCESS, CommandCategory.LOAN);
    }
}
