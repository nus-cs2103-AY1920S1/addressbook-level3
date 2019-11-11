package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedRemoveInstallmentCommand;

/**
 * Deletes an existing installment identified using its displayed index in the finance tracker.
 */
public class RemoveInstallmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-install";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Jarvis will remove the installment identified by the index number used in the displayed list of "
            + "installments.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INSTALLMENT_SUCCESS = "Jarvis has removed this installment! \n%1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "Jarvis has added this installment back: %1$s";
    public static final String MESSAGE_INVERSE_INSTALLMENT_TO_ADD_ALREADY_EXIST = "Installment was already added: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;

    private Installment deletedInstallment;

    /**
     * Creates a {@code RemoveInstallmentCommand} and sets the targetIndex to the {@code Index}
     * of the {@code Installment} to be deleted.
     *
     * @param targetIndex {@code Index} of the {@code Installment} to be deleted.
     * @param deletedInstallment Installment that was deleted, which can be null.
     */
    public RemoveInstallmentCommand(Index targetIndex, Installment deletedInstallment) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.deletedInstallment = deletedInstallment;
    }

    public RemoveInstallmentCommand(Index targetIndex) {
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
     * Gets the {@code Index} of the {@code Installment} to be deleted.
     *
     * @return {@code Index} of the {@code Installment} to be deleted.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the {@code Installment} that was deleted wrapped in an {@code Optional}.
     *
     * @return {@code Installment} that was deleted wrapped in an {@code Optional}.
     */
    public Optional<Installment> getDeletedInstallment() {
        return Optional.ofNullable(deletedInstallment);
    }

    /**
     * Deletes {@code Installment} from finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful delete.
     * @throws CommandException If targetIndex is >= the number of installments in finance tracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            deletedInstallment = model.getInstallment(targetIndex.getOneBased());
            model.deleteInstallment(targetIndex.getOneBased());
            model.setViewStatus(ViewType.LIST_FINANCE);

            return new CommandResult(String.format(MESSAGE_DELETE_INSTALLMENT_SUCCESS, deletedInstallment),
                    true);
        } catch (InstallmentNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_INSTALLMENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Adds back the {@code Installment} that was deleted
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful restore of the deleted {@code Installment}
     * if the {@code Installment} is not already in the finance tracker
     * @throws CommandException If the installment to be added will be in conflict with an existing installment
     * in the finance tracker
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInstallment(deletedInstallment)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_INSTALLMENT_TO_ADD_ALREADY_EXIST,
                    deletedInstallment));
        }

        model.addInstallment(targetIndex.getZeroBased(), deletedInstallment);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, deletedInstallment));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedRemoveInstallmentCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveInstallmentCommand
                && targetIndex.equals((((RemoveInstallmentCommand) other).targetIndex)));
    }
}
