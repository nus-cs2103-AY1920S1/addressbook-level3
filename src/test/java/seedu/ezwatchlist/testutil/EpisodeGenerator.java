package seedu.ezwatchlist.testutil;

import java.util.ArrayList;

import seedu.ezwatchlist.model.show.Episode;

/**
 * Utility class to facilitate the creation of Episodes.
 */
public class EpisodeGenerator {

    private int nextIndex = 0;
    private ArrayList<Episode> episodes = new ArrayList<>();

    /**
     * Generates the next episode with the default name and episode number in sequence and adds it to the episode list.
     */
    public void generateNextEpisode() {
        nextIndex++;
        episodes.add(new Episode("Episode " + nextIndex, nextIndex));
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

}
