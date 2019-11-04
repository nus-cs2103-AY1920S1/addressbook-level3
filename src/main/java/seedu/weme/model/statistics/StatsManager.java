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
