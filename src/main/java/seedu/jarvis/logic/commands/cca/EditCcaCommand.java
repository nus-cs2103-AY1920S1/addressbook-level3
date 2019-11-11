package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jarvis.logic.commands.cca.IncreaseProgressCommand.MESSAGE_CCA_PROGRESS_NOT_YET_SET;
import static seedu.jarvis.logic.commands.cca.IncreaseProgressCommand.MESSAGE_INCREMENT_AT_MAX;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_PROGRESS_LEVEL_NAMES;
import static seedu.jarvis.model.cca.CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS;
import static seedu.jarvis.model.viewstatus.ViewType.LIST_CCA;

import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.CollectionUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedEditCcaCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Edits the details of an existing cca in Jarvis.
 */
public class EditCcaCommand extends Command {

    public static final String COMMAND_WORD = "edit-cca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Cca identified "
            + "by the index number used in the displayed cca list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CCA_NAME + "CCA NAME] "
            + "[" + PREFIX_CCA_TYPE + "CCA TYPE] "
            + "[" + PREFIX_EQUIPMENT_NAME + "EQUIPMENT] "
            + "[" + PREFIX_PROGRESS_LEVEL + "LEVEL] "
            + "[" + PREFIX_PROGRESS_LEVEL_NAMES + "MILESTONE NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CCA_TYPE + "sport "
            + PREFIX_EQUIPMENT_NAME + "tennis racket";

    public static final String MESSAGE_EDIT_CCA_SUCCESS = "Edited Cca: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CCA = "This Cca already exists in the CcaTracker.";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "Duplicate equipment found.";
    public static final String MESSAGE_DUPLICATE_CCA_MILESTONES = "Duplicate milestones found.";

    public static final String MESSAGE_INVERSE_SUCCESS_EDIT = "Reverted edit.";

    public static final String MESSAGE_INVERSE_CCA_NOT_FOUND =
            "Unable to edit cca, cca not found in the CcaTracker.";

    public static final String MESSAGE_INVERSE_CONFLICT_WITH_EXISTING_CCA =
            "There is a conflict in reverting edits made to cca as there is an existing cca with similar details";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;
    private final EditCcaDescriptor editCcaDescriptor;

    private Cca originalCca;
    private Cca editedCca;

    /**
     * @param targetIndex of the {@code Cca} in the {@code CcaList} to edit
     * @param editCcaDescriptor details to edit the {@code cca} with
     * @param originalCca cca before the edit.
     * @param editedCca cca after the edit.
     */
    public EditCcaCommand(Index targetIndex, EditCcaDescriptor editCcaDescriptor, Cca originalCca, Cca editedCca) {
        requireAllNonNull(targetIndex, targetIndex);
        this.targetIndex = targetIndex;
        this.editCcaDescriptor = editCcaDescriptor;
        this.originalCca = originalCca;
        this.editedCca = editedCca;
    }

    /**
     * @param targetIndex of the {@code Cca} in the {@code CcaList} to edit
     * @param editCcaDescriptor details to edit the {@code cca} with
     */
    public EditCcaCommand(Index targetIndex, EditCcaDescriptor editCcaDescriptor) {
        this(targetIndex, editCcaDescriptor, null, null);
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
     * Gets the {@code Index} of the cca to be edited.
     *
     * @return {@code Index} of the cca to be edited.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the {@code EditCcaDescriptor} to edit the cca.
     *
     * @return {@code EditCcaDescriptor} to edit the cca.
     */
    public EditCcaDescriptor getEditCcaDescriptor() {
        return editCcaDescriptor;
    }

    /**
     * Gets the original {@code Cca} before the edit.
     *
     * @return Original {@code Cca} before the edit.
     */
    public Optional<Cca> getOriginalCca() {
        return Optional.ofNullable(originalCca);
    }

    /**
     * Gets the {@code Cca} after the edit.
     *
     * @return {@code Cca} after the edit.
     */
    public Optional<Cca> getEditedCca() {
        return Optional.ofNullable(editedCca);
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
     * Edits a {@code Cca} in CcaTracker with a new set of description from {@code EditCcaDescriptor}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful edit.
     * @throws CommandException If the targetIndex is out of range of the number of ccas in the CcaTracker in
     * zero-based indexing, or if the new edited description is already of an existing {@code Cca} in CcaTracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int numberOfCcas = model.getNumberOfCcas();

        // checks if index is out of bounds.
        if (targetIndex.getZeroBased() >= numberOfCcas) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        originalCca = model.getCca(targetIndex);

        Cca createdEditedCca = createEditedCca(originalCca, editCcaDescriptor);

        // checks if edited cca does not conflict with another existing cca that is not the original cca.
        if (!originalCca.isSameCca(createdEditedCca) && model.containsCca(createdEditedCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        editedCca = createdEditedCca;

        model.updateCca(originalCca, editedCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.setViewStatus(LIST_CCA);

        return new CommandResult(String.format(MESSAGE_EDIT_CCA_SUCCESS, editedCca), true);
    }

    /**
     * Reverts back the edits made to {@code Cca} in CcaTracker by the command's execution.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful revert of {@code Cca} if the revert is possible, or a
     * {@code CommandResult} that the edited cca is no longer in the CcaTracker, or a {@code CommandResult} that
     * there will be a conflict with an existing {@code Cca} in the CcaTracker if the revert is made.
     * @throws CommandException If the cca to be reverted is not found in the CcaTracker, or if reverting the edits
     * to the cca will result in a conflict with another cca in the CcaTracker.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        // checks if cca to be reverted is in Cca Tracker.
        if (!model.containsCca(editedCca)) {
            throw new CommandException(MESSAGE_INVERSE_CCA_NOT_FOUND);
        }

        // checks if reverting the Cca will be in conflict with another existing Cca in the CcaTracker.
        if (!originalCca.isSameCca(editedCca) && model.containsCca(originalCca)) {
            throw new CommandException(MESSAGE_INVERSE_CONFLICT_WITH_EXISTING_CCA);
        }

        model.updateCca(editedCca, originalCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        model.setViewStatus(LIST_CCA);

        return new CommandResult(MESSAGE_INVERSE_SUCCESS_EDIT, true);
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedEditCcaCommand(this);
    }

    /**
     * Creates and returns a {@code Cca} with the details of {@code ccaToEdit}
     * edited with {@code editCcaDescriptor}.
     */
    private static Cca createEditedCca(Cca ccaToEdit, EditCcaDescriptor editCcaDescriptor) throws CommandException {
        assert ccaToEdit != null;

        CcaName updatedName = editCcaDescriptor.getCcaName().orElse(ccaToEdit.getName());
        CcaType updatedCcaType = editCcaDescriptor.getCcaType().orElse(ccaToEdit.getCcaType());
        EquipmentList updatedEquipmentList = editCcaDescriptor.getEquipmentList().orElse(ccaToEdit.getEquipmentList());

        CcaProgress updatedCcaProgress = editCcaDescriptor.getCcaProgress()
                .orElse(new CcaProgress(ccaToEdit.getCcaProgress()));

        if (editCcaDescriptor.getCcaMilestoneList().isPresent()) {
            updatedCcaProgress.setMilestones(editCcaDescriptor.getCcaMilestoneList().get());
            updatedCcaProgress.resetCcaCurrentProgress();
        }

        if (editCcaDescriptor.getCcaCurrentProgress().isPresent() && updatedCcaProgress.ccaMilestoneListIsEmpty()) {
            throw new CommandException(MESSAGE_CCA_PROGRESS_NOT_YET_SET);
        }

        if (editCcaDescriptor.getCcaCurrentProgress().isPresent()
                && editCcaDescriptor.getCcaCurrentProgress().get().getCurrentProgress()
                > updatedCcaProgress.getMilestoneList().size()) {
            throw new CommandException(MESSAGE_INCREMENT_AT_MAX);
        }

        if (editCcaDescriptor.getCcaCurrentProgress().isPresent()) {
            updatedCcaProgress.setCcaCurrentProgress(editCcaDescriptor.getCcaCurrentProgress().get());
        }

        return new Cca(updatedName, updatedCcaType, updatedEquipmentList, updatedCcaProgress);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCcaCommand)) {
            return false;
        }

        // state check
        EditCcaCommand e = (EditCcaCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editCcaDescriptor.equals(e.editCcaDescriptor);
    }

    /**
     * Stores the details to edit the cca with. Each non-empty field value will replace the
     * corresponding field value of the cca.
     */
    public static class EditCcaDescriptor {
        private CcaName ccaName;
        private CcaType ccaType;
        private EquipmentList equipmentList;
        private CcaProgress ccaProgress;
        private CcaMilestoneList ccaMilestoneList;
        private CcaCurrentProgress ccaCurrentProgress;

        public EditCcaDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditCcaDescriptor(EditCcaDescriptor toCopy) {
            setCcaName(toCopy.ccaName);
            setCcaType(toCopy.ccaType);
            setEquipmentList(toCopy.equipmentList);
            setCcaProgress(toCopy.ccaProgress);
            setMilestoneList(toCopy.ccaMilestoneList);
            setCcaCurrentProgress(toCopy.ccaCurrentProgress);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(ccaName, ccaType, equipmentList, ccaProgress, ccaMilestoneList,
                    ccaCurrentProgress);
        }

        public void setCcaName(CcaName ccaName) {
            this.ccaName = ccaName;
        }

        public Optional<CcaName> getCcaName() {
            return Optional.ofNullable(ccaName);
        }

        public void setCcaType(CcaType ccaType) {
            this.ccaType = ccaType;
        }

        public Optional<CcaType> getCcaType() {
            return Optional.ofNullable(ccaType);
        }

        public void setEquipmentList(EquipmentList equipmentList) {
            this.equipmentList = equipmentList;
        }

        public Optional<EquipmentList> getEquipmentList() {
            return Optional.ofNullable(equipmentList);
        }

        public void setMilestoneList(CcaMilestoneList ccaMilestones) {
            this.ccaMilestoneList = ccaMilestones;
        }

        public Optional<CcaMilestoneList> getCcaMilestoneList() {
            return Optional.ofNullable(ccaMilestoneList);
        }

        public void setCcaProgress(CcaProgress ccaProgress) {
            this.ccaProgress = ccaProgress;
        }

        public Optional<CcaProgress> getCcaProgress() {
            return Optional.ofNullable(ccaProgress);
        }

        public void setCcaCurrentProgress(CcaCurrentProgress ccaCurrentProgress) {
            this.ccaCurrentProgress = ccaCurrentProgress;
        }

        public Optional<CcaCurrentProgress> getCcaCurrentProgress() {
            return Optional.ofNullable(ccaCurrentProgress);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCcaDescriptor)) {
                return false;
            }

            // state check
            EditCcaDescriptor e = (EditCcaDescriptor) other;

            return getCcaName().equals(e.getCcaName())
                    && getCcaType().equals(e.getCcaType())
                    && getEquipmentList().equals(e.getEquipmentList())
                    && getCcaProgress().equals(e.getCcaProgress());
        }
    }
}
