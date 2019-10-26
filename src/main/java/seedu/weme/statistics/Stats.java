package seedu.weme.statistics;

import java.util.List;

import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    //============= Like Data ====================================

    LikeData getLikeData();

    void setLikeData(LikeData likeData);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementMemeLikeCount(Meme meme);

    void deleteLikesByMeme(Meme meme);

    void resetData(Stats stats);

    //============= Tag Data ====================================

    List<TagWithCount> getTagsWithCountList(List<Meme> memeList);

}
