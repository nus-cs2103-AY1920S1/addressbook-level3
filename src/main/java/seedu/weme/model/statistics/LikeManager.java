package seedu.weme.model.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;

/**
 * Implementation of Like interface.
 */
public class LikeManager {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int INCREMENT = 1;
    private static final int DECREMENT = -1;

    private LikeData data;

    public LikeManager(LikeData data) {
        super();
        requireAllNonNull(data);

        logger.fine("Initializing with like data: " + data);

        this.data = new LikeData(data.getObservableLikeData());
    }

    public LikeManager() {
        this.data = new LikeData();
    }

    /**
     * Returns the number of likes of a meme.
     */
    public int getLikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        return data.getLikesByMemeRef(memeRef);
    }

    /**
     * Returns an unmodifiable view of {@code LikeData}.
     */
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return data.getObservableLikeData();
    }

    /**
     * Replace the current like data with a new set of data.
     */
    public void setLikeData(Map<String, SimpleIntegerProperty> replacement) {
        data.setLikeMap(replacement);
    }

    /**
     * Initializes the like count of a meme.
     */
    public void addDefaultLikeData(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        data.setLikesByMemeRef(memeRef, 0);
    }

    /**
     * Increments a meme's like count by 1.
     */
    public void incrementMemeLikeCount(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        data.setLikesByMemeRef(memeRef, INCREMENT);
    }

    /**
     * Decrements a meme's like count by 1.
     */
    public void decrementLikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        data.setLikesByMemeRef(memeRef, DECREMENT);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    public void deleteLikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        data.deleteLikesByMemeRef(memeRef);
    }

}
