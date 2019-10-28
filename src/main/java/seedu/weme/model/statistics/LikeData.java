package seedu.weme.model.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * The like data storage in Stats.
 */
public class LikeData {

    private final ObservableMap<String, Integer> likeMap =
            FXCollections.observableHashMap();
    private final ObservableMap<String, Integer> unmodifiableLikeMap =
            FXCollections.unmodifiableObservableMap(likeMap);

    /**
     * Constructs an empty LikeData
     */
    public LikeData() {}

    /**
     * Constructs a LikeData filled with the provided likeMap
     */
    public LikeData(ObservableMap<String, Integer> likeMap) {
        setLikeMap(likeMap);
    }

    /**
     * Sets the current set of {@code LikeData} with a replacement.
     */
    public void setLikeMap(Map<String, Integer> replacement) {
        requireAllNonNull(replacement);
        likeMap.clear();
        likeMap.putAll(replacement);
    }

    /**
     * Sets like count of a meme.
     */
    public void setLikesByMemeRef(String memeRef, int change) {
        int currLikes = likeMap.getOrDefault(memeRef, 0);
        likeMap.put(memeRef, currLikes + change);
    }

    /**
     * Returns the like count of a meme by its URL.
     */
    public int getLikesByMemeRef(String memeRef) {
        return likeMap.getOrDefault(memeRef, 0);
    }

    /**
     * Returns an unmodifiable view of LikeData.
     */
    public ObservableMap<String, Integer> getObservableLikeData() {
        return unmodifiableLikeMap;
    }

    /**
     * Deletes like count of a meme by its URL.
     * @param memeRef
     */
    public void deleteLikesByMemeRef(String memeRef) {
        likeMap.remove(memeRef);
    }

}
