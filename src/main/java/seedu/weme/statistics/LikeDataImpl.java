package seedu.weme.statistics;

import java.util.HashMap;

/**
 * Like data.
 */
public class LikeDataImpl {

    private HashMap<String, Integer> likesList;

    public LikeDataImpl() {
        likesList = new HashMap<>();
    }

    public void setLikesList(HashMap<String, Integer> likesList) {
        this.likesList = likesList;
    }

    public void setLikesByMemeRef(String memeRef, int change) {
        if (!likesList.containsKey(memeRef)) {
            likesList.put(memeRef, change);
        } else {
            int currLikes = likesList.get(memeRef);
            likesList.replace(memeRef, currLikes + change);
        }
    }

    public int getLikesByMemeRef(String memeRef) {
        if (!likesList.containsKey(memeRef)) {
            likesList.put(memeRef, 0);
            return 0;
        }
        return likesList.get(memeRef);
    }

    public void deleteLikesByMemeRef(String memeRef) {
        likesList.remove(memeRef);
    }
}
