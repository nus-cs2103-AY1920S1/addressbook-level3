package seedu.weme.statistics;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;

import java.util.logging.Logger;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Implementation of Like interface.
 */
public class LikeManager implements LikeData {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int INCREMENT = 1;
    private static final int DECREMENT = -1;

    LikeDataImpl data;

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

    public void incrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, INCREMENT);
    }

    public void decrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, DECREMENT);
    }

    public void deleteLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.deleteLikesByMemeRef(memeRef);
    }
}
