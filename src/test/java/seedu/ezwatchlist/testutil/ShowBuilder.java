package seedu.ezwatchlist.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.*;
import seedu.ezwatchlist.model.util.SampleDataUtil;

/**
 * A utility class to help with building Show objects.
 */
public class ShowBuilder {

    public static final String DEFAULT_NAME = "Inception";
    public static final String DEFAULT_TYPE = "movie";
    public static final String DEFAULT_DATE = "16 July 2010";
    public static final boolean DEFAULT_ISWATCHED = true;
    public static final String DEFAULT_DESCRIPTION = "A thief who steals corporate secrets through the use "
            + " of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.";
    public static final int DEFAULT_RUNNINGTIME = 148;

    private Name name;
    private String type;
    private Description description;
    private IsWatched isWatched;
    private RunningTime runningTime;
    private Set<Actor> actors;
    private Date dateOfRelease;

    public ShowBuilder() {
        name = new Name(DEFAULT_NAME);
        this.type = DEFAULT_TYPE;
        description = new Description(DEFAULT_DESCRIPTION);
        this.isWatched = new IsWatched(DEFAULT_ISWATCHED);
        dateOfRelease = new Date(DEFAULT_DATE);
        this.runningTime = new RunningTime(DEFAULT_RUNNINGTIME);
        actors = new HashSet<>();
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
    public ShowBuilder withDateOfRelease(String Date) {
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
        if (type.equals("movie")) {
            return new Movie(name, description, isWatched, dateOfRelease, runningTime, actors);
        } else {
            return new TvShow(name, description, isWatched, dateOfRelease, runningTime, actors,
                    0 ,0, null);
        }
    }

}
