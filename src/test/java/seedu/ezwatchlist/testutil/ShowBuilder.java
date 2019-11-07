package seedu.ezwatchlist.testutil;

import java.util.ArrayList;
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
    public static final Type DEFAULT_TYPE = Type.MOVIE;
    public static final String DEFAULT_DATE = "16 July 2010";
    public static final String DEFAULT_IS_WATCHED = "true";
    public static final String DEFAULT_DESCRIPTION = "A thief who steals corporate secrets through the use "
            + " of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.";
    public static final int DEFAULT_RUNNINGTIME = 148;

    private Name name;
    private Type type;
    private Description description;
    private IsWatched isWatched;
    private RunningTime runningTime;
    private Set<Actor> actors;
    private Date dateOfRelease;

    public ShowBuilder() {
        name = new Name(DEFAULT_NAME);
        this.type = DEFAULT_TYPE;
        description = new Description(DEFAULT_DESCRIPTION);
        this.isWatched = new IsWatched(DEFAULT_IS_WATCHED);
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
        switch (type) {
          case ("movie"):
            this.type = Type.MOVIE;
          case ("tv"):
            this.type = Type.TV_SHOW;
        }
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
     * Builds the show.
     * @return show.
     */
    public Show build() {
        if (type.equals(Type.MOVIE)) {
            return new Movie(name, description, isWatched, dateOfRelease, runningTime, actors);
        } else {
            return new TvShow(name, description, isWatched, dateOfRelease, runningTime, actors,
                    0 , 0, new ArrayList<TvSeason>());
        }
    }

}
