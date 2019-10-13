package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.model.Model.PREDICATE_SHOW_ALL_SHOWS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.util.CollectionUtil;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Edits the details of an existing show in the watchlist.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the show identified "
            + "by the index number used in the displayed show list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE_OF_RELEASE + "DATE OF RELEASE] "
            + "[" + PREFIX_IS_WATCHED + "WATCHED?] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_RUNNING_TIME + "RUNNING TIME] "
            + "[" + PREFIX_ACTOR + "ACTOR]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Joker "
            + PREFIX_DATE_OF_RELEASE + "3 October 2019";

    public static final String MESSAGE_EDIT_SHOW_SUCCESS = "Edited Show: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in the watchlist.";

    private final Index index;
    private final EditShowDescriptor editShowDescriptor;

    /**
     * @param index of the show in the filtered show list to edit
     * @param editShowDescriptor details to edit the show with
     */
    public EditCommand(Index index, EditShowDescriptor editShowDescriptor) {
        requireNonNull(index);
        requireNonNull(editShowDescriptor);

        this.index = index;
        this.editShowDescriptor = new EditShowDescriptor(editShowDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Show> lastShownList = model.getFilteredShowList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
        }

        Show showToEdit = lastShownList.get(index.getZeroBased());
        Show editedShow = createEditedShow(showToEdit, editShowDescriptor);

        if (!showToEdit.isSameShow(editedShow) && model.hasShow(editedShow)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        model.setShow(showToEdit, editedShow);
        model.updateFilteredShowList(PREDICATE_SHOW_ALL_SHOWS);
        return new CommandResult(String.format(MESSAGE_EDIT_SHOW_SUCCESS, editedShow));
    }

    /**
     * Creates and returns a {@code Show} with the details of {@code showToEdit}
     * edited with {@code editShowDescriptor}.
     */
    private static Show createEditedShow(Show showToEdit, EditShowDescriptor editShowDescriptor) {
        assert showToEdit != null;

        Name updatedName = editShowDescriptor.getName().orElse(showToEdit.getName());
        Date updatedDateOfRelease = editShowDescriptor.getDateOfRelease().orElse(showToEdit.getDateOfRelease());
        IsWatched updatedIsWatched = editShowDescriptor.getIsWatched().orElse(showToEdit.isWatched());
        Description updatedDescription = editShowDescriptor.getDescription().orElse(showToEdit.getDescription());
        RunningTime updatedRunningTime = editShowDescriptor.getRunningTime().orElse(showToEdit.getRunningTime());
        Set<Actor> updatedActors = editShowDescriptor.getActors().orElse(showToEdit.getActors());

        return new Show(updatedName, updatedDescription, updatedIsWatched,
                updatedDateOfRelease, updatedRunningTime, updatedActors);
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
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editShowDescriptor.equals(e.editShowDescriptor);
    }

    /**
     * Stores the details to edit the show with. Each non-empty field value will replace the
     * corresponding field value of the show.
     */
    public static class EditShowDescriptor {
        private Name name;
        private Date dateOfRelease;
        private IsWatched isWatched;
        private Description description;
        private RunningTime runningTime;
        private Set<Actor> actors = new HashSet<>();

        public EditShowDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code actors} is used internally.
         */
        public EditShowDescriptor(EditShowDescriptor toCopy) {
            setName(toCopy.name);
            setDateOfRelease(toCopy.dateOfRelease);
            setIsWatched(toCopy.isWatched);
            setDescription(toCopy.description);
            setRunningTime(toCopy.runningTime);
            setActors(toCopy.actors);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, dateOfRelease, isWatched, description, runningTime, actors);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDateOfRelease(Date dateOfRelease) {
            this.dateOfRelease = dateOfRelease;
        }

        public Optional<Date> getDateOfRelease() {
            return Optional.ofNullable(dateOfRelease);
        }

        public void setIsWatched(IsWatched isWatched) {
            this.isWatched = isWatched;
        }

        public Optional<IsWatched> getIsWatched() {
            return Optional.ofNullable(isWatched);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setRunningTime(RunningTime runningTime) {
            this.runningTime = runningTime;
        }

        public Optional<RunningTime> getRunningTime() {
            return Optional.ofNullable(runningTime);
        }

        /**
         * Sets {@code actors} to this object's {@code actors}.
         * A defensive copy of {@code actors} is used internally.
         */
        public void setActors(Set<Actor> actors) {
            this.actors = (actors != null) ? new HashSet<>(actors) : null;
        }

        /**
         * Returns an unmodifiable actor set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code actor} is null.
         */
        public Optional<Set<Actor>> getActors() {
            return (actors != null) ? Optional.of(Collections.unmodifiableSet(actors)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditShowDescriptor)) {
                return false;
            }

            // state check
            EditShowDescriptor e = (EditShowDescriptor) other;

            return getName().equals(e.getName())
                    && getDateOfRelease().equals(e.getDateOfRelease())
                    && getIsWatched().equals(e.getIsWatched())
                    && getDescription().equals(e.getDescription())
                    && getRunningTime().equals(e.getRunningTime())
                    && getActors().equals(e.getActors());

        }
    }
}
