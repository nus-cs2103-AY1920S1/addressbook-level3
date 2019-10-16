package seedu.address.logic.commands.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.loan.Loan;

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
