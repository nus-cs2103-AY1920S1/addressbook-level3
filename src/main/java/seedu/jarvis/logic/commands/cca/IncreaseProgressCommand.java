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
 * Increments progress for the chosen cca.
 */
public class IncreaseProgressCommand extends Command {

    public static final String COMMAND_WORD = "increment-progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a progress tracker to a cca. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String MESSAGE_INCREMENT_PROGRESS_SUCCESS = "Incremented progress for Cca: %1$s";
    public static final String MESSAGE_CCA_PROGRESS_NOT_YET_SET = "A progress does not yet exists in this cca.";
    public static final String MESSAGE_INCREMENT_AT_MAX = "Cca progress at maximum.";

    private final Index targetIndex;

    private Cca targetCca;

    public IncreaseProgressCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String getCommandWord() {
        return null;
    }

    @Override
    public boolean hasInverseExecution() {
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getNumberOfCcas()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        if (!model.ccaContainsProgress(targetIndex)) {
            throw new CommandException(MESSAGE_CCA_PROGRESS_NOT_YET_SET);
        }

        if (model.ccaAtMaxIncrement(targetIndex)) {
            throw new CommandException(MESSAGE_INCREMENT_AT_MAX);
        }

        model.increaseProgress(targetIndex);

        return new CommandResult(String.format(MESSAGE_INCREMENT_PROGRESS_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }
}
