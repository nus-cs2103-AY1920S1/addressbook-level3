package seedu.weme.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

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
    /**
     * Replaces the contents of the like data with {@code likeData}.
     */
    @Override
    public void setLikeData(Map<String, Integer> likeData) {
        likeManager.setLikeData(likeData);
    }

    /**
     * Returns the number of likes of a meme.
     */
    @Override
    public int getLikesByMeme(Meme meme) {
        return likeManager.getLikesByMeme(meme);
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
     * Increments like count of a meme.
     */
    @Override
    public void decrementMemeLikeCount(Meme meme) {
        likeManager.decrementLikesByMeme(meme);
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
    public int getCountOfTag(List<Meme> memeList, Tag tag) {
        return tagManager.getCountOfTag(memeList, tag);
    }

    @Override
    public List<TagWithCount> getTagsWithCountList(List<Meme> memeList) {
        return tagManager.getTagsWithCountList(memeList);
    };

    @Override
    public List<TagWithLike> getTagsWithLikeCountList(List<Meme> memeList) {
        return tagManager.getTagsWithLike(memeList, likeManager);
    }

    /**
     * Resets the existing data of this {@code StatsManager} with {@code newData}.
     */
    @Override
    public void resetData(Stats newData) {
        requireNonNull(newData);

        setLikeData(newData.getObservableLikeData());
    }

    /**
     * Returns a copy of the current Stats.
     */
    @Override
    public Stats getStats() {
        Stats newStats = new StatsManager(this);
        return newStats;
    }

}
