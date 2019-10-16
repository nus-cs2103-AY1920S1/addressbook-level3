package seedu.weme.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableMap;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;

/**
 * Implementation of Like interface.
 */
public class LikeManager implements LikeData {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int INCREMENT = 1;
    private static final int DECREMENT = -1;

    private LikeDataImpl data;

    public LikeManager(LikeDataImpl data) {
        super();
        requireAllNonNull(data);

        logger.fine("Initializing with like data: " + data);

        this.data = data;
    }

    public LikeManager() {
        this(new LikeDataImpl());
    }

    public int getLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        return data.getLikesByMemeRef(memeRef);
    }

    /**
     * Returns an unmodifiable view of {@code LikeData}.
     */
    public ObservableMap<String, Integer> getLikeData() {
        return data.getInMap();
    }

    /**
     * Increments likes of a meme by the Meme object.
     */
    public void incrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, INCREMENT);
    }

    /**
     * Decrements likes of a meme by the Meme object.
     */
    public void decrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, DECREMENT);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    public void deleteLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.deleteLikesByMemeRef(memeRef);
    }

}
