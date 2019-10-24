package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.installment.Installment;

/**
 * Adds a purchase to the finance tracker.
 */
public class SetInstallmentCommand extends Command {

    public static final String COMMAND_WORD = "add-install";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an installment to the finance tracker. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Netflix subscription "
            + PREFIX_MONEY + "13.50 ";

    public static final String MESSAGE_SUCCESS = "New installment added: %1$s";
    public static final String MESSAGE_DUPLICATE_INSTALLMENT = "This purchase already exists in the finance tracker";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Installment: %1$s";
    public static final String MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND = "Installment already deleted: %1$s";

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
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
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
     * Adds {@code Installment} to the finance tracker, if the installment does not already exist in the
     * finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that purchase was added successfully.
     * @exception CommandException if there is already an {@code Installment} matching the installment to be
     * added to the finance tracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInstallment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INSTALLMENT);
        }

        model.addInstallment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Deletes {@code Installment} from the finance tracker that was added by this command's execution
     * if the installment is still in the finance tracker
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that installment was removed if installment was in the finance tracker,
     * else {@code CommandResult} that the installment was already not in the finance tracker
     * @throws CommandException If installment to be removed is not found in the finance tracker
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasInstallment(toAdd)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND, toAdd));
        }

        model.deleteInstallment(toAdd);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetInstallmentCommand // instanceof handles nulls
                && toAdd.equals(((SetInstallmentCommand) other).toAdd));
    }
}
