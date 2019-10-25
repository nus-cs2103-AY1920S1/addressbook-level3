package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.purchase.Purchase;

/**
 * Adds a purchase to the finance tracker.
 */
public class SetPaidCommand extends Command {

    public static final String COMMAND_WORD = "add-paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a payment to the finance tracker. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Aston's for Lunch "
            + PREFIX_MONEY + "9.50 ";

    public static final String MESSAGE_SUCCESS = "New purchase added: %1$s";
    public static final String MESSAGE_DUPLICATE_PURCHASE = "This purchase already exists in the finance tracker";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Payment: %1$s";
    public static final String MESSAGE_INVERSE_PURCHASE_NOT_FOUND = "Payment already deleted: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Purchase toAdd;

    /**
     * Creates a {@code PaidCommand} to add the specified (@code Purchase}.
     */
    public SetPaidCommand(Purchase purchase) {
        requireNonNull(purchase);
        toAdd = purchase;
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
     * Adds {@code Purchase} to the finance tracker, if the purchase does not already exist in finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that purchase was added successfully.
     * @throws CommandException if there is already a {@code Purchase} matching the purchase to be added to the
     * finance tracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPurchase(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PURCHASE);
        }

        model.addPurchase(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Deletes {@code Purchase} from the finance tracker that was added by this command's execution
     * if the purchase is still in the finance tracker
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that purchase was removed if purchase was in the finance tracker,
     * else {@code CommandResult} that the purchase was already not in the finance tracker
     * @throws CommandException If purchase to be removed is not found in the finance tracker
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPurchase(toAdd)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_PURCHASE_NOT_FOUND, toAdd));
        }

        model.deletePurchase(toAdd);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPaidCommand // instanceof handles nulls
                && toAdd.equals(((SetPaidCommand) other).toAdd));
    }
}
