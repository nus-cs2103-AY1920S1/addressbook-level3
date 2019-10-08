package seedu.address.model.show;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.actor.Actor;

/**
 * Represents a Show in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Show {

    //identity fields
    private final Name name;
    private final Date dateOfRelease;
    private final boolean isWatched;

    //data fields
    private final Description description;
    private final RunningTime runningTime;
    private final Set<Actor> actors = new HashSet<>();

    public Show(Name name, Description description, boolean isWatched, Date dateOfRelease,
                RunningTime runningTime, Set<Actor> actors) {
        requireAllNonNull(name, description, isWatched, dateOfRelease, runningTime, actors);
        this.name = name;
        this.description = description;
        this.isWatched = isWatched;
        this.dateOfRelease = dateOfRelease;
        this.runningTime = runningTime;
        this.actors.addAll(actors);
    }

    public Name getName() {
        return name;
    }

    public Date getDateOfRelease() {
        return dateOfRelease;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public Description getDescription() {
        return description;
    }

    public RunningTime getRunningTime() {
        return runningTime;
    }

    /**
     * Returns an immutable actor set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Actor> getActors() {
        return Collections.unmodifiableSet(actors);
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
                && (otherShow.getDateOfRelease().equals(getDateOfRelease()) || otherShow.isWatched() == (isWatched()));
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
                && otherShow.getDateOfRelease().equals(getDateOfRelease())
                && (otherShow.isWatched() == isWatched())
                && otherShow.getDescription().equals(getDescription())
                && otherShow.getRunningTime().equals(getRunningTime())
                && otherShow.getActors().equals(getActors());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dateOfRelease, isWatched, description, runningTime, actors);
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
                .append(" Actors: ");
        getActors().forEach(builder::append);
        return builder.toString();
    }
}
