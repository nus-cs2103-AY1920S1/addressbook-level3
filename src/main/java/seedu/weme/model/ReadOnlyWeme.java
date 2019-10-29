package seedu.weme.model;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.statistics.TagWithLike;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.Template;

/**
 * Unmodifiable view of a Weme
 */
public interface ReadOnlyWeme {

    /**
     * Returns an unmodifiable view of the memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getMemeList();

    /**
     * Returns an umodifiable view of the staged memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getStagedMemeList();

    /**
     * Returns an umodifiable view of the import memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getImportList();

    /**
     * Returns an unmodifiable view of the template list.
     * This list will not contain any duplicate templates.
     */
    ObservableList<Template> getTemplateList();

    /**
     * Returns the statistics data of Weme.
     */
    Stats getStats();

    /**
     * Returns the count of a tag in the memeList.
     * Returns -1 if the tag is not present in the memeList.
     */
    int getCountOfTag(Tag tag);

    /**
     * Returns a list of tags with their counts.
     */
    List<TagWithCount> getTagsWithCountList();

    /**
     * Returns a list of tags with their like counts.
     */
    List<TagWithLike> getTagsWithLikeCountList();

    /**
     * Returns records of Weme.
     */
    Records getRecords();

    /**
     * Returns the current meme creation session.
     *
     * @return the current meme creation session
     */
    MemeCreation getMemeCreation();

}
