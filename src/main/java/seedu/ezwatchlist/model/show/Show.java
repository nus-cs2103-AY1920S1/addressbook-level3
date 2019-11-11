package seedu.ezwatchlist.model.show;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.ezwatchlist.commons.util.CollectionUtil;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Represents a Show in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Show {

    private String type;

    //identity fields
    private final Name name;
    private final Date dateOfRelease;
    private final IsWatched isWatched;

    //data fields
    private final Description description;
    private final RunningTime runningTime;
    private final Set<Actor> actors = new HashSet<>();
    private Poster poster;
    private final Set<Genre> genres = new HashSet<>();

    public Show(Name name, Description description, IsWatched isWatched, Date dateOfRelease,
                RunningTime runningTime, Set<Actor> actors) {
        this.poster = new Poster();
        CollectionUtil.requireAllNonNull(name, description, isWatched, dateOfRelease, runningTime, actors);
        this.name = name;
        this.description = description;
        this.isWatched = isWatched;
        this.dateOfRelease = dateOfRelease;
        this.runningTime = runningTime;
        this.actors.addAll(actors);
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public Poster getPoster() {
        return poster;
    }

    public Name getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Date getDateOfRelease() {
        return dateOfRelease;
    }

    public IsWatched isWatched() {
        return isWatched;
    }

    public Description getDescription() {
        return description;
    }

    public RunningTime getRunningTime() {
        return runningTime;
    }

    public abstract int getNumOfEpisodesWatched();

    public abstract int getTotalNumOfEpisodes();

    public abstract List<TvSeason> getTvSeasons();

    public abstract int getLastWatchedSeasonNum();

    public abstract int getLastWatchedSeasonEpisode();

    public abstract int getNumOfSeasons();

    public abstract int getNumOfEpisodesOfSeason(int seasonNum);

    /**
     * Returns an immutable actor set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Actor> getActors() {
        return Collections.unmodifiableSet(actors);
    }

    /**
     * Returns an immutable genre set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public void addGenres(Set<Genre> genres) {
        this.genres.addAll(genres);
    }

    /**
     * Returns true if both Shows of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two shows.
     */
    public boolean isSameShow(Show otherShow) {
        if (otherShow == this) {
            return true;
        }

        return otherShow != null
                && otherShow.getName().equals(getName())
                && otherShow.getType().equals(getType())
                && (otherShow.getDateOfRelease().equals(getDateOfRelease()) || otherShow.isWatched() == (isWatched()));
    }

    /**
     * Return true if the other show has name similar to the current show.
     * @param showToBeSearched Show to be compare to this show.
     * @return True if the other show has name similar to the current show.
     */
    public boolean hasNameWithWord(Show showToBeSearched) {
        if (isSameName(showToBeSearched)) {
            return true;
        } else {
            return this.getName().getName().toLowerCase().contains(showToBeSearched.getName().getName().toLowerCase());
        }
    }

    /**
     * Checks if two shows have the same name.
     * @param otherShow other show to be checked.
     * @return boolean whether the 2 shows are the same.
     */
    public boolean isSameName(Show otherShow) {
        if (otherShow == this) {
            return true;
        }
        return otherShow != null && otherShow.getName().equals(getName());
    }

    /**
     * Return true if the other show has actor similar to the current show.
     * @param showToBeSearched Show to be compare to this show.
     * @return True if the other show has name similar to the current show.
     */
    public boolean hasActorWithName(Show showToBeSearched) {
        Set<Actor> actorSearchedSet = showToBeSearched.getActors();
        for (Actor actorSearched : actorSearchedSet) {
            Set<Actor> actorDataSet = this.getActors();
            for (Actor actorData : actorDataSet) {
                if (actorData.getActorName().toLowerCase().contains(actorSearched.getActorName().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * tba
     * @param showToBeSearched tba
     * @return tba
     */
    public boolean hasGenre(Show showToBeSearched) {
        Set<Genre> genreSearchedSet = showToBeSearched.getGenres();
        for (Genre genreSearched : genreSearchedSet) {
            Set<Genre> genreDataSet = this.getGenres();
            for (Genre genreData : genreDataSet) {
                if (genreData.getGenreName().toLowerCase().contains(genreSearched.getGenreName().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if both shows have the same identity and data fields.
     * This defines a stronger notion of equality between two shows.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Show)) {
            return false;
        }

        Show otherShow = (Show) other;
        return otherShow.getName().equals(getName())
                && otherShow.getType().equals(getType())
                && otherShow.getDateOfRelease().equals(getDateOfRelease())
                && otherShow.getDescription().equals(getDescription())
                && otherShow.getRunningTime().equals(getRunningTime())
                && otherShow.getActors().equals(getActors())
                && otherShow.getGenres().equals(getGenres());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, type, dateOfRelease, isWatched, description, runningTime, actors);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date of Release: ")
                .append(getDateOfRelease())
                .append(" Description: ")
                .append(getDescription())
                .append(" Running Time: ")
                .append(getRunningTime())
                .append(" Watched: ")
                .append(isWatched().toString())
                .append(" Actors: ");
        getActors().forEach(builder::append);
        return builder.toString();
    }
}
