package seedu.ezwatchlist.model.show;

import java.util.List;
import java.util.Set;

import seedu.ezwatchlist.model.actor.Actor;

/**
 * Represents a Movie in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Movie extends Show {

    public Movie(Name name, Description description, IsWatched isWatched,
                 Date dateOfRelease, RunningTime runningTime, Set<Actor> actors) {
        super(name, description, isWatched, dateOfRelease, runningTime, actors);
        super.setType("Movie");
    }

    @Override
    public int getNumOfEpisodesWatched() {
        return 0;
    }

    @Override
    public int getTotalNumOfEpisodes() {
        return 0;
    }

    @Override
    public List<TvSeason> getTvSeasons() {
        return null;
    }

    @Override
    public int getLastWatchedSeasonNum() {
        return 0;
    }

    @Override
    public int getLastWatchedSeasonEpisode() {
        return 0;
    }

    @Override
    public int getNumOfSeasons() {
        return 0;
    }

    @Override
    public int getNumOfEpisodesOfSeason(int seasonNum) {
        return 0;
    }

}
