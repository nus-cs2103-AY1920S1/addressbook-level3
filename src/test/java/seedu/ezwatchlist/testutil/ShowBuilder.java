package seedu.ezwatchlist.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import seedu.ezwatchlist.model.util.SampleDataUtil;

/**
 * A utility class to help with building Show objects.
 */
public class ShowBuilder {

    public static final String DEFAULT_NAME = "Inception";
    public static final String DEFAULT_TYPE = "movie";

    public static final String DEFAULT_DATE = "16/07/2010";
    public static final String DEFAULT_ISWATCHED = "false";
    public static final String DEFAULT_DESCRIPTION = "A thief who steals corporate secrets through the use "
            + " of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.";
    public static final int DEFAULT_RUNNINGTIME = 148;
    public static final int DEFAULT_NUM_OF_EPISODES_WATCHED = 0;
    public static final int DEFAULT_TOTAL_NUM_OF_EPISODES = 0;
    public static final String DEFAULT_POSTER = "/images/poster-placeholder.png";

    private Name name;
    private String type;
    private Description description;
    private IsWatched isWatched;
    private RunningTime runningTime;
    private Set<Actor> actors;
    private Date dateOfRelease;
    private Set<Genre> genres;
    private Poster poster;
    private int numOfEpisodesWatched;
    private int totalNumOfEpisodes;
    private List<TvSeason> tvSeasons;

    public ShowBuilder() {
        name = new Name(DEFAULT_NAME);
        type = DEFAULT_TYPE;
        description = new Description(DEFAULT_DESCRIPTION);
        isWatched = new IsWatched(DEFAULT_ISWATCHED);
        dateOfRelease = new Date(DEFAULT_DATE);
        runningTime = new RunningTime(DEFAULT_RUNNINGTIME);
        actors = new HashSet<>();
        genres = new HashSet<>();
        poster = new Poster(DEFAULT_POSTER);
        numOfEpisodesWatched = DEFAULT_NUM_OF_EPISODES_WATCHED;
        totalNumOfEpisodes = DEFAULT_TOTAL_NUM_OF_EPISODES;
        tvSeasons = new ArrayList<>();
    }

    /**
     * Initializes the ShowBuilder with the data of {@code showToCopy}.
     */
    public ShowBuilder(Show showToCopy) {
        name = showToCopy.getName();
        type = showToCopy.getType();
        description = showToCopy.getDescription();
        isWatched = showToCopy.isWatched();
        dateOfRelease = showToCopy.getDateOfRelease();
        runningTime = showToCopy.getRunningTime();
        actors = new HashSet<>(showToCopy.getActors());
        genres = new HashSet<>(showToCopy.getGenres());
        poster = showToCopy.getPoster();
        if (showToCopy.getType().equals("Tv Show")) {
            numOfEpisodesWatched = showToCopy.getNumOfEpisodesWatched();
            totalNumOfEpisodes = showToCopy.getTotalNumOfEpisodes();
            tvSeasons = new ArrayList<>(showToCopy.getTvSeasons());
        }
    }

    /**
     * Sets the {@code Name} of the {@code Show} that we are building.
     */
    public ShowBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code type} of the {@code Show} that we are building.
     */
    public ShowBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Parses the {@code Actors} into a {@code Set<Actors>} and set it to the {@code Actors} that we are building.
     */
    public ShowBuilder withActors(String ... actors) {
        this.actors = SampleDataUtil.getActorSet(actors);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Show} that we are building.
     */
    public ShowBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code IsWatched} of the {@code Show} that we are building.
     */
    public ShowBuilder withIsWatched(String isWatched) {
        this.isWatched = new IsWatched(isWatched);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Show} that we are building.
     */
    public ShowBuilder withDateOfRelease(String date) {
        this.dateOfRelease = new Date(date);
        return this;
    }

    /**
     * Sets the {@code runningTime} of the {@code Show} that we are building.
     */
    public ShowBuilder withRunningTime(int runningTime) {
        this.runningTime = new RunningTime(runningTime);
        return this;
    }

    /**
     * Sets the {@code poster} of the {@code Show} that we are building.
     */
    public ShowBuilder withPoster(String poster) {
        this.poster = new Poster(poster);
        return this;
    }

    /**
     * Parses the {@code Genres} into a {@code Set<Genres>} and set it to the {@code Genres} that we are building.
     */
    public ShowBuilder withGenres(String ... genres) {
        this.genres = SampleDataUtil.getGenreSet(genres);
        return this;
    }

    /**
     * Sets the {@code numOfEpisodesWatched} of the {@code Show} that we are building.
     */
    public ShowBuilder withNumOfEpisodesWatched(int numOfEpisodesWatched) {
        this.numOfEpisodesWatched = numOfEpisodesWatched;
        return this;
    }

    /**
     * Sets the {@code totalNumOfEpisodes} of the {@code Show} that we are building.
     */
    public ShowBuilder withTotalNumOfEpisodes(int totalNumOfEpisodes) {
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        return this;
    }

    /**
     * Parses the {@code Seasons} into a {@code Set<Seasons>} and set it to the {@code Seasons} that we are building.
     */
    public ShowBuilder withSeasons(List<TvSeason> seasons) {
        this.tvSeasons = seasons;
        return this;
    }

    /**
     * Builds the show.
     * @return show.
     */
    public Show build() {
        if (type.toLowerCase().equals("movie")) {
            return new Movie(name, description, isWatched, dateOfRelease, runningTime, actors);
        } else {
            return new TvShow(name, description, isWatched, dateOfRelease, runningTime, actors,
                    numOfEpisodesWatched , totalNumOfEpisodes, tvSeasons);
        }
    }

}
