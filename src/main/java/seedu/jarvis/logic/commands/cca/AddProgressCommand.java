package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL_NAMES;
import static seedu.jarvis.model.cca.CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS;
import static seedu.jarvis.model.viewstatus.ViewType.LIST_CCA;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;

/**
 * Adds a progress tracker to the specified cca .
 */
public class AddProgressCommand extends Command {

    public static final String COMMAND_WORD = "add-progress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a progress tracker to a cca. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PROGRESS_LEVEL_NAMES + "PROGRESS LEVEL NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_PROGRESS_LEVEL_NAMES + "bronze standard "
            + PREFIX_PROGRESS_LEVEL_NAMES + "silver standard "
            + PREFIX_PROGRESS_LEVEL_NAMES + "gold standard "
            + PREFIX_PROGRESS_LEVEL_NAMES + "gold star standard";

    public static final String MESSAGE_SUCCESS = "New progress added to cca at index: %1$s";
    public static final String MESSAGE_CCA_PROGRESS_ALREADY_SET = "A progress already exists in this cca.";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Progress: %1$s";
    public static final String MESSAGE_INVERSE_PROGRESS_NOT_FOUND = "Progress already deleted for Cca at index: %1$s";
    public static final String MESSAGE_INVERSE_CCA_NOT_FOUND = "Cca at index: %1$s not found";

    public static final boolean HAS_INVERSE = false;


    private final Index targetIndex;

    private Cca targetCca;

    private final CcaMilestoneList toAddCcaMilestoneList;


    /**
     * Creates a {@code AddProgressCommand}, sets targetIndex to the {@code Index} and sets toAddCcaMilestoneList to the
     * {@code CcaProgress} ccaProgress of the {@code Cca} at the targetIndex.
     *
     * @param targetIndex of the {@code Cca} to be deleted.
     * @param ccaMilestoneList {@code CcaMilestoneList}.
     * @param targetCca {@code Cca}.
     */
    public AddProgressCommand(Index targetIndex, CcaMilestoneList ccaMilestoneList, Cca targetCca) {
        requireAllNonNull(targetIndex, ccaMilestoneList);
        this.targetIndex = targetIndex;
        this.toAddCcaMilestoneList = ccaMilestoneList;
        this.targetCca = targetCca;
    }

    public AddProgressCommand(Index targetIndex, CcaMilestoneList ccaMilestoneList) {
        this(targetIndex, ccaMilestoneList, null);
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public Cca getTargetCca() {
        return targetCca;
    }

    public CcaMilestoneList getCcaMilestoneList() {
        return toAddCcaMilestoneList;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
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

        if (model.ccaContainsProgress(targetIndex)) {
            throw new CommandException(MESSAGE_CCA_PROGRESS_ALREADY_SET);
        }

        targetCca = model.getCca(targetIndex);

        model.addProgress(targetCca, toAddCcaMilestoneList);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.setViewStatus(LIST_CCA);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()), true);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.containsCca(targetCca)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_CCA_NOT_FOUND, targetIndex));

        }

        if (!model.ccaContainsProgress(targetIndex)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_PROGRESS_NOT_FOUND, targetIndex));
        }

        model.removeProgress(targetCca, toAddCcaMilestoneList);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, targetIndex));

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
        return toAddCcaMilestoneList.equals(e.toAddCcaMilestoneList);
    }
}
