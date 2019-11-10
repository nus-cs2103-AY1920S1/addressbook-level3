package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.UniqueMemeList;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.records.Records;
import seedu.weme.model.records.RecordsManager;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.StatsManager;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.statistics.TagWithDislike;
import seedu.weme.model.statistics.TagWithLike;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;
import seedu.weme.model.template.UniqueTemplateList;
import seedu.weme.model.template.exceptions.MemeCreationException;
import seedu.weme.model.util.ImageUtil;

/**
 * Wraps all data at Weme level
 * Duplicates are not allowed (by {@link Meme#isSameMeme(Meme)} and
 * {@link Template#isSameTemplate(Template)} comparisons)
 */
public class Weme implements ReadOnlyWeme {

    private final UniqueMemeList memes;
    private final UniqueMemeList exportList;
    private final UniqueMemeList importList;
    private final UniqueTemplateList templates;
    private final Stats stats;
    private final Records records;
    private final MemeCreation memeCreation;
    private SimpleObjectProperty<Meme> viewableMeme;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        memes = new UniqueMemeList();
        exportList = new UniqueMemeList();
        importList = new UniqueMemeList();
        templates = new UniqueTemplateList();
        stats = new StatsManager();
        records = new RecordsManager();
        memeCreation = new MemeCreation();
        viewableMeme = new SimpleObjectProperty<>();
    }

    public Weme() {
    }

    /**
     * Creates an Weme using the Memes in the {@code toBeCopied}
     */
    public Weme(ReadOnlyWeme toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// overwrite operations

    /**
     * Resets the existing data of this {@code Weme} with {@code newData}.
     */
    public void resetData(ReadOnlyWeme newData) {
        requireNonNull(newData);

        // load stats first as memes require stats loaded to prevent error
        setStats(newData.getStats());
        setMemes(newData.getMemeList());
        setTemplates(newData.getTemplateList());
        updateStats(newData.getMemeList());
        setRecords(newData.getRecords());
        setMemeCreation(newData.getMemeCreation());
        setStagedMemes(newData.getStagedMemeList());
    }

    /**
     * Updates {@code Stats} with the latest {@code MemeList}.
     */
    private void updateStats(List<Meme> memeList) {
        for (Meme meme : memeList) {
            if (stats.getLikesByMeme(meme) == Integer.MAX_VALUE) {
                stats.addDefaultLikeData(meme);
            }
            if (stats.getDislikesByMeme(meme) == Integer.MAX_VALUE) {
                stats.addDefaultDislikeData(meme);
            }
        }
    }

    /**
     * Replaces the contents of the meme list with {@code memes}.
     * {@code memes} must not contain duplicate memes.
     */
    public void setMemes(List<Meme> memes) {
        this.memes.setMemes(memes);
    }

    /**
     * Replaces the contents of the template list with {@code templates}.
     * {@code templates} must not contain duplicate templates.
     */
    public void setTemplates(List<Template> templates) {
        this.templates.setTemplates(templates);
    }

    /**
     * Replaces the contents of the export list with {@code memes}.
     * {@code memes} must not contain duplicate memes.
     */
    public void setStagedMemes(List<Meme> memes) {
        this.exportList.setMemes(memes);
    }

    public void startMemeCreation(Template template) throws IOException {
        memeCreation.startWithTemplate(template);
    }

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in Weme.
     */
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return memes.contains(meme);
    }

    /**
     * Stages a meme to the staging area.
     *
     * @param meme meme to stage
     */
    public void stageMeme(Meme meme) {
        exportList.add(meme);
    }

    //// meme-level operations

    /**
     * Unstages a meme from the staging area.
     *
     * @param meme meme to unstage
     */
    public void unstageMeme(Meme meme) {
        exportList.remove(meme);
    }

    /**
     * Transfers all memes from importList into storage.
     */
    public void importMeme(Path internalImagePath) throws IOException {
        for (Meme meme : importList) {
            Meme copiedMeme = ImageUtil.copyMeme(meme, internalImagePath);
            addMeme(copiedMeme);
        }
    }

    public void clearImportList() {
        importList.clear();
    }

    public void clearExportList() {
        exportList.clear();
    }

    /**
     * Loads meme from given directory to staging area.
     *
     * @param pathList
     */
    public void loadMemes(List<Path> pathList) {
        for (Path path : pathList) {
            Meme meme = new Meme(new ImagePath(path.toString()));
            importList.add(meme);
        }
    }

    public List<Path> getExportPathList() {
        return exportList.asPathList();
    }

    /**
     * Returns true if a template with the same identity as {@code template} exists in Weme.
     */
    public boolean hasTemplate(Template template) {
        requireNonNull(template);
        return templates.contains(template);
    }

    /**
     * Returns true if the meme exists in the export list.
     */
    public boolean isMemeStaged(Meme meme) {
        requireNonNull(meme);
        return exportList.contains(meme);
    }

    /**
     * Returns true if the export list is empty.
     */
    public boolean isStagingAreaEmpty() {
        return exportList.isEmpty();
    }

    /**
     * Adds a meme to Weme.
     * The meme must not already exist in Weme.
     */
    public void addMeme(Meme m) {
        stats.addDefaultLikeData(m);
        stats.addDefaultDislikeData(m);
        memes.add(m);
    }

    /**
     * Adds a template to Weme.
     * The template must not already exist in Weme.
     */
    public void addTemplate(Template p) {
        templates.add(p);
    }

    /**
     * Replaces the given meme {@code target} in the list with {@code editedMeme}.
     * {@code target} must exist in Weme.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in Weme.
     */
    public void setMeme(Meme target, Meme editedMeme) {
        requireNonNull(editedMeme);

        memes.setMeme(target, editedMeme);
    }

    public void setViewableMeme(Meme meme) {
        viewableMeme.setValue(meme);
    }

    /**
     * Replaces the given meme {@code target} in the list with {@code editedMeme}.
     * {@code target} must exist in Weme.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in Weme.
     */
    public void setImportedMeme(Meme target, Meme editedMeme) {
        requireNonNull(editedMeme);

        importList.setMeme(target, editedMeme);
    }

    /**
     * Replaces the given template {@code target} in the list with {@code editedTemplate}.
     * {@code target} must exist in Weme.
     * The template identity of {@code editedTemplate} must not be the same as another existing template in the
     * Weme.
     */
    public void setTemplate(Template target, Template editedTemplate) {
        requireNonNull(editedTemplate);

        templates.setTemplate(target, editedTemplate);
    }

    /**
     * Removes {@code key} from this {@code Weme}.
     * {@code key} must exist in Weme.
     */
    public void removeMeme(Meme key) {
        memes.remove(key);
    }

    /**
     * Removes {@code key} from this {@code Weme}.
     * {@code key} must exist in Weme.
     */
    public void removeImportedMeme(Meme key) {
        importList.remove(key);
    }

    /**
     * Removes {@code key} from this {@code Weme}.
     * {@code key} must exist in Weme.
     */
    public void removeTemplate(Template key) {
        templates.remove(key);
    }

    @Override
    public MemeCreation getMemeCreation() {
        return memeCreation;
    }

    /**
     * Replaces the current meme creation session with {@code memeCreation}
     */
    public void setMemeCreation(MemeCreation replacement) {
        memeCreation.resetSession(replacement);
    }

    //// Meme Creation methods

    public void abortMemeCreation() {
        memeCreation.abort();
    }

    @Override
    public ObservableList<MemeText> getMemeTextList() {
        return memeCreation.getMemeTextList();
    }

    public void addMemeText(MemeText memeText) {
        memeCreation.addText(memeText);
    }

    public void deleteMemeText(MemeText toDelete) {
        memeCreation.deleteText(toDelete);
    }

    public void setMemeText(MemeText toReplace, MemeText replacement) {
        memeCreation.setText(toReplace, replacement);
    }

    @Override
    public Optional<BufferedImage> getMemeCreationImage() {
        return memeCreation.getCurrentImage();
    }

    public void createMeme(Path path) throws MemeCreationException {
        memeCreation.generate(path);
    }

    @Override
    public String toString() {
        return memes.asUnmodifiableObservableList().size() + " memes and "
                + templates.asUnmodifiableObservableList().size() + " templates";
        // TODO: refine later
    }

    @Override
    public ObservableList<Meme> getMemeList() {
        return memes.asUnmodifiableObservableList();
    }

    //// util methods

    @Override
    public ObservableList<Meme> getStagedMemeList() {
        return exportList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Meme> getImportList() {
        return importList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableValue<Meme> getViewableMeme() {
        return viewableMeme;
    }

    public ObservableList<Template> getTemplateList() {
        return templates.asUnmodifiableObservableList();
    }

    @Override
    public Stats getStats() {
        return stats.getStats();
    }

    public void setStats(Stats replacement) {
        stats.resetData(replacement);
    }

    // ============== Stats data Methods ===============================

    public int getLikesByMeme(Meme meme) {
        return stats.getLikesByMeme(meme);
    }

    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return stats.getObservableLikeData();
    }

    public void incrementMemeLikeCount(Meme meme) {
        stats.incrementMemeLikeCount(meme);
    }

    public void decrementMemeLikeCount(Meme meme) {
        stats.decrementMemeLikeCount(meme);
    }

    public int getDislikesByMeme(Meme meme) {
        return stats.getDislikesByMeme(meme);
    }

    public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
        return stats.getObservableDislikeData();
    }

    public void incrementMemeDislikeCount(Meme meme) {
        stats.incrementMemeDislikeCount(meme);
    }

    public void decrementMemeDislikeCount(Meme meme) {
        stats.decrementMemeDislikeCount(meme);
    }

    /**
     * Remove stats data of a meme.
     */
    public void clearMemeStats(Meme meme) {
        stats.deleteLikesByMeme(meme);
        stats.deleteDislikesByMeme(meme);
    }

    public int getCountOfTag(Tag tag) {
        return stats.getCountOfTag(getMemeList(), tag);
    }

    // ============== Tag Data Methods ===============================

    public List<TagWithCount> getTagsWithCountList() {
        return stats.getTagsWithCountList(getMemeList());
    }

    public List<TagWithDislike> getTagsWithDislikeCountList() {
        return stats.getTagsWithDislikeCountList(getMemeList());
    }

    public List<TagWithLike> getTagsWithLikeCountList() {
        return stats.getTagsWithLikeCountList(getMemeList());
    }

    // ============== Records Methods ===============================

    @Override
    public Records getRecords() {
        return records;
    }

    public void setRecords(Records replacement) {
        records.resetRecords(replacement);
    }

    public Set<String> getPaths() {
        return records.getPaths();
    }

    public Set<String> getDescriptions() {
        return records.getDescriptions();
    }

    public Set<String> getTags() {
        return records.getTags();
    }

    public Set<String> getNames() {
        return records.getNames();
    }

    public Set<String> getTexts() {
        return records.getTexts();
    }

    public void addPath(ImagePath path) {
        records.addPath(path);
    }

    public void addDescription(Description description) {
        records.addDescription(description);
    }

    public void addTags(Set<Tag> tags) {
        records.addTags(tags);
    }

    public void addName(Name name) {
        records.addName(name);
    }

    public void addText(String text) {
        records.addText(text);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Weme)) {
            return false;
        }

        Weme otherWeme = (Weme) other;
        return memes.equals(otherWeme.memes)
                && templates.equals(otherWeme.templates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memes, templates);
    }
}
