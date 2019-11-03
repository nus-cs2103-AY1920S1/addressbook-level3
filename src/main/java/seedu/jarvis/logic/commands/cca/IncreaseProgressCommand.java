package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.cca.CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS;
import static seedu.jarvis.model.viewstatus.ViewType.LIST_CCA;

import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedIncreaseProgressCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Increments progress for the chosen cca.
 */
public class IncreaseProgressCommand extends Command {

    public static final String COMMAND_WORD = "increment-progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Increases progress for the chosen cca. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String MESSAGE_INCREMENT_PROGRESS_SUCCESS = "Incremented progress for Cca: %1$s";
    public static final String MESSAGE_DECREMENT_PROGRESS_SUCCESS = "Decremented progress for Cca: %1$s";
    public static final String MESSAGE_CCA_PROGRESS_NOT_YET_SET = "A progress does not yet exists in this cca.";
    public static final String MESSAGE_INCREMENT_AT_MAX = "Cca progress at maximum.";
    public static final String MESSAGE_INCREMENT_AT_MIN = "Cca progress at minimum.";
    public static final boolean HAS_INVERSE = false;

    private final Index targetIndex;
    private Cca targetCca;

    /**
     * Constructs a {@code IncreaseProgressCommand} to increase the progress of the cca at the given {@code Index}.
     *
     * @param targetIndex {@code Index} of the cca.
     * @param targetCca {@code Cca} that is updated.
     */
    public IncreaseProgressCommand(Index targetIndex, Cca targetCca) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.targetCca = targetCca;
    }

    public IncreaseProgressCommand(Index targetIndex) {
        this(targetIndex, null);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Gets the target {@code Index}.
     * @return Target {@code Index}.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the target {@code Cca}.
     *
     * @return Target {@code Cca}.
     */
    public Optional<Cca> getTargetCca() {
        return Optional.ofNullable(targetCca);
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
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
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.setViewStatus(LIST_CCA);
        return new CommandResult(String.format(MESSAGE_INCREMENT_PROGRESS_SUCCESS, targetIndex.getOneBased()),
                true);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getNumberOfCcas()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        if (!model.ccaContainsProgress(targetIndex)) {
            throw new CommandException(MESSAGE_CCA_PROGRESS_NOT_YET_SET);
        }

        if (model.ccaProgressAtMinLevel(targetIndex)) {
            throw new CommandException(MESSAGE_INCREMENT_AT_MIN);
        }

        model.decreaseProgress(targetIndex);

        return new CommandResult(String.format(MESSAGE_DECREMENT_PROGRESS_SUCCESS, targetIndex.getOneBased()));

    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedIncreaseProgressCommand(this);
    }

    @Override
    public boolean equals(Object obj) {
        // Short circuit if it is the same object.
        if (obj == this) {
            return true;
        }
        // instanceof handles nulls.
        if (!(obj instanceof IncreaseProgressCommand)) {
            return false;
        }
        IncreaseProgressCommand other = (IncreaseProgressCommand) obj;
        return targetIndex.equals(other.targetIndex);
    }
}
