package seedu.weme.statistics;

import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Meme;

/**
 * Manager class for statistics data for Weme.
 */
public class StatsManager implements StatsEngine {

    private LikeData likeData;

    /**
     * Constructs a {@code StatsManager} without data.
     */
    public StatsManager() {
        this.likeData = new LikeManager();
    }

    @Override
    public LikeData getLikeData() {
        return likeData;
    }

    @Override
    public void setLikeData(LikeData likeData) {
        this.likeData = likeData;
    }

    @Override
    public ObservableMap<String, Integer> getObservableLikeData() {
        return likeData.getLikeData();
    }

    @Override
    public void incrementLikesByMeme(Meme meme) {
        likeData.incrementLikesByMeme(meme);
    }

    @Override
    public void deleteLikesByMeme(Meme meme) {
        likeData.deleteLikesByMeme(meme);
    }
}
