package seedu.weme.model;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.TagWithCount;

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
     * Returns a list of tags with their counts.
     */
    List<TagWithCount> getTagsWithCountList();

    /** Return records of Weme.
     * @return
     */
    Records getRecords();

}
