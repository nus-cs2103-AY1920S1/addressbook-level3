package seedu.weme.model.statistics;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    void resetData(Stats stats);

    Stats getStats();

    //============= Like Data ====================================

    int getLikesByMeme(Meme meme);

    void setLikeData(Map<String, Integer> likeData);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementMemeLikeCount(Meme meme);

    void deleteLikesByMeme(Meme meme);

    //============= Tag Data ====================================

    public int getCountOfTag(List<Meme> memeList, Tag tag);

    List<TagWithCount> getTagsWithCountList(List<Meme> memeList);

}
