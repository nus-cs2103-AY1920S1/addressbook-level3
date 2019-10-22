package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVELS;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL_NAMES;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.ccaprogress.CcaProgressList;

/**
 * Adds a progress tracker to the specified cca .
 */
public class AddProgressCommand extends Command {

    public static final String COMMAND_WORD = "add-progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a progress tracker to a cca. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PROGRESS_LEVELS + "PROGRESS LEVELS "
            + "[" + PREFIX_PROGRESS_LEVEL_NAMES + "PROGRESS LEVEL NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_PROGRESS_LEVELS + "3 "
            + PREFIX_PROGRESS_LEVEL_NAMES + "bronze standard "
            + PREFIX_PROGRESS_LEVEL_NAMES + "silver standard "
            + PREFIX_PROGRESS_LEVEL_NAMES + "gold standard "
            + PREFIX_PROGRESS_LEVEL_NAMES + "gold star standard";

    public static final String MESSAGE_SUCCESS = "New progress added to cca at index: %1$s";
    public static final String MESSAGE_DUPLICATE_CCA = "This specific progress already exists in this cca.";

    private final Index targetIndex;
    private Cca targetCca;

    private final CcaProgressList toAddCcaProgressList;

    /**
     * Creates a {@code AddProgressCommand}, sets targetIndex to the {@code Index} and sets toAddCcaProgressList to the
     * {@code CcaProgress} ccaProgress of the {@code Cca} at the targetIndex.
     *
     * @param targetIndex of the {@code Cca} to be deleted.
     */
    public AddProgressCommand(Index targetIndex, CcaProgressList ccaProgressList) {
        this.targetIndex = targetIndex;
        this.toAddCcaProgressList = ccaProgressList;
    }

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

        targetCca = model.getCca(targetIndex);
        model.addProgress(targetCca, toAddCcaProgressList);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddProgressCommand)) {
            return false;
        }

        // state check
        AddProgressCommand e = (AddProgressCommand) other;
        return toAddCcaProgressList.equals(e.toAddCcaProgressList);
    }
}
