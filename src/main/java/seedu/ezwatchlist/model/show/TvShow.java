package seedu.ezwatchlist.model.show;

import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Represents a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TvShow extends Show {

    private static final Image imageOfShow = null;
    private int numOfEpisodesWatched;
    private List<TvSeason> tvSeasons;
    private final int totalNumOfEpisodes;

    public TvShow(Name name, Description description, IsWatched isWatched,
                  Date dateOfRelease, RunningTime runningTime, Set<Actor> actors,
                  int numOfEpisodesWatched, int totalNumOfEpisodes, List<TvSeason> tvSeasons) {
        super(name, description, isWatched, dateOfRelease, runningTime, actors);
        this.numOfEpisodesWatched = numOfEpisodesWatched;
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        this.tvSeasons = tvSeasons;
        super.setType("Tv Show");
    }

    @Override
    public int getNumOfEpisodesWatched() {
        return numOfEpisodesWatched;
    }

    @Override
    public List<TvSeason> getTvSeasons() {
        return tvSeasons;
    }

    @Override
    public int getTotalNumOfEpisodes() {
        return totalNumOfEpisodes;
    }

}
