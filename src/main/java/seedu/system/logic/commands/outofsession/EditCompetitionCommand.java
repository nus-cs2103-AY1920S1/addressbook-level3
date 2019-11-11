package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_START_END_DATES;
import static seedu.system.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_COMPETITIONS;

import java.util.List;
import java.util.Optional;

import seedu.system.commons.core.Messages;
import seedu.system.commons.core.index.Index;
import seedu.system.commons.util.CollectionUtil;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;

/**
 * Edits the details of an existing competition in the system.
 */
public class EditCompetitionCommand extends Command {

    public static final String COMMAND_WORD = "editCompetition";
    public static final CommandType COMMAND_TYPE = CommandType.COMPETITION;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the competition identified "
            + "by the index number used in the displayed competition list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "COMPETITION NAME] "
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
    public EditCompetitionCommand(Index index, EditCompetitionDescriptor editCompetitionDescriptor) {
        requireNonNull(index);
        requireNonNull(editCompetitionDescriptor);

        this.index = index;
        this.editCompetitionDescriptor = new EditCompetitionDescriptor(editCompetitionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);
        List<Competition> lastShownList = model.getFilteredCompetitionList();

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

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
        return new CommandResult(String.format(MESSAGE_EDIT_COMPETITION_SUCCESS, editedComp), COMMAND_TYPE);
    }

    /**
     * Creates and returns a {@code Competition} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Competition createEditedComp(Competition compToEdit,
                                              EditCompetitionDescriptor editCompetitionDescriptor)
            throws ParseException {
        assert compToEdit != null;

        Name updatedName = editCompetitionDescriptor.getName().orElse(compToEdit.getName());
        CustomDate updatedStartDate = editCompetitionDescriptor.getStartDate().orElse(compToEdit.getStartDate());
        CustomDate updatedEndDate = editCompetitionDescriptor.getEndDate().orElse(compToEdit.getEndDate());
        System.out.println(updatedStartDate.toString());
        System.out.println(updatedEndDate.toString());
        if (!ParserUtil.isBefore(updatedStartDate, updatedEndDate)) {
            throw new ParseException(MESSAGE_INVALID_START_END_DATES);
        }
        return new Competition(updatedName, updatedStartDate, updatedEndDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonCommand)) {
            return false;
        }

        // state check
        EditCompetitionCommand e = (EditCompetitionCommand) other;
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
