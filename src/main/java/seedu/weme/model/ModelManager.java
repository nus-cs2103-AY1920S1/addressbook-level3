package seedu.weme.model;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.Template;
import seedu.weme.model.template.exceptions.MemeCreationException;

/**
 * Represents the in-memory model of Weme data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedWeme versionedWeme;
    private final UserPrefs userPrefs;
    private final FilteredList<Meme> filteredMemes;
    private final ObservableList<Meme> stagedMemeList;
    private final ObservableList<Meme> importMemeList;
    private final FilteredList<Template> filteredTemplates;
    private ObservableValue<Meme> viewableMeme;

    // ModelContext determines which parser to use at any point of time.
    private SimpleObjectProperty<ModelContext> context = new SimpleObjectProperty<>(ModelContext.CONTEXT_MEMES);

    /**
     * Initializes a ModelManager with the given Weme and userPrefs.
     */
    public ModelManager(ReadOnlyWeme weme, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(weme, userPrefs);

        logger.fine("Initializing with Weme: " + weme + " and user prefs " + userPrefs);

        versionedWeme = new VersionedWeme(weme);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMemes = new FilteredList<>(versionedWeme.getMemeList(), PREDICATE_SHOW_ALL_UNARCHIVED_MEMES);
        stagedMemeList = versionedWeme.getStagedMemeList();
        importMemeList = versionedWeme.getImportList();
        viewableMeme = versionedWeme.getViewableMeme();
        filteredTemplates = new FilteredList<>(versionedWeme.getTemplateList(),
                PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES);
    }

    public ModelManager() {
        this(new Weme(), new UserPrefs());
    }

    //=========== Export/Import ==============================================================================

    @Override
    public List<Path> getExportPathList() {
        return versionedWeme.getExportPathList();
    }

    @Override
    public void importMemes() throws IOException {
        versionedWeme.importMeme(getMemeImagePath());
    }

    @Override
    public void loadMemes(List<Path> pathList) {
        versionedWeme.loadMemes(pathList);
    }

    @Override
    public void clearExportList() {
        versionedWeme.clearExportList();
    }

    @Override
    public void clearImportList() {
        versionedWeme.clearImportList();
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public ObservableMap<String, String> getObservableUserPreferences() {
        return userPrefs.getObservableUserPreferences();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getDataFilePath() {
        return userPrefs.getDataFilePath();
    }

    @Override
    public Path getDefaultExportPath() {
        return userPrefs.getDefaultExportPath();
    }

    @Override
    public void setDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        userPrefs.setDataFilePath(dataFilePath);
    }

    @Override
    public Path getMemeImagePath() {
        return userPrefs.getMemeImagePath();
    }

    @Override
    public ObservableValue<Meme> getViewableMeme() {
        return viewableMeme;
    };

    @Override
    public void setViewableMeme(Meme meme) {
        versionedWeme.setViewableMeme(meme);
    }

    @Override
    public void setMemeImagePath(Path memeImagePath) {
        requireNonNull(memeImagePath);
        userPrefs.setMemeImagePath(memeImagePath);
    }

    @Override
    public Path getTemplateImagePath() {
        return userPrefs.getTemplateImagePath();
    }

    @Override
    public void setTemplateImagePath(Path templateImagePath) {
        requireNonNull(templateImagePath);
        userPrefs.setTemplateImagePath(templateImagePath);
    }

    //=========== Weme ================================================================================

    @Override
    public void setWeme(ReadOnlyWeme weme) {
        this.versionedWeme.resetData(weme);
    }

    @Override
    public ReadOnlyWeme getWeme() {
        return versionedWeme;
    }

    @Override
    public void clearMemes() {
        versionedWeme.setMemes(new ArrayList<>());
    }

    @Override
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return versionedWeme.hasMeme(meme);
    }

    @Override
    public void deleteMeme(Meme target) {
        versionedWeme.removeMeme(target);
    }

    @Override
    public void deleteImportedMeme(Meme target) {
        versionedWeme.removeImportedMeme(target);
    }

    @Override
    public void addMeme(Meme meme) {
        versionedWeme.addMeme(meme);
        updateFilteredMemeList(PREDICATE_SHOW_ALL_UNARCHIVED_MEMES);
    }

    @Override
    public void setMeme(Meme target, Meme editedMeme) {
        requireAllNonNull(target, editedMeme);

        versionedWeme.setMeme(target, editedMeme);
    }

    @Override
    public boolean isMemeStaged(Meme meme) {
        return versionedWeme.isMemeStaged(meme);
    }

    @Override
    public boolean isStagingAreaEmpty() {
        return versionedWeme.isStagingAreaEmpty();
    }

    @Override
    public void setImportedMeme(Meme target, Meme editedMeme) {
        requireAllNonNull(target, editedMeme);

        versionedWeme.setImportedMeme(target, editedMeme);
    }

    @Override
    public void stageMeme(Meme meme) {
        versionedWeme.stageMeme(meme);
    }

    @Override
    public void unstageMeme(Meme meme) {
        versionedWeme.unstageMeme(meme);
    }

    @Override
    public boolean hasTemplate(Template template) {
        requireNonNull(template);
        return versionedWeme.hasTemplate(template);
    }

    @Override
    public void deleteTemplate(Template template) {
        versionedWeme.removeTemplate(template);
    }

    @Override
    public void clearTemplates() {
        versionedWeme.setTemplates(new ArrayList<>());
    }

    @Override
    public void addTemplate(Template template) {
        versionedWeme.addTemplate(template);
        updateFilteredTemplateList(PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES);
    }

    @Override
    public void setTemplate(Template target, Template editedTemplate) {
        requireAllNonNull(target, editedTemplate);
        versionedWeme.setTemplate(target, editedTemplate);
    }

    //=========== Filtered Meme/Template List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Meme} backed by the internal list of
     * {@code versionedWeme}
     */
    @Override
    public ObservableList<Meme> getFilteredMemeList() {
        return filteredMemes;
    }

    @Override
    public ObservableList<Meme> getStagedMemeList() {
        return stagedMemeList;
    }

    @Override
    public ObservableList<Meme> getImportList() {
        return importMemeList;
    }

    @Override
    public void updateFilteredMemeList(Predicate<Meme> predicate) {
        requireNonNull(predicate);
        filteredMemes.setPredicate(predicate);
    }

    @Override
    public ObservableList<Template> getFilteredTemplateList() {
        return filteredTemplates;
    }

    @Override
    public void updateFilteredTemplateList(Predicate<Template> predicate) {
        requireNonNull(predicate);
        filteredTemplates.setPredicate(predicate);
    }

    @Override
    public void setContext(ModelContext context) {
        this.context.setValue(context);
    }

    @Override
    public ObservableValue<ModelContext> getContext() {
        return context;
    }

    @Override
    public void startMemeCreation(Template template) throws IOException {
        versionedWeme.startMemeCreation(template);
    }

    @Override
    public void abortMemeCreation() {
        versionedWeme.abortMemeCreation();
    }

    @Override
    public ObservableList<MemeText> getMemeTextList() {
        return versionedWeme.getMemeTextList();
    }

    @Override
    public void addMemeText(MemeText text) {
        versionedWeme.addMemeText(text);
    }

    @Override
    public void deleteMemeText(MemeText toDelete) {
        versionedWeme.deleteMemeText(toDelete);
    }

    @Override
    public void setMemeText(MemeText toReplace, MemeText replacement) {
        versionedWeme.setMemeText(toReplace, replacement);
    }

    @Override
    public Optional<BufferedImage> getMemeCreationImage() {
        return versionedWeme.getMemeCreationImage();
    }

    @Override
    public void createMeme(Path path) throws MemeCreationException {
        versionedWeme.createMeme(path);
    }

    @Override
    public boolean canUndoWeme() {
        return versionedWeme.canUndo();
    }

    @Override
    public boolean canRedoWeme() {
        return versionedWeme.canRedo();
    }

    @Override
    public String undoWeme() {
        return versionedWeme.undo();
    }

    @Override
    public String redoWeme() {
        return versionedWeme.redo();
    }

    @Override
    public void commitWeme(String feedback) {
        versionedWeme.commit(feedback);
    }

    //=========== Statistics Methods =============================================================

    @Override
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return versionedWeme.getObservableLikeData();
    }

    @Override
    public void incrementMemeLikeCount(Meme meme) {
        versionedWeme.incrementMemeLikeCount(meme);
    }

    @Override
    public void decrementMemeLikeCount(Meme meme) {
        versionedWeme.decrementMemeLikeCount(meme);
    }

    @Override
    public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
        return versionedWeme.getObservableDislikeData();
    }

    @Override
    public void incrementMemeDislikeCount(Meme meme) {
        versionedWeme.incrementMemeDislikeCount(meme);
    }

    @Override
    public void decrementMemeDislikeCount(Meme meme) {
        versionedWeme.decrementMemeDislikeCount(meme);
    }

    @Override
    public int getCountOfTag(Tag tag) {
        return versionedWeme.getCountOfTag(tag);
    }

    @Override
    public List<TagWithCount> getTagsWithCountList() {
        return versionedWeme.getTagsWithCountList();
    }

    @Override
    public void clearMemeStats(Meme meme) {
        versionedWeme.clearMemeStats(meme);
    }

    //=========== Records method ================================================================================

    @Override
    public Set<String> getPathRecords() {
        return versionedWeme.getPaths();
    }

    @Override
    public Set<String> getDescriptionRecords() {
        return versionedWeme.getDescriptions();
    }

    @Override
    public Set<String> getTagRecords() {
        return versionedWeme.getTags();
    }

    @Override
    public Set<String> getNameRecords() {
        return versionedWeme.getNames();
    }

    @Override
    public Set<String> getTextRecords() {
        return versionedWeme.getTexts();
    }

    @Override
    public void addMemeToRecords(Meme meme) {
        versionedWeme.addPath(meme.getImagePath());
        versionedWeme.addDescription(meme.getDescription());
        versionedWeme.addTags(meme.getTags());
    }

    @Override
    public void addTemplateToRecords(Template template) {
        versionedWeme.addPath(template.getImagePath());
        versionedWeme.addName(template.getName());
    }

    @Override
    public void addMemeTextToRecords(MemeText memeText) {
        versionedWeme.addText(memeText.getText());
    }

    @Override
    public void cleanMemeStorage() {
        logger.info("Attempting to delete unreferenced meme images");
        try {
            Set<File> filesToKeep = new HashSet<>();
            for (Meme meme : versionedWeme.getMemeList()) {
                File file = meme.getImagePath().getFilePath().toFile();
                filesToKeep.add(file);
            }

            Files.list(getMemeImagePath())
                    .map(Path::toFile)
                    .filter(file -> !filesToKeep.contains(file))
                    .forEach(File::delete);
            logger.info("Successfully deleted unreferenced meme images");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void cleanTemplateStorage() {
        logger.info("Attempting to delete unreferenced templates images");
        try {
            Set<File> filesToKeep = new HashSet<>();
            for (Template template : versionedWeme.getTemplateList()) {
                File file = template.getImagePath().getFilePath().toFile();
                filesToKeep.add(file);
            }

            Files.list(getTemplateImagePath())
                    .map(Path::toFile)
                    .filter(file -> !filesToKeep.contains(file))
                    .forEach(File::delete);
            logger.info("Successfully deleted unreferenced template images");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        Optional<Meme> viewingMeme = Optional.ofNullable(viewableMeme.getValue());
        Optional<Meme> otherViewingMeme = Optional.ofNullable(other.getViewableMeme().getValue());
        return versionedWeme.equals(other.versionedWeme)
                && userPrefs.equals(other.userPrefs)
                && filteredMemes.equals(other.filteredMemes)
                && viewingMeme.equals(otherViewingMeme)
                && stagedMemeList.equals(other.stagedMemeList)
                && importMemeList.equals(other.importMemeList)
                && context.getValue().equals(other.context.getValue());
    }

}
