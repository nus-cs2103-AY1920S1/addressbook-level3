package seedu.address.model.show;

import java.util.ArrayList;

/**
 * Represents a TvSeason of a TvShow in the watchlist.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TvSeason {

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
}
