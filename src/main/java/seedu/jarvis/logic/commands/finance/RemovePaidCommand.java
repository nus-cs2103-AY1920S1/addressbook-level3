package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedRemovePaidCommand;

/**
 * Deletes an existing purchase identified using its displayed index in the finance tracker.
 */
public class RemovePaidCommand extends Command {

    public static final String COMMAND_WORD = "delete-paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Jarvis will remove the purchase identified by the index number used in the displayed list of "
            + "purchases.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PURCHASE_SUCCESS = "Jarvis has deleted this purchase! \n%1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "Jarvis has added this purchase back: %1$s";
    public static final String MESSAGE_INVERSE_PURCHASE_TO_ADD_ALREADY_EXIST = "Purchase was already added: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;

    private Purchase deletedPurchase;

    /**
     * Creates a {@code RemovePaidCommand} and sets the targetIndex to the {@code Index} of the {@code Purchase} to be
     * deleted.
     *
     * @param targetIndex of the {@code Purchase} to be deleted
     * @param deletedPurchase {@code Purchase} that was deleted, can be null.
     */
    public RemovePaidCommand(Index targetIndex, Purchase deletedPurchase) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.deletedPurchase = deletedPurchase;
    }

    public RemovePaidCommand(Index targetIndex) {
        this(targetIndex, null);
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
     * Gets the {@code Index} of the purchase to be deleted.
     *
     * @return {@code Index} of the purchase to be deleted.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the {@code Purchase} that was deleted wrapped in {@code Optional}.
     *
     * @return {@code Purchase} that was deleted wrapped in {@code Optional}.
     */
    public Optional<Purchase> getDeletedPurchase() {
        return Optional.ofNullable(deletedPurchase);
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
     * Deletes {@code Purchase} from finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful delete.
     * @throws CommandException If targetIndex is >= the number of installments in finance tracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            deletedPurchase = model.getPurchase(targetIndex.getOneBased());
            model.deletePurchase(targetIndex.getOneBased());
            model.setViewStatus(ViewType.LIST_FINANCE);

            return new CommandResult(String.format(MESSAGE_DELETE_PURCHASE_SUCCESS, deletedPurchase), true);
        } catch (PurchaseNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PURCHASE_DISPLAYED_INDEX);
        }
    }

    /**
     * Adds back the {@code Purchase} that was deleted
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful restore of the deleted {@code Purchase}
     * if the {@code Purchase} is not already in the finance tracker
     * @throws CommandException If the purchase to be added will be in conflict with an existing purchase
     * in the finance tracker
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPurchase(deletedPurchase)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_PURCHASE_TO_ADD_ALREADY_EXIST,
                    deletedPurchase));
        }

        model.addPurchase(targetIndex.getZeroBased(), deletedPurchase);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, deletedPurchase));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedRemovePaidCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemovePaidCommand)) {
            return false;
        }

        RemovePaidCommand command = (RemovePaidCommand) other;
        return targetIndex.equals(command.targetIndex) && Objects.equals(deletedPurchase, command.deletedPurchase);
    }
}
