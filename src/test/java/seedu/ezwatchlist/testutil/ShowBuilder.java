package seedu.ezwatchlist.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.*;
import seedu.ezwatchlist.model.tag.Tag;
import seedu.ezwatchlist.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class ShowBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "24/09/1997";
    public static final boolean DEFAULT_ISWATCHED = true;
    public static final String DEFAULT_DESCRIPTION = "STORY OF ALICE AND WONDERLAND";
    public static final int DEFAULT_RUNNINGTIME = 122;



    private seedu.ezwatchlist.model.show.Name name;
    private Description description;
    private IsWatched isWatched;
    private RunningTime runningTime;
    private Set<Actor> actors;
    private Date dateOfRelease;

    public ShowBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        this.isWatched = new IsWatched(DEFAULT_ISWATCHED);
        dateOfRelease = new Date(DEFAULT_DATE);
        this.runningTime = new RunningTime(DEFAULT_RUNNINGTIME);
        actors = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public ShowBuilder(Show showToCopy) {
        name = showToCopy.getName();
        description = showToCopy.getDescription();
        isWatched = showToCopy.isWatched();
        dateOfRelease = showToCopy.getDateOfRelease();
        runningTime = showToCopy.getRunningTime();
        actors = new HashSet<>(showToCopy.getActors());
    }

    /**
     * Sets the {@code Name} of the {@code Show} that we are building.
     */
    public ShowBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code Actors} into a {@code Set<Actors>} and set it to the {@code Actors} that we are building.
     */
    public ShowBuilder withActors(String ... Actors) {
        this.actors = SampleDataUtil.getActorSet(Actors);
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
    public ShowBuilder withIsWatched(boolean isWatched) {
        this.isWatched = new IsWatched(isWatched);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Show} that we are building.
     */
    public ShowBuilder withDateofRelease(String Date) {
        this.dateOfRelease = new Date(Date);
        return this;
    }

    /**
     * Sets the {@code runningTime} of the {@code Show} that we are building.
     */
    public ShowBuilder withRunningTime(int runningTime) {
        this.runningTime = new RunningTime(runningTime);
        return this;
    }


    public Show build() {
        return new Show(name, description, isWatched, dateOfRelease, runningTime, actors);
    }

}
