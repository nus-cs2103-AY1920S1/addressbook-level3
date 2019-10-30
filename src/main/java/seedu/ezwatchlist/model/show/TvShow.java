package seedu.ezwatchlist.model.show;

import java.util.List;
import java.util.Set;

import seedu.ezwatchlist.model.actor.Actor;

/**
 * Represents a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TvShow extends Show {

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

    public int getLastWatchedSeasonNum() {
        int seasonNum = 0;
        int episodeNum = 0;
        while (episodeNum < numOfEpisodesWatched) {
            episodeNum += tvSeasons.get(seasonNum).getTotalNumOfEpisodes();
            seasonNum++;
        }
        return seasonNum;
    }

    public int getLastWatchedSeasonEpisode() {
        int cumulativeNumberOfEpisodes = 0;
        int lastWatchedSeasonNum = getLastWatchedSeasonNum();
        for (int seasonNum = 1; seasonNum < lastWatchedSeasonNum; seasonNum++) {
            cumulativeNumberOfEpisodes += tvSeasons.get(seasonNum - 1).getTotalNumOfEpisodes();
        }
        int episodeNum = numOfEpisodesWatched - cumulativeNumberOfEpisodes;

        if (lastWatchedSeasonNum > 0 && episodeNum == 0) {
            episodeNum = tvSeasons.get(lastWatchedSeasonNum).getTotalNumOfEpisodes();
        }

        return episodeNum;
    }

    @Override
    public int getNumOfSeasons() {
        return tvSeasons.size();
    }

    @Override
    public int getNumOfEpisodesOfSeason(int seasonNum) {
        return tvSeasons.get(seasonNum - 1).getTotalNumOfEpisodes();
    }
}
