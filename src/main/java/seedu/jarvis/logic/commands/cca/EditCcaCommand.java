package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;

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

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCcaCommand extends Command {

    public static final String COMMAND_WORD = "edit-cca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Cca identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CCA_NAME + "CCA NAME] "
            + "[" + PREFIX_CCA_TYPE + "CCA TYPE] "
            + "[" + PREFIX_EQUIPMENT_NAME + "EQUIPMENT]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CCA_TYPE + "sport "
            + PREFIX_EQUIPMENT_NAME + "tennis racket";

    public static final String MESSAGE_EDIT_CCA_SUCCESS = "Edited Cca: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CCA = "This Cca already exists in the CcaTracker.";

    public static final String MESSAGE_INVERSE_SUCCESS_EDIT = "Reverted edit.";

    public static final String MESSAGE_INVERSE_PERSON_NOT_FOUND =
            "Unable to edit cca, cca not found in the address book.";

    public static final String MESSAGE_INVERSE_CONFLICT_WITH_EXISTING_CCA =
            "There is a conflict in reverting edits made to cca as there is an existing cca with similar details";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;
    private final EditCcaDescriptor editCcaDescriptor;

    private Cca originalCca;
    private Cca editedCca;

    /**
     * @param index of the {@code Cca} in the {@code CcaList} to edit
     * @param editCcaDescriptor details to edit the {@code cca} with
     */
    public EditCcaCommand(Index index, EditCcaDescriptor editCcaDescriptor) {
        requireNonNull(index);
        requireNonNull(editCcaDescriptor);

        this.targetIndex = index;
        this.editCcaDescriptor = new EditCcaDescriptor(editCcaDescriptor);
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
        return false;
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
        if (!originalCca.isSameCca(createdEditedCca) && model.hasCca(createdEditedCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }
        editedCca = createdEditedCca;

        model.updateCca(originalCca, createdEditedCca);
        //        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_CCA_SUCCESS, editedCca));
    }

    /**
     * Reverts back the edits made to {@code Cca} in CcaTracker by the command's execution.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful revert of {@code Cca} if the revert is possible, or a
     * {@code CommandResult} that the edited cca is no longer in the CcaTracker, or a {@code CommandResult} that
     * there will be a conflict with an existing {@code Cca} in the CcaTracker if the revert is made.
     * @throws CommandException If the cca to be reverted is not found in the CcaTracker, or if reverting the edits
     * to the cca will result in a conflict with another person in the CcaTracker.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    /**
     * Creates and returns a {@code Cca} with the details of {@code ccaToEdit}
     * edited with {@code editCcaDescriptor}.
     */
    private static Cca createEditedCca(Cca ccaToEdit, EditCcaDescriptor editCcaDescriptor) {
        assert ccaToEdit != null;

        CcaName updatedName = editCcaDescriptor.getCcaName().orElse(ccaToEdit.getName());
        CcaType updatedCcaType = editCcaDescriptor.getCcaType().orElse(ccaToEdit.getCcaType());
        EquipmentList updatedEquipmentList = editCcaDescriptor.getEquipmentList().orElse(ccaToEdit.getEquipmentList());

        return new Cca(updatedName, updatedCcaType, updatedEquipmentList);
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
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditCcaDescriptor {
        private CcaName ccaName;
        private CcaType ccaType;
        private EquipmentList equipmentList;

        public EditCcaDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditCcaDescriptor(EditCcaDescriptor toCopy) {
            setCcaName(toCopy.ccaName);
            setCcaType(toCopy.ccaType);
            setEquipmentList(toCopy.equipmentList);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(ccaName, ccaType, equipmentList);
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
                    && getEquipmentList().equals(e.getEquipmentList());
        }
    }
}
