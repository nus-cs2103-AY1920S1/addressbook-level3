package seedu.weme.statistics;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Like data.
 */
public class LikeDataImpl {

    private ObservableMap<String, Integer> likeMap;

    public LikeDataImpl() {
        likeMap = FXCollections.observableMap(new HashMap<>());
    }

    public void setLikeMap(ObservableMap<String, Integer> replacement) {
        this.likeMap = FXCollections.observableMap(replacement);
    }

    public void setLikesByMemeRef(String memeRef, int change) {
        if (!likeMap.containsKey(memeRef)) {
            likeMap.put(memeRef, change);
        } else {
            int currLikes = likeMap.get(memeRef);
            likeMap.replace(memeRef, currLikes + change);
        }
    }

    public int getLikesByMemeRef(String memeRef) {
        if (!likeMap.containsKey(memeRef)) {
            likeMap.put(memeRef, 0);
            return 0;
        }
        return likeMap.get(memeRef);
    }

    public ObservableMap<String, Integer> getInObservableMap() {
        return likeMap;
    }

    public void deleteLikesByMemeRef(String memeRef) {
        likeMap.remove(memeRef);
    }

    public Map<String, Integer> getInMap() {
        return likeMap;
    }
}
