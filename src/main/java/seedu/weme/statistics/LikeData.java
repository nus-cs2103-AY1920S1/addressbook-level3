package seedu.weme.statistics;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * The like data storage in Stats.
 */
public class LikeData {

    private ObservableMap<String, Integer> likeMap;

    /**
     * Constructs an empty like data.
     */
    public LikeData() {
        likeMap = FXCollections.observableMap(new HashMap<>());
    }

    /**
     * Sets the current set of {@code LikeData} with a replacement.
     */
    public void setLikeMap(ObservableMap<String, Integer> replacement) {
        likeMap.putAll(replacement);
    }

    /**
     * Sets like count of a meme.
     */
    public void setLikesByMemeRef(String memeRef, int change) {
        if (!likeMap.containsKey(memeRef)) {
            likeMap.put(memeRef, change);
        } else {
            int currLikes = likeMap.get(memeRef);
            likeMap.replace(memeRef, currLikes + change);
        }
    }

    /**
     * Returns the like count of a meme by its URL.
     */
    public int getLikesByMemeRef(String memeRef) {
        if (!likeMap.containsKey(memeRef)) {
            likeMap.put(memeRef, 0);
            return 0;
        }
        return likeMap.get(memeRef);
    }

    /**
     * Returns an unmodifiable view of LikeData.
     * @return
     */
    public ObservableMap<String, Integer> getObservableLikeData() {
        return likeMap;
    }

    /**
     * Deletes like count of a meme by its URL.
     * @param memeRef
     */
    public void deleteLikesByMemeRef(String memeRef) {
        likeMap.remove(memeRef);
    }

    /**
     * Returns like data in Map.
     */
    public Map<String, Integer> getInMap() {
        return likeMap;
    }
}
