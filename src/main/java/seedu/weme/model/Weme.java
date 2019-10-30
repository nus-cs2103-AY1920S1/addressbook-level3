package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.UniqueMemeList;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.StatsManager;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.statistics.TagWithLike;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;
import seedu.weme.model.template.UniqueTemplateList;
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
    }

    public Weme() {}

    /**
     * Creates an Weme using the Memes in the {@code toBeCopied}
     */
    public Weme(ReadOnlyWeme toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// overwrite operations

    public void setStats(Stats replacement) {
        stats.resetData(replacement);
    }

    public void setRecords(Records replacement) {
        records.resetRecords(replacement);
    }

    /**
     * Resets the existing data of this {@code Weme} with {@code newData}.
     */
    public void resetData(ReadOnlyWeme newData) {
        requireNonNull(newData);

        setMemes(newData.getMemeList());
        setTemplates(newData.getTemplateList());
        setStats(newData.getStats());
        setRecords(newData.getRecords());
        setMemeCreation(newData.getMemeCreation());
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

    public void startMemeCreation(Template template) throws IOException {
        memeCreation.startWithTemplate(template);
    }

    //// meme-level operations

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
     * Adds a meme to Weme.
     * The meme must not already exist in Weme.
     */
    public void addMeme(Meme p) {
        memes.add(p);
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
    public void removeTemplate(Template key) {
        templates.remove(key);
    }

    //// Meme Creation methods

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

    public void abortMemeCreation() {
        memeCreation.abort();
    }

    //// util methods

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

    @Override
    public ObservableList<Meme> getStagedMemeList() {
        return exportList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Meme> getImportList() {
        return importList.asUnmodifiableObservableList();
    }

    public ObservableList<Template> getTemplateList() {
        return templates.asUnmodifiableObservableList();
    }

    // ============== Stats data Methods ===============================

    @Override
    public Stats getStats() {
        return stats;
    }

    public int getLikesByMeme(Meme meme) {
        return stats.getLikesByMeme(meme);
    }

    public ObservableMap<String, Integer> getObservableLikeData() {
        return stats.getObservableLikeData();
    }

    public void incrementMemeLikeCount(Meme meme) {
        stats.incrementMemeLikeCount(meme);
    }

    public void decrementMemeLikeCount(Meme meme) {
        stats.decrementMemeLikeCount(meme);
    }

    public void clearMemeStats(Meme meme) {
        stats.deleteLikesByMeme(meme);
    }

    // ============== Tag Data Methods ===============================

    public int getCountOfTag(Tag tag) {
        return stats.getCountOfTag(getMemeList(), tag);
    }

    public List<TagWithCount> getTagsWithCountList() {
        return stats.getTagsWithCountList(getMemeList());
    }

    // ============== Records Methods ===============================

    public List<TagWithLike> getTagsWithLikeCountList() {
        return stats.getTagsWithLikeCountList(getMemeList());
    };

    @Override
    public Records getRecords() {
        return records;
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

    public Set<String> getColors() {
        return records.getColors();
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

    public void addColor(Color color) {
        records.addColor(color);
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
