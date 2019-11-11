package seedu.weme.model.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * The like data storage in Stats.
 */
public class LikeData {

    private final ObservableMap<String, SimpleIntegerProperty> likeMap =
            FXCollections.observableHashMap();
    private final ObservableMap<String, SimpleIntegerProperty> unmodifiableLikeMap =
            FXCollections.unmodifiableObservableMap(likeMap);

    /**
     * Constructs an empty LikeData
     */
    public LikeData() {}

    /**
     * Constructs a LikeData filled with the provided likeMap
     */
    public LikeData(ObservableMap<String, SimpleIntegerProperty> likeMap) {
        setLikeMap(likeMap);
    }

    /**
     * Constructs a LikeData filled with the provided likeMap
     */
    public LikeData(LikeData likeData) {
        setLikeMap(likeData.getCopy());
    }

    /**
     * Sets the current set of {@code LikeData} with a replacement.
     */
    public void setLikeMap(Map<String, SimpleIntegerProperty> replacement) {
        requireAllNonNull(replacement);
        likeMap.clear();
        likeMap.putAll(replacement);
    }

    /**
     * Sets like count of a meme.
     */
    public void setLikesByMemeRef(String memeRef, int change) {
        SimpleIntegerProperty currLikes = likeMap.getOrDefault(memeRef, new SimpleIntegerProperty(0));
        currLikes.set(currLikes.get() + change);
        if (!likeMap.containsKey(memeRef)) {
            likeMap.put(memeRef, currLikes);
        }
        // forces the map to update as changes to the individual SimpleIntegerProperty value is not reflected as
        // change to the map.
        forceUpdate();
    }

    /**
     * Forces the map to update.
     */
    private void forceUpdate() {
        likeMap.put("update", new SimpleIntegerProperty(0));
        likeMap.remove("update");
    }

    /**
     * Returns the like count of a meme by its URL.
     */
    public int getLikesByMemeRef(String memeRef) {
        return likeMap.getOrDefault(memeRef, new SimpleIntegerProperty(Integer.MAX_VALUE)).get();
    }

    /**
     * Returns an unmodifiable view of LikeData.
     */
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return unmodifiableLikeMap;
    }

    /**
     * Deletes like count of a meme by its URL.
     */
    public void deleteLikesByMemeRef(String memeRef) {
        likeMap.remove(memeRef);
    }

    /**
     * Returns a deep copy of the current like map.
     */
    public Map<String, SimpleIntegerProperty> getCopy() {
        Map<String, SimpleIntegerProperty> copy = new HashMap<>();
        for (Map.Entry<String, SimpleIntegerProperty> entry : likeMap.entrySet()) {
            copy.put(entry.getKey(), new SimpleIntegerProperty(entry.getValue().get()));
        }
        return copy;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LikeData)) {
            return false;
        }

        LikeData otherLikeData = (LikeData) other;
        if (!likeMap.keySet().equals(otherLikeData.getCopy().keySet())) {
            return false;
        } else {
            for (Map.Entry<String, SimpleIntegerProperty> entry : otherLikeData.likeMap.entrySet()) {
                if (likeMap.get(entry.getKey()).get() != entry.getValue().get()) {
                    return false;
                }
            }
        }
        return true;
    }
}

