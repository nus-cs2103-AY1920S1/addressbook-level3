package seedu.weme.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;

/**
 * Manager class for statistics data for Weme.
 */
public class StatsManager implements Stats {

    private LikeManager likeManager;
    private TagManager tagManager;

    /**
     * Constructs a {@code StatsManager} without data.
     */
    public StatsManager() {
        this.likeManager = new LikeManager();
        this.tagManager = new TagManager();
    }

    /**
     * Constructs a {@code Stats Manager} with a {@code stats} data.
     */
    public StatsManager(Stats stats) {
        this();
        resetData(stats);
    }

    //============= Like Data ====================================

    @Override
    public LikeData getLikeData() {
        return likeManager.getLikeData();
    }

    /**
     * Replaces the contents of the like data with {@code likeData}.
     */
    @Override
    public void setLikeData(LikeData likeData) {
        this.likeManager.setLikeData(likeData);
    }

    /**
     * Returns an unmodifiable view of LikeData.
     */
    @Override
    public ObservableMap<String, Integer> getObservableLikeData() {
        return likeManager.getObservableLikeData();
    }

    /**
     * Increments like count of a meme.
     */
    @Override
    public void incrementMemeLikeCount(Meme meme) {
        likeManager.incrementMemeLikeCount(meme);
    }

    /**
     * Deletes like count of a meme from likeData.
     */
    @Override
    public void deleteLikesByMeme(Meme meme) {
        likeManager.deleteLikesByMeme(meme);
    }

    //============= Tag Data ====================================

    @Override
    public List<TagWithCount> getTagsWithCountList(List<Meme> memeList) {
        return tagManager.getTagsWithCountList(memeList);
    };

    /**
     * Resets the existing data of this {@code StatsManager} with {@code newData}.
     */
    @Override
    public void resetData(Stats newData) {
        requireNonNull(newData);

        setLikeData(newData.getLikeData());
    }
}
