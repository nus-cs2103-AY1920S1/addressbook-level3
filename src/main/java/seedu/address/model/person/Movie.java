package seedu.address.model.person;

import java.util.Set;

public class Movie extends Show {

    public Movie(Name name, Description description, boolean isWatched,
                 Date dateOfRelease, RunningTime runningTime, Set<Actor> actors) {
        super(name, description, isWatched, dateOfRelease, runningTime, actors);
    }
}
