package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.cca.CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS;
import static seedu.jarvis.model.viewstatus.ViewType.LIST_CCA;

import java.util.Objects;
import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;

/**
 * Deletes a cca from Jarvis.
 */
public class DeleteCcaCommand extends Command {

    public static final String COMMAND_WORD = "delete-cca";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the cca identified by the index number used in the displayed cca list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CCA_SUCCESS = "Deleted Cca: %1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "New Cca added: %1$s";
    public static final String MESSAGE_INVERSE_CCA_TO_ADD_ALREADY_EXIST = "Cca already added: %1$s";

    public static final boolean HAS_INVERSE = false;

    private final Index targetIndex;

    private Cca deletedCca;

    /**
     * Creates a {@code DeleteCcaCommand} and sets targetIndex to the {@code Index}
     * of the {@code Cca} to be deleted.
     *
     * @param targetIndex of the {@code Cca} to be deleted.
     * @param deletedCca that was deleted.
     */
    public DeleteCcaCommand(Index targetIndex, Cca deletedCca) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.deletedCca = deletedCca;
    }

    public DeleteCcaCommand(Index targetIndex) {
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
     * Gets the {@code Index} of the cca to be deleted.
     *
     * @return {@code Index} of the cca to be deleted.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the deleted {@code Cca} wrapped in an {@code Optional}.
     *
     * @return Deleted {@code Cca} wrapped in an {@code Optional}.
     */
    public Optional<Cca> getDeletedCca() {
        return Optional.ofNullable(deletedCca);
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int numberOfCcas = model.getNumberOfCcas();

        if (targetIndex.getZeroBased() >= numberOfCcas) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        deletedCca = model.getCca(targetIndex);
        model.removeCca(deletedCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.setViewStatus(LIST_CCA);

        return new CommandResult(String.format(MESSAGE_DELETE_CCA_SUCCESS, deletedCca), true);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (model.containsCca(deletedCca)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_CCA_TO_ADD_ALREADY_EXIST, deletedCca));
        }

        model.addCca(deletedCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, deletedCca));

    }

    @Override
    public boolean equals(Object obj) {
        // Short circuit if it is the same object.
        if (obj == this) {
            return true;
        }
        // instanceof handles nulls.
        if (!(obj instanceof DeleteCcaCommand)) {
            return false;
        }
        DeleteCcaCommand other = (DeleteCcaCommand) obj;
        return targetIndex.equals(other.targetIndex) && Objects.equals(deletedCca, other.deletedCca);
    }
}
