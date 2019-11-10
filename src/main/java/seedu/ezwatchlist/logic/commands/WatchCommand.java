package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_EPISODES;
import static seedu.ezwatchlist.model.Model.PREDICATE_ALL_SHOWS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
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
 * Marks an existing show in the watchlist as watched.
 */
public class WatchCommand extends Command {

    public static final String COMMAND_WORD = "watch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an existing show in the watchlist as watched "
            + "by the index number used in the displayed show list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_NUM_OF_EPISODES + "NUMBER OF EPISODES] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NUM_OF_EPISODES + "12 ";

    public static final String MESSAGE_WATCH_SHOW_SUCCESS = "Marked show as watched: %1$s";
    public static final String MESSAGE_UNWATCH_SHOW_SUCCESS = "Unmarked show as watched: %1$s";
    public static final String MESSAGE_MARK_EPISODES_SUCCESS = "Marked %1$s episodes as watched: %2$s";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in the watchlist.";
    public static final String MESSAGE_EDITING_MOVIE_EPISODES_OR_SEASONS = "Movies do not have episodes and seasons.";
    public static final String MESSAGE_INVALID_EPISODE_NUMBER = "The provided number of episodes is too large, there"
            + " are only %1$s episode(s) in %2$s.";
    public static final String MESSAGE_INVALID_SEASON_NUMBER = "The provided number of seasons is too large, there are"
            + " only %1$s season(s) in %2$s.";
    public static final String MESSAGE_INVALID_EPISODE_NUMBER_OF_SEASON = "Season %1$s of %2$s only has"
            + " %3$s episode(s).";

    private final Index index;
    private final WatchShowDescriptor watchShowDescriptor;
    private final boolean isToggle;
    private final boolean seasonsArePresent;
    private final boolean episodesArePresent;

    /**
     * @param index of the show in the filtered show list to edit
     * @param watchShowDescriptor details to edit the show with
     */
    public WatchCommand(Index index, WatchShowDescriptor watchShowDescriptor,
                        boolean seasonsArePresent, boolean episodesArePresent) {
        requireNonNull(index);
        requireNonNull(watchShowDescriptor);
        requireNonNull(seasonsArePresent);
        requireNonNull(episodesArePresent);

        this.index = index;
        this.watchShowDescriptor = new WatchShowDescriptor(watchShowDescriptor);
        this.seasonsArePresent = seasonsArePresent;
        this.episodesArePresent = episodesArePresent;
        this.isToggle = (!seasonsArePresent && !episodesArePresent);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Show> lastShownList = model.getFilteredShowList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
        }

        Show showToEdit = lastShownList.get(index.getZeroBased());
        Show editedShow = createEditedShow(showToEdit, watchShowDescriptor, isToggle,
                seasonsArePresent, episodesArePresent);

        if (!showToEdit.isSameShow(editedShow) && model.hasShow(editedShow)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        if (showToEdit.getType().equals("Movie") && !isToggle) {
            throw new CommandException(MESSAGE_EDITING_MOVIE_EPISODES_OR_SEASONS);
        }

        model.setShow(showToEdit, editedShow);
        model.updateFilteredShowList(PREDICATE_ALL_SHOWS);

        boolean isWatched = editedShow.isWatched().value;

        if (editedShow.getType().equals("Movie") || isToggle) {
            if (isWatched) {
                return new CommandResult(String.format(MESSAGE_WATCH_SHOW_SUCCESS, editedShow), false);
            } else {
                return new CommandResult(String.format(MESSAGE_UNWATCH_SHOW_SUCCESS, editedShow), false);
            }
        } else {
            return new CommandResult(String.format(
                    MESSAGE_MARK_EPISODES_SUCCESS, editedShow.getNumOfEpisodesWatched(), editedShow), false);
        }
    }

    /**
     * Creates and returns a {@code Show} with the details of {@code showToEdit}
     * edited with {@code editShowDescriptor}.
     */
    private Show createEditedShow(Show showToEdit, WatchShowDescriptor watchShowDescriptor, boolean isToggle,
                                  boolean seasonsArePresent, boolean episodesArePresent) throws CommandException {
        assert showToEdit != null;

        Name name = showToEdit.getName();
        Date dateOfRelease = showToEdit.getDateOfRelease();
        Description description = showToEdit.getDescription();
        RunningTime runningTime = showToEdit.getRunningTime();
        Set<Actor> actors = showToEdit.getActors();
        Poster poster = showToEdit.getPoster();
        int totalNumOfEpisodes = showToEdit.getTotalNumOfEpisodes();
        List<TvSeason> seasons = showToEdit.getTvSeasons();
        Set<Genre> genres = showToEdit.getGenres();

        if (showToEdit.getType().equals("Movie")) {

            IsWatched updatedIsWatched = new IsWatched(Boolean.toString(!showToEdit.isWatched().value));
            Movie editedShow = new Movie(name, description, updatedIsWatched, dateOfRelease, runningTime, actors);
            editedShow.setPoster(poster);
            editedShow.addGenres(genres);

            return editedShow;
        } else { // show is a tv show

            int numOfEpisodesWatched = watchShowDescriptor.getNumOfEpisodesWatched();
            int numOfSeasonsWatched = watchShowDescriptor.getNumOfSeasonsWatched();

            IsWatched updatedIsWatched = showToEdit.isWatched();

            if (isToggle) {
                updatedIsWatched = new IsWatched(Boolean.toString(!showToEdit.isWatched().value));
            }

            checkValidityOfArguments(showToEdit, numOfEpisodesWatched, numOfSeasonsWatched);

            if (seasonsArePresent && episodesArePresent) {
                numOfEpisodesWatched = calcEpisodesWatched(showToEdit, numOfSeasonsWatched, numOfEpisodesWatched);
            } else if (seasonsArePresent) {
                numOfEpisodesWatched = calcEpisodesWatched(showToEdit, numOfSeasonsWatched);
            }

            checkIfValidNumOfEpisodesWatched(showToEdit, numOfEpisodesWatched, totalNumOfEpisodes);

            if (isToggle) {
                if (updatedIsWatched.value) {
                    numOfEpisodesWatched = totalNumOfEpisodes;
                } else {
                    numOfEpisodesWatched = 0;
                }
            } else {
                if (numOfEpisodesWatched == totalNumOfEpisodes) {
                    updatedIsWatched = new IsWatched("true");
                } else {
                    updatedIsWatched = new IsWatched("false");
                }
            }

            TvShow editedShow = new TvShow(name, description, updatedIsWatched, dateOfRelease, runningTime,
                    actors, numOfEpisodesWatched, totalNumOfEpisodes, seasons);
            editedShow.setPoster(poster);
            editedShow.addGenres(genres);

            return editedShow;
        }
    }

    /**
     * Checks the validity of {@code numOfEpisodesWatched} and {@code numOfSeasonsWatched} user inputs.
     * @param showToEdit show that is being edited.
     * @param numOfEpisodesWatched number of episodes given by user.
     * @param numOfSeasonsWatched number of seasons given by user.
     * @throws CommandException if there is any invalid input.
     */
    private void checkValidityOfArguments(Show showToEdit, int numOfEpisodesWatched, int numOfSeasonsWatched)
            throws CommandException {
        if (seasonsArePresent && !isValidSeasonNumber(showToEdit, numOfSeasonsWatched)) {
            throw new CommandException(String.format(MESSAGE_INVALID_SEASON_NUMBER,
                    showToEdit.getNumOfSeasons(), showToEdit.getName()));
        }
        if (seasonsArePresent && episodesArePresent
                && !isValidEpisodeNumberOfSeason(showToEdit, numOfEpisodesWatched, numOfSeasonsWatched)) {
            throw new CommandException(String.format(MESSAGE_INVALID_EPISODE_NUMBER_OF_SEASON,
                    showToEdit.getTvSeasons().get(numOfSeasonsWatched - 1).getSeasonNum(),
                    showToEdit.getName(),
                    showToEdit.getNumOfEpisodesOfSeason(numOfSeasonsWatched)));
        }
    }

    /**
     * Checks the validity of {@code numOfEpisodesWatched} and {@code numOfSeasonsWatched} user inputs.
     * @param showToEdit show that is being edited.
     * @param numOfEpisodesWatched number of episodes given by user.
     * @param totalNumOfEpisodes total number of episodes.
     * @throws CommandException if there is any invalid input.
     */
    private void checkIfValidNumOfEpisodesWatched(Show showToEdit, int numOfEpisodesWatched, int totalNumOfEpisodes)
            throws CommandException {
        if (numOfEpisodesWatched > totalNumOfEpisodes) {
            throw new CommandException(String.format(MESSAGE_INVALID_EPISODE_NUMBER,
                    totalNumOfEpisodes, showToEdit.getName()));
        }
    }

    /**
     * Calculates the total number of episodes watched given and the season
     * number and number of episodes watched in that particular season.
     * @param show The show to be edited.
     * @param numOfSeasons the number of seasons provided by the user.
     * @param numOfEpisodes the number of episodes of the season provided by the user.
     * @return the total number of episodes watched.
     */
    private int calcEpisodesWatched(Show show, int numOfSeasons, int numOfEpisodes) {
        int numOfEpisodesWatched = 0;
        for (int seasonNum = 1; seasonNum < numOfSeasons; seasonNum++) {
            numOfEpisodesWatched += show.getTvSeasons().get(seasonNum - 1).getTotalNumOfEpisodes();
        }
        numOfEpisodesWatched += numOfEpisodes;
        return numOfEpisodesWatched;
    }

    /**
     * Calculates the total number of episodes watched given the number of seasons watched.
     * @param show The show to be edited.
     * @param numOfSeasons the number of seasons provided by the user.
     * @return the total number of episodes watched.
     */
    private int calcEpisodesWatched(Show show, int numOfSeasons) {
        int numOfEpisodesWatched = 0;
        for (int seasonNum = 1; seasonNum <= numOfSeasons; seasonNum++) {
            numOfEpisodesWatched += show.getNumOfEpisodesOfSeason(seasonNum);
        }
        return numOfEpisodesWatched;
    }

    private boolean isValidSeasonNumber(Show showToEdit, int seasonNum) {
        return showToEdit.getType().equals("Tv Show")
                && (seasonNum <= showToEdit.getNumOfSeasons());
    }

    private boolean isValidEpisodeNumberOfSeason(Show showToEdit, int episodeNum, int seasonNum) {
        return showToEdit.getType().equals("Tv Show")
                && episodeNum <= showToEdit.getTvSeasons().get(seasonNum - 1).getTotalNumOfEpisodes();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WatchCommand)) {
            return false;
        }

        // state check
        WatchCommand e = (WatchCommand) other;
        return index.equals(e.index)
                && watchShowDescriptor.equals(e.watchShowDescriptor);
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
        private Set<Genre> genres;
        private int numOfEpisodesWatched;
        private int numOfSeasonsWatched;
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
            setGenres(toCopy.genres);
            setNumOfEpisodesWatched(toCopy.numOfEpisodesWatched);
            setNumOfSeasonsWatched(toCopy.numOfSeasonsWatched);
            setTotalNumOfEpisodes(toCopy.totalNumOfEpisodes);
            setSeasons(toCopy.seasons);
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

        public void setNumOfEpisodesWatched(int numOfEpisodesWatched) {
            this.numOfEpisodesWatched = numOfEpisodesWatched;
        }

        public int getNumOfEpisodesWatched() {
            return numOfEpisodesWatched;
        }

        public void setTotalNumOfEpisodes(int totalNumOfEpisodes) {
            this.totalNumOfEpisodes = totalNumOfEpisodes;
        }

        public void setNumOfSeasonsWatched(int numOfSeasonsWatched) {
            this.numOfSeasonsWatched = numOfSeasonsWatched;
        }

        public int getNumOfSeasonsWatched() {
            return numOfSeasonsWatched;
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
            return getNumOfEpisodesWatched() == e.getNumOfEpisodesWatched()
                    && getNumOfSeasonsWatched() == e.getNumOfSeasonsWatched();
        }
    }
}
