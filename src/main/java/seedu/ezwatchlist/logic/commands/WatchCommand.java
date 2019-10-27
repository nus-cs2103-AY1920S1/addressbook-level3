package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_EPISODES;
import static seedu.ezwatchlist.model.Model.PREDICATE_SHOW_ALL_SHOWS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.util.CollectionUtil;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvSeason;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Marks an existing show in the watchlist as watched.
 */
public class WatchCommand extends Command {

    public static final String COMMAND_WORD = "watch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an existing show in the watchlist as watched "
            + "by the index number used in the displayed show list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_NUM_OF_EPISODES + "NUMBER OF EPISODES] "
            + "Example: " + COMMAND_WORD + " 1"
            + PREFIX_NUM_OF_EPISODES + "12 ";

    public static final String MESSAGE_WATCH_SHOW_SUCCESS = "Marked show as watched: %1$s";
    public static final String MESSAGE_UNWATCH_SHOW_SUCCESS = "Unmarked show as watched: %1$s";
    public static final String MESSAGE_MARK_EPISODES_SUCCESS = "Marked %1$s episodes as watched: %2$s";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in the watchlist.";
    public static final String MESSAGE_INVALID_EPISODE_NUMBER = "The provided number of episodes is too large, the are"
            + " only %1$s episodes in the chosen series.";

    private final Index index;
    private final WatchShowDescriptor watchShowDescriptor;
    private final boolean isToggle;

    /**
     * @param index of the show in the filtered show list to edit
     * @param watchShowDescriptor details to edit the show with
     */
    public WatchCommand(Index index, WatchShowDescriptor watchShowDescriptor, boolean isToggle) {
        requireNonNull(index);
        requireNonNull(watchShowDescriptor);

        this.index = index;
        this.watchShowDescriptor = new WatchShowDescriptor(watchShowDescriptor);
        this.isToggle = isToggle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Show> lastShownList = model.getFilteredShowList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
        }

        Show showToEdit = lastShownList.get(index.getZeroBased());
        Show editedShow = createEditedShow(showToEdit, watchShowDescriptor, isToggle);

        if (!showToEdit.isSameShow(editedShow) && model.hasShow(editedShow)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        model.setShow(showToEdit, editedShow);
        model.updateFilteredShowList(PREDICATE_SHOW_ALL_SHOWS);

        boolean isWatched = editedShow.isWatched().value;

        if (isToggle) {
            if (isWatched) {
                return new CommandResult(String.format(MESSAGE_WATCH_SHOW_SUCCESS, editedShow));
            } else {
                return new CommandResult(String.format(MESSAGE_UNWATCH_SHOW_SUCCESS, editedShow));
            }
        } else {
            return new CommandResult(String.format(
                    MESSAGE_MARK_EPISODES_SUCCESS, editedShow.getNumOfEpisodesWatched(), editedShow));
        }
    }

    /**
     * Creates and returns a {@code Show} with the details of {@code showToEdit}
     * edited with {@code editShowDescriptor}.
     */
    private static Show createEditedShow(Show showToEdit, WatchShowDescriptor watchShowDescriptor, boolean isToggle)
            throws CommandException{
        assert showToEdit != null;

        Name name = showToEdit.getName();
        Date dateOfRelease = showToEdit.getDateOfRelease();
        Description description = showToEdit.getDescription();
        RunningTime runningTime = showToEdit.getRunningTime();
        Set<Actor> actors = showToEdit.getActors();
        Poster poster = showToEdit.getPoster();
        IsWatched updatedIsWatched;

        if (isToggle) {
            updatedIsWatched = new IsWatched(!showToEdit.isWatched().value);
        } else {
            updatedIsWatched = new IsWatched(showToEdit.isWatched().value);
        }

        if (showToEdit.getType().equals("Movie")) {
            updatedIsWatched = new IsWatched(!showToEdit.isWatched().value);
            Movie editedShow = new Movie(name, description, updatedIsWatched,
                    dateOfRelease, runningTime, actors);
            editedShow.setPoster(poster);
            return editedShow;
        } else { //showToEdit.type.equals("Tv show")
            int totalNumOfEpisodes = showToEdit.getTotalNumOfEpisodes();
            List<TvSeason> seasons = showToEdit.getTvSeasons();

            int updatedNumOfEpisodesWatched = watchShowDescriptor.getNumOfEpisodesWatched()
                    .orElse(showToEdit.getNumOfEpisodesWatched());

            if (updatedNumOfEpisodesWatched > totalNumOfEpisodes) {
                throw new CommandException(String.format(MESSAGE_INVALID_EPISODE_NUMBER, totalNumOfEpisodes));
            }

            if (isToggle) {
                if (updatedIsWatched.value) {
                    updatedNumOfEpisodesWatched = totalNumOfEpisodes;
                } else {
                    updatedNumOfEpisodesWatched = 0;
                }
            } else {
                if (updatedNumOfEpisodesWatched == totalNumOfEpisodes) {
                    updatedIsWatched = new IsWatched(true);
                } else {
                    updatedIsWatched = new IsWatched(false);
                }
            }

            TvShow editedShow = new TvShow(name, description, updatedIsWatched,
                    dateOfRelease, runningTime, actors,
                    updatedNumOfEpisodesWatched, totalNumOfEpisodes, seasons);
            editedShow.setPoster(poster);
            return editedShow;
        }
    }

    /**
     * Stores the details to edit the show with. Each non-empty field value will replace the
     * corresponding field value of the show.
     */
    public static class WatchShowDescriptor {
        private Name name;
        private String type;
        private Date dateOfRelease;
        private IsWatched isWatched;
        private Description description;
        private RunningTime runningTime;
        private Set<Actor> actors;
        private Poster poster;
        private int numOfEpisodesWatched;
        private int totalNumOfEpisodes;
        private List<TvSeason> seasons;

        public WatchShowDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code actors} is used internally.
         */
        public WatchShowDescriptor(WatchShowDescriptor toCopy) {
            setName(toCopy.name);
            setType(toCopy.type);
            setDateOfRelease(toCopy.dateOfRelease);
            setIsWatched(toCopy.isWatched);
            setDescription(toCopy.description);
            setRunningTime(toCopy.runningTime);
            setActors(toCopy.actors);
            setPoster(toCopy.poster);
            setNumOfEpisodesWatched(toCopy.numOfEpisodesWatched);
            setTotalNumOfEpisodes(toCopy.totalNumOfEpisodes);
            setSeasons(toCopy.seasons);
        }

        /**
         * Returns true if isWatched is edited.
         */
        public boolean isAnyFieldWatched() {
            return CollectionUtil.isAnyNonNull(isWatched);
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

        public Optional<Integer> getNumOfEpisodesWatched() {
            return Optional.ofNullable(numOfEpisodesWatched);
        }

        public void setTotalNumOfEpisodes(int totalNumOfEpisodes) {
            this.totalNumOfEpisodes = totalNumOfEpisodes;
        }

        public Optional<Integer> getTotalNumOfEpisodes() {
            return Optional.ofNullable(totalNumOfEpisodes);
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
            if (!(other instanceof WatchShowDescriptor)) {
                return false;
            }

            // state check
            WatchShowDescriptor e = (WatchShowDescriptor) other;

            return getName().equals(e.getName())
                    && getType().equals(e.getType())
                    && getDateOfRelease().equals(e.getDateOfRelease())
                    && getIsWatched().equals(e.getIsWatched())
                    && getDescription().equals(e.getDescription())
                    && getRunningTime().equals(e.getRunningTime())
                    && getActors().equals(e.getActors())
                    && getPoster().equals(e.getPoster());
        }
    }
}
