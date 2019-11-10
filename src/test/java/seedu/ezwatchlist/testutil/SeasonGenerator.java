package seedu.ezwatchlist.testutil;

import java.util.ArrayList;

import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.TvSeason;

/**
 * Utility class to facilitate the creation of TvSeasons.
 */
public class SeasonGenerator {

    private int nextIndex = 0;
    private ArrayList<TvSeason> tvSeasons = new ArrayList<>();

    /**
     * Generates the next TvSeason and adds it to the list of tvSeasons.
     * @param numOfEpisodes the specified number of episodes in the season.
     * @return the existing SeasonGenerator object.
     */
    public SeasonGenerator withTvSeason(int numOfEpisodes) {
        nextIndex++;
        EpisodeGenerator episodeGenerator = new EpisodeGenerator();
        for (int i = 0; i < numOfEpisodes; i++) {
            episodeGenerator.generateNextEpisode();
        }
        ArrayList<Episode> episodes = episodeGenerator.getEpisodes();
        tvSeasons.add(new TvSeason(nextIndex, episodes.size(), episodes));
        return this;
    }

    public ArrayList<TvSeason> getTvSeasons() {
        return tvSeasons;
    }

}
