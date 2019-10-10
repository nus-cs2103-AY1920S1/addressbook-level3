package seedu.address.model.show;

import java.util.Set;

import seedu.address.model.actor.Actor;

/**
 * Represents a Movie in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Movie extends Show {


    public Movie(Name name, Description description, boolean isWatched,
                 Date dateOfRelease, RunningTime runningTime, Set<Actor> actors) {
        super(name, description, isWatched, dateOfRelease, runningTime, actors);
    }
}
