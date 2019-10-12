package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.Installment;

/**
 * Adds a purchase to the finance tracker.
 */
public class SetInstallmentCommand extends Command {

    public static final String COMMAND_WORD = "intall set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an installment to the finance tracker. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Netflix subscription "
            + PREFIX_MONEY + "13.50 ";

    public static final String MESSAGE_SUCCESS = "New installment added: %1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Installment: %1$s";
    public static final String MESSAGE_INVERSE_PAYMENT_NOT_FOUND = "Installment already deleted: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Installment toAdd;

    /**
     * Creates a {@code SetInstallmentCommand} to add the specified (@code Installment}.
     */
    public SetInstallmentCommand(Installment installment) {
        requireNonNull(installment);
        toAdd = installment;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Adds {@code Installment} to the finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that purchase was added successfully.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.addInstallment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetInstallmentCommand // instanceof handles nulls
                && toAdd.equals(((SetInstallmentCommand) other).toAdd));
    }
}
