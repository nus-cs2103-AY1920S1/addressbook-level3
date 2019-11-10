package seedu.weme.model.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * The dislike data storage in Stats.
 */
public class DislikeData {

    private final ObservableMap<String, SimpleIntegerProperty> dislikeMap =
            FXCollections.observableHashMap();
    private final ObservableMap<String, SimpleIntegerProperty> unmodifiableDislikeMap =
            FXCollections.unmodifiableObservableMap(dislikeMap);

    /**
     * Constructs an empty DislikeData
     */
    public DislikeData() {}

    /**
     * Constructs a DislikeData filled with the provided dislikeMap
     */
    public DislikeData(ObservableMap<String, SimpleIntegerProperty> dislikeMap) {
        setDislikeMap(dislikeMap);
    }

    /**
     * Sets the current set of {@code DislikeData} with a replacement.
     */
    public void setDislikeMap(Map<String, SimpleIntegerProperty> replacement) {
        requireAllNonNull(replacement);
        dislikeMap.clear();
        dislikeMap.putAll(replacement);
    }

    /**
     * Sets dislike count of a meme.
     */
    public void setDislikesByMemeRef(String memeRef, int change) {
        SimpleIntegerProperty currDislikes = dislikeMap.getOrDefault(memeRef, new SimpleIntegerProperty(0));
        currDislikes.set(currDislikes.get() + change);
        if (!dislikeMap.containsKey(memeRef)) {
            dislikeMap.put(memeRef, currDislikes);
        }
        // forces the map to update as changes to the individual SimpleIntegerProperty value is not reflected as
        // change to the map.
        forceUpdate();
    }

    /**
     * Forces the map to update.
     */
    private void forceUpdate() {
        dislikeMap.put("update", new SimpleIntegerProperty(0));
        dislikeMap.remove("update");
    }

    /**
     * Returns the dislike count of a meme by its URL.
     */
    public int getDislikesByMemeRef(String memeRef) {
        return dislikeMap.getOrDefault(memeRef, new SimpleIntegerProperty(Integer.MAX_VALUE)).get();
    }

    /**
     * Returns an unmodifiable view of DislikeData.
     */
    public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
        return unmodifiableDislikeMap;
    }

    /**
     * Deletes dislike count of a meme by its URL.
     */
    public void deleteDislikesByMemeRef(String memeRef) {
        dislikeMap.remove(memeRef);
    }

    /**
     * Returns a deep copy of the current dislike map.
     */
    public Map<String, SimpleIntegerProperty> getCopy() {
        Map<String, SimpleIntegerProperty> copy = new HashMap<>();
        for (Map.Entry<String, SimpleIntegerProperty> entry : dislikeMap.entrySet()) {
            copy.put(entry.getKey(), new SimpleIntegerProperty(entry.getValue().get()));
        }
        return copy;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DislikeData)) {
            return false;
        }

        DislikeData otherDislikeData = (DislikeData) other;
        if (!dislikeMap.keySet().equals(otherDislikeData.getCopy().keySet())) {
            return false;
        } else {
            for (Map.Entry<String, SimpleIntegerProperty> entry : otherDislikeData.dislikeMap.entrySet()) {
                if (dislikeMap.get(entry.getKey()).get() != entry.getValue().get()) {
                    return false;
                }
            }
        }
        return true;
    }
}
