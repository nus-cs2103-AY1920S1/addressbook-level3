package seedu.weme.statistics;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    void resetData(Stats stats);

    Stats getStats();

    //============= Like Data ====================================

    void setLikeData(Map<String, Integer> likeData);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementMemeLikeCount(Meme meme);

    void deleteLikesByMeme(Meme meme);

    //============= Tag Data ====================================

    List<TagWithCount> getTagsWithCountList(List<Meme> memeList);

}
