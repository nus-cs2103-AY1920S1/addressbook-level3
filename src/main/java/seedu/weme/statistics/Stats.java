package seedu.weme.statistics;

import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Meme;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    LikeData getLikeManager();

    void setLikeManager(LikeData likeManager);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementMemeLikeCount(Meme meme);

    void deleteLikesByMeme(Meme meme);
}
