package seedu.weme.model.statistics;

import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    void resetData(Stats stats);

    Stats getStats();

    LikeManager getLikeManager();

    //============= Like Data ====================================

    /**
     * Returns the number of likes of a meme.
     */
    int getLikesByMeme(Meme meme);

    /**
     * Replaces the contents of the like data with {@code likeData}.
     */
    void setLikeData(Map<String, SimpleIntegerProperty> likeData);

    /**
     * Returns an unmodifiable view of LikeData.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableLikeData();

    /**
     * Adds default like when the like data of a meme is not captured.
     */
    void addDefaultLikeData(Meme meme);

    /**
     * Increments like count of a meme.
     */
    void incrementMemeLikeCount(Meme meme);

    /**
     * Increments like count of a meme.
     */
    void decrementMemeLikeCount(Meme meme);

    /**
     * Deletes like count of a meme from likeData.
     */
    void deleteLikesByMeme(Meme meme);

    //============= Dislike Data ====================================

    /**
     * Returns the number of dislikes of a meme.
     */
    int getDislikesByMeme(Meme meme);

    /**
     * Replaces the contents of the dislike data with {@code dislikeData}.
     */
    void setDislikeData(Map<String, SimpleIntegerProperty> dislikeData);

    /**
     * Returns an unmodifiable view of DislikeData.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData();

    /**
     * Adds default dislike when the dislike data of a meme is not captured.
     */
    void addDefaultDislikeData(Meme meme);

    /**
     * Increments dislike count of a meme.
     */
    void incrementMemeDislikeCount(Meme meme);

    /**
     * Increments dislike count of a meme.
     */
    void decrementMemeDislikeCount(Meme meme);

    /**
     * Deletes dislike count of a meme from dislikeData.
     */
    void deleteDislikesByMeme(Meme meme);

    //============= Tag Data ====================================

    public int getCountOfTag(List<Meme> memeList, Tag tag);

    /**
     * Returns a list of tags with their use counts in descending order.
     */
    List<TagWithCount> getTagsWithCountList(List<Meme> memeList);

    /**
     * Returns a list of tags with their like counts in descending order.
     */
    List<TagWithLike> getTagsWithLikeCountList(List<Meme> memeList);

    /**
     * Returns a list of tags with their like counts in descending order.
     */
    List<TagWithDislike> getTagsWithDislikeCountList(List<Meme> memeList);
}
