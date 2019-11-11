package seedu.weme.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
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

    public LikeManager getLikeManager() {
        return likeManager;
    }

    //============= Like Data ====================================

    public void addDefaultLikeData(Meme meme) {
        likeManager.addDefaultLikeData(meme);
    }

    @Override
    public void setLikeData(Map<String, SimpleIntegerProperty> likeData) {
        likeManager.setLikeData(likeData);
    }

    @Override
    public int getLikesByMeme(Meme meme) {
        return likeManager.getLikesByMeme(meme);
    }

    @Override
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return likeManager.getObservableLikeData();
    }

    @Override
    public void incrementMemeLikeCount(Meme meme) {
        likeManager.incrementMemeLikeCount(meme);
    }

    @Override
    public void decrementMemeLikeCount(Meme meme) {
        likeManager.decrementLikesByMeme(meme);
    }

    @Override
    public void deleteLikesByMeme(Meme meme) {
        likeManager.deleteLikesByMeme(meme);
    }

    //============= Dislike Data ====================================

    public int getDislikesByMeme(Meme meme) {
        return likeManager.getDislikesByMeme(meme);
    }

    public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
        return likeManager.getObservableDislikeData();
    }

    public void setDislikeData(Map<String, SimpleIntegerProperty> replacement) {
        likeManager.setDislikeData(replacement);
    }

    public void addDefaultDislikeData(Meme meme) {
        likeManager.addDefaultDislikeData(meme);
    }

    public void incrementMemeDislikeCount(Meme meme) {
        likeManager.incrementMemeDislikeCount(meme);
    }

    /**
     * Decrements a meme's like count by 1.
     */
    public void decrementMemeDislikeCount(Meme meme) {
        likeManager.decrementDislikesByMeme(meme);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    public void deleteDislikesByMeme(Meme meme) {
        likeManager.deleteDislikesByMeme(meme);
    }
    //============= Tag Data ====================================

    @Override
    public int getCountOfTag(List<Meme> memeList, Tag tag) {
        return tagManager.getCountOfTag(memeList, tag);
    }

    @Override
    public List<TagWithCount> getTagsWithCountList(List<Meme> memeList) {
        return tagManager.getTagsWithCountList(memeList);
    }

    @Override
    public List<TagWithLike> getTagsWithLikeCountList(List<Meme> memeList) {
        return tagManager.getTagsWithLike(memeList, likeManager);
    }

    @Override
    public List<TagWithDislike> getTagsWithDislikeCountList(List<Meme> memeList) {
        return tagManager.getTagsWithDislike(memeList, likeManager);
    }

    /**
     * Resets the existing data of this {@code StatsManager} with {@code newData}.
     */
    @Override
    public void resetData(Stats newData) {
        requireNonNull(newData);

        setLikeData(newData.getObservableLikeData());
        setDislikeData(newData.getObservableDislikeData());
    }

    /**
     * Returns a copy of the current Stats.
     */
    @Override
    public Stats getStats() {
        Stats newStats = new StatsManager(this);
        // needs a full copy of likedata and dislikedata using it's own SIP
        newStats.setLikeData(likeManager.getCopyLikeData());
        newStats.setDislikeData(likeManager.getCopyDislikeData());
        return newStats;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatsManager)) {
            return false;
        }

        StatsManager otherStats = (StatsManager) other;
        return likeManager.equals(otherStats.likeManager);
    }
}
