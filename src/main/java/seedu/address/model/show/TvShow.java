package seedu.address.model.show;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.actor.Actor;

/**
 * Represents a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TvShow extends Show {

    private int numOfEpisodesWatched;
    private ArrayList<TvSeason> tvSeasons;
    private final int totalNumOfEpisodes;

    public TvShow(Name name, Description description, boolean isWatched,
                  Date dateOfRelease, RunningTime runningTime, Set<Actor> actors,
                  int numOfEpisodesWatched, int totalNumOfEpisodes, ArrayList<TvSeason> tvSeasons) {
        super(name, description, isWatched, dateOfRelease, runningTime, actors);
        this.numOfEpisodesWatched = numOfEpisodesWatched;
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        this.tvSeasons = tvSeasons;
    }

    public int getNumOfEpisodesWatched() {
        return numOfEpisodesWatched;
    }

    public ArrayList<TvSeason> getTvSeasons() {
        return tvSeasons;
    }

    public int getTotalNumOfEpisodes() {
        return totalNumOfEpisodes;
    }

}
