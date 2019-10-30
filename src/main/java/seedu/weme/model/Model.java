package seedu.weme.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.Template;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Meme> PREDICATE_SHOW_ALL_MEMES = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Template> PREDICATE_SHOW_ALL_TEMPLATES = unused -> true;

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
     * Replaces meme data with the data in {@code memes}.
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
     * Loads the memes to import staging area.
     */
    void loadMemes(List<Path> pathList);

    /**
     * Replaces the given meme {@code target} with {@code editedMeme}.
     * {@code target} must exist in Weme.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in Weme.
     */
    void setMeme(Meme target, Meme editedMeme);

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
    ObservableList<Meme> getFilteredStagedMemeList();

    /**
     * Returns an unmodifiable view of the import meme list.
     */
    ObservableList<Meme> getFilteredImportList();

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
     * Returns the current meme creation session.
     * @return the current meme creation session.
     */
    MemeCreation getMemeCreation();

    /**
     * Aborts the current meme creation session.
     **/
    void abortMemeCreation();

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
     * Returns the number of likes of a meme.
     */
    int getLikesByMeme(Meme meme);

    /**
     * Returns the like data.
     */
    ObservableMap<String, Integer> getObservableLikeData();

    /**
     * Increments likes of a meme by the Meme object.
     */
    void incrementMemeLikeCount(Meme meme);

    /**
     * Deletes stats data by meme.
     */
    void clearMemeStats(Meme memeToDelete);

    /**
     * Clears the list memes in export tab after exporting.
     */
    void clearExportList();


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
     * Add information of a meme to the records.
     */
    void addMemeToRecord(Meme meme);

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
