package seedu.weme.model;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.records.Records;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.statistics.TagWithDislike;
import seedu.weme.model.statistics.TagWithLike;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.MemeText;
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
     * Returns an unmodifiable view of the meme the user is currently viewing.
     */
    ObservableValue<Meme> getViewableMeme();

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
     * Returns an observable view of the like data.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableLikeData();

    /**
     * Returns an observable view of the dislike data.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData();

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
     * Returns a list of tags with their like counts.
     */
    List<TagWithDislike> getTagsWithDislikeCountList();

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

    /**
     * Returns the {@code MemeText}s in the current meme creation session.
     *
     * @return the {@code MemeText}s in the current meme creation session
     */
    ObservableList<MemeText> getMemeTextList();

    /**
     * Returns the image of the meme creation session, if any.
     *
     * @return the image of the meme creation session, or {@link Optional#empty} if there is none.
     */
    Optional<BufferedImage> getMemeCreationImage();
}
