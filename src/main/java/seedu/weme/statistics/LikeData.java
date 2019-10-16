package seedu.weme.statistics;

import java.util.Map;

import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;

/**
 * An interface for Like/Dislike feature.
 */
public interface LikeData {

    /**
     * Returns the like data about a meme.
     */
    int getLikesByMeme(Meme meme);

    /**
     * Returns the like data in map form.
     */
    ObservableMap<String, Integer> getLikeData();

    /**
     * Replaces current like data with replacement.
     */
    void setLikeData(LikeData replacement);

    /**
     * Sets the current like data with Map of like data.
     */
    void setLikeDataFromMap(Map<String, Integer> replacement);

    /**
     * Likes a meme once.
     */
    void incrementLikesByMeme(Meme meme);

    /**
     * Dislikes a meme once.
     */
    void decrementLikesByMeme(Meme meme);

    /**
     * Delete like data for a meme.
     */
    void deleteLikesByMeme(Meme meme);

    Map<String, Integer> getLikeDataInMap();
}
