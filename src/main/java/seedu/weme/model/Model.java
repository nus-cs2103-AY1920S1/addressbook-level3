package seedu.weme.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.Template;
import seedu.weme.model.template.exceptions.MemeCreationException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that evaluates to true if unarchived */
    Predicate<Meme> PREDICATE_SHOW_ALL_UNARCHIVED_MEMES = meme -> !meme.isArchived();
    /** {@code Predicate} that evaluate to true if unarchived */
    Predicate<Template> PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES = template -> !template.isArchived();
    /** {@code Predicate} that evaluate to true if archived */
    Predicate<Meme> PREDICATE_SHOW_ALL_ARCHIVED_MEMES = meme -> meme.isArchived();
    /** {@code Predicate} that evaluate to true if archived */
    Predicate<Template> PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES = template -> template.isArchived();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Returns the user's preferences for Weme.
     */
    ObservableMap<String, String> getObservableUserPreferences();


    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the default export path in preferences.json.
     */
    Path getDefaultExportPath();

    /**
     * Returns the user prefs' data file path.
     */
    Path getDataFilePath();

    /**
     * Sets the user prefs' data file path.
     */
    void setDataFilePath(Path dataFilePath);

    /**
     * Returns the user prefs' meme image path.
     */
    Path getMemeImagePath();

    /**
     * Returns the meme that the user wants to view.
     */
    ObservableValue<Meme> getViewableMeme();

    /**
     * Sets the meme for the user to view.
     * @param meme
     */
    void setViewableMeme(Meme meme);

    /**
     * Sets the user prefs' meme image path.
     */
    void setMemeImagePath(Path memeImagePath);

    /**
     * Returns the user prefs' template image path.
     */
    Path getTemplateImagePath();

    /**
     * Sets the user prefs' template image path.
     */
    void setTemplateImagePath(Path templateImagePath);

    /**
     * Replaces Weme data with the data in {@code weme}.
     */
    void setWeme(ReadOnlyWeme weme);

    /**
     * Returns Weme
     */
    ReadOnlyWeme getWeme();

    /**
     * Deletes all memes from Weme.
     */
    void clearMemes();

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in weme.
     */
    boolean hasMeme(Meme meme);

    /**
     * Stages the given meme for export.
     * The meme must exist in Weme.
     */
    void stageMeme(Meme meme);

    /**
     * Unstages the given meme for export.
     * The meme must exist in Weme.
     */
    void unstageMeme(Meme meme);

    /**
     * Deletes the given meme.
     * The meme must exist in Weme.
     */
    void deleteMeme(Meme target);

    /**
     * Deletes the given imported meme.
     * The meme must exist in the imported list.
     */
    void deleteImportedMeme(Meme target);

    /**
     * Adds the given meme.
     * {@code meme} must not already exist in Weme.
     */
    void addMeme(Meme meme);

    /**
     * Retrieves paths of memes in the staging area.
     *
     * @return A List of Path representing memes in staging area.
     */
    List<Path> getExportPathList();

    /**
     * Imports the meme to storage.
     */
    void importMemes() throws IOException;

    /**
     * Loads the memes to import tab.
     */
    void loadMemes(List<Path> pathList);

    /**
     * Replaces the given meme {@code target} with {@code editedMeme}.
     * {@code target} must exist in Weme.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in Weme.
     */
    void setMeme(Meme target, Meme editedMeme);

    /**
     * Returns true if the meme is in the export staging area.
     */
    boolean isMemeStaged(Meme meme);

    /**
     * Replaces the given meme {@code target} with {@code editedMeme}.
     * {@code target} must exist in Weme.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in Weme.
     */
    void setImportedMeme(Meme target, Meme editedMeme);

    /**
     * Returns true if the export staging area is empty.
     */
    boolean isStagingAreaEmpty();

    /**
     * Returns true if a template with the same identity as {@code template} exists in Weme.
     */
    boolean hasTemplate(Template template);

    /**
     * Deletes the given template.
     * The template must exist in Weme.
     */
    void deleteTemplate(Template template);

    /**
     * Deletes all templates from Weme.
     */
    void clearTemplates();

    /**
     * Adds the given template.
     * {@code template} must not already exist in Weme.
     */
    void addTemplate(Template template);

    /**
     * Replaces the given template {@code target} with {@code editedTemplate}.
     * {@code target} must exist in Weme.
     * The template identity of {@code editedTemplate} must not be the same as another existing template in Weme.
     */
    void setTemplate(Template target, Template editedTemplate);

    /** Returns an unmodifiable view of the filtered meme list */
    ObservableList<Meme> getFilteredMemeList();

    /**
     * Returns an unmodifiable view of the staged meme list.
     */
    ObservableList<Meme> getStagedMemeList();

    /**
     * Returns an unmodifiable view of the import meme list.
     */
    ObservableList<Meme> getImportList();

    /**
     * Updates the filter of the filtered meme list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemeList(Predicate<Meme> predicate);

    /** Returns an unmodifiable view of the filtered template list */
    ObservableList<Template> getFilteredTemplateList();

    /**
     * Updates the filter of the filtered template list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTemplateList(Predicate<Template> predicate);

    /**
     * Sets the model context.
     * @param context the context to switch to
     */
    void setContext(ModelContext context);

    /**
     * Returns the context of the model.
     * @return the current context
     */
    ObservableValue<ModelContext> getContext();

    /**
     * Starts a meme creation session.
     * @param template the template to use for meme creation
     */
    void startMemeCreation(Template template) throws IOException;

    /**
     * Aborts the current meme creation session.
     **/
    void abortMemeCreation();

    /**
     * Returns the meme text list of the meme creation session.
     *
     * @return the meme text list of the meme creation session
     */
    ObservableList<MemeText> getMemeTextList();

    /**
     * Adds a {@code MemeText} to the current meme creation session.
     *
     * @param text the {@code MemeText} to add
     */
    void addMemeText(MemeText text);

    /**
     * Deletes a {@code MemeText} from the current meme creation session.
     *
     * @param toDelete the {@code MemeText} to delete
     */
    void deleteMemeText(MemeText toDelete);

    /**
     * Replaces a {@code MemeText} in the current meme creation session with another {@code MemeText}.
     *
     * @param toReplace   the {@code MemeText} to replace
     * @param replacement the {@code MemeText} to use as replacement
     */
    void setMemeText(MemeText toReplace, MemeText replacement);

    /**
     * Returns the image of the meme creation session, if any.
     *
     * @return the image of the meme creation session, or {@link Optional#empty} if there is none.
     */
    Optional<BufferedImage> getMemeCreationImage();

    /**
     * Creates a new {@code Meme} from the current meme creation session and save it into Weme.
     * @param path the {@code Path} to save the new meme image to
     * @throws MemeCreationException if an IO error occurs while writing the image to the disk
     */
    void createMeme(Path path) throws MemeCreationException;

    /**
     * Returns true if model has a previous state to restore.
     */
    boolean canUndoWeme();

    /**
     * Returns true if model has a undone state to restore.
     */
    boolean canRedoWeme();

    /**
     * Restores the model's Weme to its previous state.
     * @return the feedback to give the user of the undone command.
     */
    String undoWeme();

    /**
     * Restores the model's Weme to its previously undone state.
     * @return the feedback to give the user of the redone command.
     */
    String redoWeme();

    /**
     * Saves the current Weme state for undo/redo.
     * @param feedback The string to inform the user what command was undone / redone
     */
    void commitWeme(String feedback);

    /**
     * Returns the like data.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableLikeData();

    /**
     * Increments likes of a meme by the Meme object.
     */
    void incrementMemeLikeCount(Meme meme);

    /**
     * Decrements likes of a meme by the Meme object.
     */
    void decrementMemeLikeCount(Meme meme);

    /**
     * Returns the dislike data.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData();

    /**
     * Increments dislikes of a meme by the Meme object.
     */
    void incrementMemeDislikeCount(Meme meme);

    /**
     * Decrements dislikes of a meme by the Meme object.
     */
    void decrementMemeDislikeCount(Meme meme);

    /**
     * Deletes stats data by meme.
     */
    void clearMemeStats(Meme memeToDelete);

    /**
     * Clears the list memes in export tab.
     */
    void clearExportList();

    /**
     * Clears the list memes in export tab.
     */
    void clearImportList();

    /**
     * Returns all past records of file paths.
     */
    Set<String> getPathRecords();

    /**
     * Returns all past records of descriptions.
     */
    Set<String> getDescriptionRecords();

    /**
     * Returns all past records of tags.
     */
    Set<String> getTagRecords();

    /**
     * Returns all past records of names.
     */
    Set<String> getNameRecords();

    /**
     * Returns all past records of texts.
     */
    Set<String> getTextRecords();

    /**
     * Add information of a meme to the records.
     */
    void addMemeToRecords(Meme meme);

    /**
     * Add information of a template to the records.
     */
    void addTemplateToRecords(Template template);

    /**
     * Add information of a meme text to the records.
     */
    void addMemeTextToRecords(MemeText memeText);

    /**
     * Returns the count of a tag in the current meme list.
     * Returns -1 if the tag is not present in the current meme list.
     */
    public int getCountOfTag(Tag tag);

    /**
     * Returns a list of tags with counts.
     */
    List<TagWithCount> getTagsWithCountList();

    /**
     * Clears the image data folder of any memes that are not referenced in weme.
     */
    void cleanMemeStorage();

    /**
     * Clears the image data folder of any templates that are not referenced in Weme.
     */
    void cleanTemplateStorage();
}
