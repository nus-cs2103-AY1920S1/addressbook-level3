package seedu.weme.statistics;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Like data.
 */
public class LikeDataImpl {

    private HashMap<String, Integer> likeMap;

    public LikeDataImpl() {
        likeMap = new HashMap<>();
    }

    public void setLikeMap(HashMap<String, Integer> likeMap) {
        this.likeMap = likeMap;
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

    public ObservableMap<String, Integer> getInMap() {
        return FXCollections.observableMap(likeMap);
    }

    public void deleteLikesByMemeRef(String memeRef) {
        likeMap.remove(memeRef);
    }
}
