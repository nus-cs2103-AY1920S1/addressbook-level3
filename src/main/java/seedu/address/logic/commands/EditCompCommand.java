package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPETITIONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.competition.Competition;
import seedu.address.model.person.CustomDate;
import seedu.address.model.person.Name;

/**
 * Edits the details of an existing competition in the address book.
 */
public class EditCompCommand extends Command {

    public static final String COMMAND_WORD = "editComp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the competition identified "
            + "by the index number used in the displayed competition list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_START_DATE + "START DATE OF COMPETITION] "
            + "[" + PREFIX_END_DATE + "END DATE OF COMPETITION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "IPF World's "
            + PREFIX_START_DATE + "12/02/1995"
            + PREFIX_END_DATE + "15/02/1995";

    public static final String MESSAGE_EDIT_COMPETITION_SUCCESS = "Edited Competition: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_COMPETITION = "This competition already exists in the address book.";

    private final Index index;
    private final EditCompetitionDescriptor editCompetitionDescriptor;

    /**
     * @param index of the competition in the filtered competition list to edit
     * @param editCompetitionDescriptor details to edit the competition with
     */
    public EditCompCommand(Index index, EditCompetitionDescriptor editCompetitionDescriptor) {
        requireNonNull(index);
        requireNonNull(editCompetitionDescriptor);

        this.index = index;
        this.editCompetitionDescriptor = new EditCompetitionDescriptor(editCompetitionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Competition> lastShownList = model.getFilteredCompetitionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPETITION_DISPLAYED_INDEX);
        }

        Competition compToEdit = lastShownList.get(index.getZeroBased());
        Competition editedComp = createEditedComp(compToEdit, editCompetitionDescriptor);

        if (!compToEdit.isSameElement(editedComp) && model.hasCompetition(editedComp)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPETITION);
        }

        model.setCompetition(compToEdit, editedComp);
        model.updateFilteredCompetitionList(PREDICATE_SHOW_ALL_COMPETITIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_COMPETITION_SUCCESS, editedComp));
    }

    /**
     * Creates and returns a {@code Competition} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Competition createEditedComp(Competition compToEdit,
                                              EditCompetitionDescriptor editCompetitionDescriptor) {
        assert compToEdit != null;

        Name updatedName = editCompetitionDescriptor.getName().orElse(compToEdit.getName());
        CustomDate updatedStartDate = editCompetitionDescriptor.getStartDate().orElse(compToEdit.getStartDate());
        CustomDate updatedEndDate = editCompetitionDescriptor.getEndDate().orElse(compToEdit.getEndDate());
        return new Competition(updatedName, updatedStartDate, updatedEndDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCompCommand e = (EditCompCommand) other;
        return index.equals(e.index)
                && editCompetitionDescriptor.equals(e.editCompetitionDescriptor);
    }

    /**
     * Stores the details to edit a competition with. Each non-empty field value will replace the
     * corresponding field value of the competition.
     */
    public static class EditCompetitionDescriptor {
        private Name name;
        private CustomDate startDate;
        private CustomDate endDate;

        public EditCompetitionDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditCompetitionDescriptor(EditCompetitionDescriptor toCopy) {
            setName(toCopy.name);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, startDate, endDate);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setStartDate(CustomDate startDate) {
            this.startDate = startDate;
        }

        public Optional<CustomDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(CustomDate endDate) {
            this.endDate = endDate;
        }

        public Optional<CustomDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCompetitionDescriptor)) {
                return false;
            }

            // state check
            EditCompetitionDescriptor e = (EditCompetitionDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate());
        }
    }

}
