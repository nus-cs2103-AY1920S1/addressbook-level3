package seedu.weme.statistics;

import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Meme;

/**
 * Interface for statistics data for Weme.
 */
public interface StatsEngine {

    LikeData getLikeData();

    void setLikeData(LikeData likeData);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementLikesByMeme(Meme meme);

    void deleteLikesByMeme(Meme meme);
}
