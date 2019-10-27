package seedu.ezwatchlist.model.show;

import java.util.ArrayList;

/**
 * Represents a TvSeason of a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TvSeason {
    public static final String MESSAGE_CONSTRAINTS_SEASON_NUM =
            "Season numbers should be integers greater than 0.";
    public static final String MESSAGE_CONSTRAINTS_TOTAL_EPISODES =
            "Total number of episodes in a season should be integers greater or equal to 0.";

    private final int seasonNum;
    private final int totalNumOfEpisodes;
    private final ArrayList<Episode> episodes;

    public TvSeason(int seasonNum, int totalNumOfEpisodes, ArrayList<Episode> episodes) {
        this.seasonNum = seasonNum;
        this.totalNumOfEpisodes = totalNumOfEpisodes;
        this.episodes = episodes;
    }

    public int getSeasonNum() {
        return seasonNum;
    }

    public int getTotalNumOfEpisodes() {
        return totalNumOfEpisodes;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * Returns true if a given integer is a valid seasonNum.
     */
    public static boolean isValidTvSeasonNumber(int test) {
        return test > 0;
    }

    /**
     * Returns true if a given integer is a valid total number of episodes.
     */
    public static boolean isValidTotalNumOfEpisodes(int test) {
        return test >= 0;
    }
}
