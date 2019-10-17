package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;

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

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;

    private Cca deletedCca;

    /**
     * Creates a {@code DeleteCcaCommand} and sets targetIndex to the {@code Index}
     * of the {@code Cca} to be deleted.
     *
     * @param targetIndex of the {@code Cca} to be deleted.
     */
    public DeleteCcaCommand(Index targetIndex) {
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

    @Override
    public boolean hasInverseExecution() {
        return false;
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

        return new CommandResult(String.format(MESSAGE_DELETE_CCA_SUCCESS, deletedCca));
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }
}
