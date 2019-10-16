package seedu.weme.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
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

    public LikeManager(LikeData data) {
        super();
        requireAllNonNull(data);

        logger.fine("Initializing with like data: " + data);

        this.data = new LikeDataImpl();
        this.data.setLikeMap(data.getLikeData());
    }

    public LikeManager() {
        this.data = new LikeDataImpl();
    }

    @Override
    public int getLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        return data.getLikesByMemeRef(memeRef);
    }

    /**
     * Returns an unmodifiable view of {@code LikeData}.
     */
    @Override
    public ObservableMap<String, Integer> getLikeData() {
        return data.getInObservableMap();
    }

    /**
     * Returns {@code LikeData} in Map.
     */
    @Override
    public Map<String, Integer> getLikeDataInMap() {
        return data.getInMap();
    }

    /**
     * Replace the current like data with a new set of data.
     */
    @Override
    public void setLikeData(LikeData replacement) {
        data.setLikeMap(replacement.getLikeData());
    }

    @Override
    public void setLikeDataFromMap(Map<String, Integer> replacement) {
        data.setLikeMap(FXCollections.observableMap(replacement));
    }

    @Override
    public void incrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, INCREMENT);
    }

    @Override
    public void decrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, DECREMENT);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    @Override
    public void deleteLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.deleteLikesByMemeRef(memeRef);
    }

}
