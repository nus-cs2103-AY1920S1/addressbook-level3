package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;

import static seedu.ezwatchlist.model.Model.PREDICATE_ALL_SHOWS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.commons.util.CollectionUtil;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvSeason;
import seedu.ezwatchlist.model.show.TvShow;

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
            + "[" + PREFIX_IS_WATCHED + "WATCHED ('true' or 'false')] "
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
        model.updateFilteredShowList(PREDICATE_ALL_SHOWS);
        return new CommandResult(String.format(MESSAGE_EDIT_SHOW_SUCCESS, editedShow), true);
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
        Poster updatedPoster = editShowDescriptor.getPoster().orElse(showToEdit.getPoster());
        Set<Genre> updatedGenres = editShowDescriptor.getGenres().orElse(showToEdit.getGenres());

        if (showToEdit.getType().equals("Movie")) {
            Movie editedShow = new Movie(updatedName, updatedDescription, updatedIsWatched,
                    updatedDateOfRelease, updatedRunningTime, updatedActors);
            editedShow.setPoster(updatedPoster);
            return editedShow;
        } else { //Tv show
            int updatedNumberOfEpisodesWatched = showToEdit.getNumOfEpisodesWatched();
            int updatedTotalNumOfEpisodes = showToEdit.getTotalNumOfEpisodes();
            List<TvSeason> updatedSeasons = editShowDescriptor.getSeasons().orElse(showToEdit.getTvSeasons());

            TvShow editedShow = new TvShow(updatedName, updatedDescription, updatedIsWatched,
                    updatedDateOfRelease, updatedRunningTime, updatedActors, updatedNumberOfEpisodesWatched,
                    updatedTotalNumOfEpisodes, updatedSeasons);
            editedShow.setPoster(updatedPoster);
            editedShow.addGenres(updatedGenres);

            return editedShow;
        }
    }

    /**
     * createEditedShowTest is a public method to access the private createEditedShow.
     * This method is used to testing to retrieve information from private createEditedShow.
     * @param showToEdit
     * @param editShowDescriptor
     * @return
     */
    public Show createEditedShowTest(Show showToEdit, EditShowDescriptor editShowDescriptor) {
        return createEditedShow(showToEdit, editShowDescriptor);
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
        private String type;
        private Date dateOfRelease;
        private IsWatched isWatched;
        private Description description;
        private RunningTime runningTime;
        private Set<Actor> actors;
        private Poster poster;
        private Set<Genre> genres;
        private int numOfEpisodesWatched;
        private int totalNumOfEpisodes;
        private List<TvSeason> seasons;

        public EditShowDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code actors} is used internally.
         */
        public EditShowDescriptor(EditShowDescriptor toCopy) {
            setName(toCopy.name);
            setType(toCopy.type);
            setDateOfRelease(toCopy.dateOfRelease);
            setIsWatched(toCopy.isWatched);
            setDescription(toCopy.description);
            setRunningTime(toCopy.runningTime);
            setActors(toCopy.actors);
            setPoster(toCopy.poster);
            setGenres(toCopy.genres);
            setNumOfEpisodesWatched(toCopy.numOfEpisodesWatched);
            setTotalNumOfEpisodes(toCopy.totalNumOfEpisodes);
            setSeasons(toCopy.seasons);
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

        public void setType(String type) {
            this.type = type;
        }

        public Optional<String> getType() {
            return Optional.ofNullable(type);
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

        public void setPoster(Poster poster) {
            this.poster = poster;
        }

        public Optional<Poster> getPoster() {
            return Optional.ofNullable(poster);
        }

        public void setNumOfEpisodesWatched(int numOfEpisodesWatched) {
            this.numOfEpisodesWatched = numOfEpisodesWatched;
        }

        public void setTotalNumOfEpisodes(int totalNumOfEpisodes) {
            this.totalNumOfEpisodes = totalNumOfEpisodes;
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

        /**
         * Sets {@code genres} to this object's {@code genres}.
         * A defensive copy of {@code genres} is used internally.
         */
        public void setGenres(Set<Genre> genres) {
            this.genres = (genres != null) ? new HashSet<>(genres) : null;
        }

        /**
         * Returns an unmodifiable genre set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code genre} is null.
         */
        public Optional<Set<Genre>> getGenres() {
            return (genres != null) ? Optional.of(Collections.unmodifiableSet(genres)) : Optional.empty();
        }

        /**
         * Sets {@code seasons} to this object's {@code seasons}.
         * A defensive copy of {@code seasons} is used internally.
         */
        public void setSeasons(List<TvSeason> seasons) {
            this.seasons = (seasons != null) ? new ArrayList<>(seasons) : null;
        }

        /**
         * Returns an unmodifiable season set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code season} is null.
         */
        public Optional<List<TvSeason>> getSeasons() {
            return (seasons != null) ? Optional.of(Collections.unmodifiableList(seasons)) : Optional.empty();
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
