package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.exceptions.DuplicateLoanException;

/**
 * Adds a loan.
 */
public class LoanCommand extends Command {

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
    public static final String MESSAGE_DUPLICATE_LOAN = "This loan already exists in the person's list.";

    private final Loan toAdd;

    public LoanCommand(Loan loan) {
        requireNonNull(loan);
        toAdd = loan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getLoansManager());

        try {
            model.getLoansManager().addLoan(toAdd);
        } catch (DuplicateLoanException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanCommand)) {
            return false;
        }

        LoanCommand otherCommand = (LoanCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }
}
