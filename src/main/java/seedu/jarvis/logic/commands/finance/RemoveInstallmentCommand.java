package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;

/**
 * Deletes an existing purchase identified using its displayed index in the finance tracker.
 */
public class RemoveInstallmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-install";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the installment identified by the index number used in the displayed list of purchases.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INSTALLMENT_SUCCESS = "Deleted Installment: %1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "New person added: %1$s";
    public static final String MESSAGE_INVERSE_INSTALLMENT_TO_ADD_ALREADY_EXIST = "Person already added: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;

    private Installment toDelete;

    /**
     * Creates a {@code RemoveInstallmentCommand} and sets the targetIndex to the {@code Index}
     * of the {@code Installment} to be deleted.
     *
     * @param targetIndex of the {@code Purchase} to be deleted
     */
    public RemoveInstallmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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
     * Deletes {@code Installment} from finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful delete.
     * @throws CommandException If targetIndex is >= the number of persons in address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            toDelete = model.getInstallment(targetIndex.getOneBased());
            model.deleteInstallment(targetIndex.getOneBased());
            return new CommandResult(String.format(MESSAGE_DELETE_INSTALLMENT_SUCCESS, toDelete));
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

        if (model.hasInstallment(toDelete)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_INSTALLMENT_TO_ADD_ALREADY_EXIST, toDelete));
        }

        model.addInstallment(targetIndex.getZeroBased(), toDelete);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveInstallmentCommand
                && targetIndex.equals((((RemoveInstallmentCommand) other).targetIndex)));
    }
}
