package seedu.address.model.person;

import java.util.ArrayList;

public class TvSeason {

    private int seasonNum;
    private int totalNumOfEpisodes;
    private ArrayList<Episode> episodes;

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
