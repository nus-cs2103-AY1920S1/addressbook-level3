package seedu.weme.statistics;

import static java.util.Objects.requireNonNull;

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

    /**
     * Constructs a {@code Stats Manager} with a {@code statsEngine} data.
     */
    public StatsManager(StatsEngine statsEngine) {
        this();
        resetData(statsEngine);
    }

    @Override
    public LikeData getLikeData() {
        return likeData;
    }

    /**
     * Replaces the contents of the like data with {@code likeData}.
     */
    @Override
    public void setLikeData(LikeData likeData) {
        this.likeData.setLikeData(likeData);
    }

    /**
     * Returns an unmodifiable view of LikeData.
     */
    @Override
    public ObservableMap<String, Integer> getObservableLikeData() {
        return likeData.getLikeData();
    }

    /**
     * Increments like count of a meme.
     */
    @Override
    public void incrementMemeLikeCount(Meme meme) {
        likeData.incrementMemeLikeCount(meme);
    }

    /**
     * Deletes like count of a meme from likeData.
     */
    @Override
    public void deleteLikesByMeme(Meme meme) {
        likeData.deleteLikesByMeme(meme);
    }

    /**
     * Resets the existing data of this {@code StatsManager} with {@code newData}.
     */
    public void resetData(StatsEngine newData) {
        requireNonNull(newData);

        setLikeData(newData.getLikeData());
    }
}
