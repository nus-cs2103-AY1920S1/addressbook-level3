package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;

/**
 * Adds a loan.
 */
public class AddLoanCommand extends Command {

    public static final String COMMAND_WORD = "loan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a loan.\n"
            + "Parameters: "
            + "out|in "
            + PREFIX_PERSON + "PERSON "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + "out "
            + PREFIX_PERSON + "John Doe "
            + PREFIX_AMOUNT + "4.20 "
            + PREFIX_DESCRIPTION + "Weed money. "
            + PREFIX_DATE + "4/12/2020";

    public static final String MESSAGE_SUCCESS = "New loan added: %1$s";

    private final Loan toAdd;

    public AddLoanCommand(Loan loan) {
        requireNonNull(loan);
        toAdd = loan;
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getLoansManager());

        model.getLoansManager().addLoan(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddLoanCommand)) {
            return false;
        }

        AddLoanCommand otherCommand = (AddLoanCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }
}
