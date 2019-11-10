package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedSetPaidCommand;

/**
 * Adds a purchase to the finance tracker.
 */
public class SetPaidCommand extends Command {

    public static final String COMMAND_WORD = "add-paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Jarvis will add a purchase to the finance tracker. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Astons for lunch "
            + PREFIX_MONEY + "9.50";

    public static final String MESSAGE_COMMAND_SYNTAX = "Command format: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY";

    public static final String MESSAGE_DESCRIPTION_ERROR = MESSAGE_COMMAND_SYNTAX + "\n"
            + PurchaseDescription.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_AMOUNT_ERROR = MESSAGE_COMMAND_SYNTAX + "\n"
            + PurchaseMoneySpent.MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_SUCCESS = "Jarvis has added your purchase! \n%1$s";
    public static final String MESSAGE_DUPLICATE_PURCHASE = "This purchase already exists!";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Jarvis has removed this purchase: \n%1$s";
    public static final String MESSAGE_INVERSE_PURCHASE_NOT_FOUND = "Purchase was already deleted: %1$s";

    public static final String MESSAGE_ALMOST_TO_LIMIT = "Note: You're almost at your spending limit for"
            + " the month!\n" + MESSAGE_SUCCESS;
    public static final String MESSAGE_OVERSPEND = "Note: You've reached your spending limit this month!"
            + " Please change your monthly limit with the command 'set-limit'.\n" + MESSAGE_SUCCESS;

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
     * Gets the {@code Purchase} to be added.
     *
     * @return {@code Purchase} to be added.
     */
    public Purchase getAddedPurchase() {
        return toAdd;
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
        model.setViewStatus(ViewType.LIST_FINANCE);

        boolean hasSpendingLimit = model.getMonthlyLimit().isPresent();
        if (hasSpendingLimit) {
            boolean isReachingLimit = model.calculateRemainingAmount() < 50.0 && model.calculateRemainingAmount() > 0;
            boolean hasExceededLimit = model.calculateRemainingAmount() <= 0;
            if (isReachingLimit) {
                return new CommandResult(String.format(MESSAGE_ALMOST_TO_LIMIT, toAdd), true);
            } else if (hasExceededLimit) {
                return new CommandResult(String.format(MESSAGE_OVERSPEND, toAdd), true);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), true);
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

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedSetPaidCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPaidCommand // instanceof handles nulls
                && toAdd.equals(((SetPaidCommand) other).toAdd));
    }
}
