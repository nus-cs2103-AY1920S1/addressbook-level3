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

    private LikeData likeData;
    private DislikeData dislikeData;

    public LikeManager(LikeData likeData, DislikeData dislikeData) {
        super();
        requireAllNonNull(likeData);

        logger.fine("Initializing with like data: " + likeData);

        this.likeData = new LikeData(likeData.getObservableLikeData());
        this.dislikeData = new DislikeData(dislikeData.getObservableDislikeData());
    }

    public LikeManager() {
        this.likeData = new LikeData();
        this.dislikeData = new DislikeData();
    }

    // ================ Like methods ===============================================

    /**
     * Returns the number of likes of a meme.
     */
    public int getLikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        return likeData.getLikesByMemeRef(memeRef);
    }

    /**
     * Returns an unmodifiable view of {@code LikeData}.
     */
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return likeData.getObservableLikeData();
    }

    /**
     * Replace the current like data with a new set of data.
     */
    public void setLikeData(Map<String, SimpleIntegerProperty> replacement) {
        likeData.setLikeMap(replacement);
    }

    /**
     * Initializes the like count of a meme.
     */
    public void addDefaultLikeData(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        likeData.setLikesByMemeRef(memeRef, 0);
    }

    /**
     * Increments a meme's like count by 1.
     */
    public void incrementMemeLikeCount(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        likeData.setLikesByMemeRef(memeRef, INCREMENT);
    }

    /**
     * Decrements a meme's like count by 1.
     */
    public void decrementLikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        likeData.setLikesByMemeRef(memeRef, DECREMENT);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    public void deleteLikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        likeData.deleteLikesByMemeRef(memeRef);
    }

    // ================ Dislike methods ===============================================

    /**
     * Returns the number of dislikes of a meme.
     */
    public int getDislikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        return dislikeData.getDislikesByMemeRef(memeRef);
    }

    /**
     * Returns an unmodifiable view of {@code DislikeData}.
     */
    public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
        return dislikeData.getObservableDislikeData();
    }

    /**
     * Replace the current like data with a new set of data.
     */
    public void setDislikeData(Map<String, SimpleIntegerProperty> replacement) {
        dislikeData.setDislikeMap(replacement);
    }

    /**
     * Initializes the like count of a meme.
     */
    public void addDefaultDislikeData(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        dislikeData.setDislikesByMemeRef(memeRef, 0);
    }

    /**
     * Increments a meme's like count by 1.
     */
    public void incrementMemeDislikeCount(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        dislikeData.setDislikesByMemeRef(memeRef, INCREMENT);
    }

    /**
     * Decrements a meme's like count by 1.
     */
    public void decrementDislikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        dislikeData.setDislikesByMemeRef(memeRef, DECREMENT);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    public void deleteDislikesByMeme(Meme meme) {
        String memeRef = meme.getImagePath().toString();
        dislikeData.deleteDislikesByMemeRef(memeRef);
    }

    public Map<String, SimpleIntegerProperty> getCopyLikeData() {
        return likeData.getCopy();
    }

    public Map<String, SimpleIntegerProperty> getCopyDislikeData() {
        return dislikeData.getCopy();
    }

}
