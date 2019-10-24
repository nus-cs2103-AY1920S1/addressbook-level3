package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.UniqueMemeList;
import seedu.weme.model.template.Template;
import seedu.weme.model.template.UniqueTemplateList;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.StatsManager;

/**
 * Wraps all data at weme level
 * Duplicates are not allowed (by {@link Meme#isSameMeme(Meme)} and
 * {@link Template#isSameTemplate(Template)} comparisons)
 */
public class Weme implements ReadOnlyWeme {

    private final UniqueMemeList memes;
    private final UniqueTemplateList templates;
    private final Stats stats;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        memes = new UniqueMemeList();
        templates = new UniqueTemplateList();
        stats = new StatsManager();
    }

    public Weme() {}

    /**
     * Creates an Weme using the Memes in the {@code toBeCopied}
     */
    public Weme(ReadOnlyWeme toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setStats(Stats replacement) {
        stats.resetData(replacement);
    }

    //// list overwrite operations

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
     * Resets the existing data of this {@code Weme} with {@code newData}.
     */
    public void resetData(ReadOnlyWeme newData) {
        requireNonNull(newData);

        setMemes(newData.getMemeList());
        setTemplates(newData.getTemplateList());
        setStats(newData.getStats());
    }

    //// meme-level operations

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in weme.
     */
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return memes.contains(meme);
    }

    /**
     * Returns true if a template with the same identity as {@code template} exists in weme.
     */
    public boolean hasTemplate(Template template) {
        requireNonNull(template);
        return templates.contains(template);
    }

    /**
     * Adds a meme to weme.
     * The meme must not already exist in weme.
     */
    public void addMeme(Meme p) {
        memes.add(p);
    }

    /**
     * Adds a template to weme.
     * The template must not already exist in weme.
     */
    public void addTemplate(Template p) {
        templates.add(p);
    }

    /**
     * Replaces the given meme {@code target} in the list with {@code editedMeme}.
     * {@code target} must exist in weme.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in weme.
     */
    public void setMeme(Meme target, Meme editedMeme) {
        requireNonNull(editedMeme);

        memes.setMeme(target, editedMeme);
    }

    /**
     * Replaces the given template {@code target} in the list with {@code editedTemplate}.
     * {@code target} must exist in weme.
     * The template identity of {@code editedTemplate} must not be the same as another existing template in the
     * weme.
     */
    public void setTemplate(Template target, Template editedTemplate) {
        requireNonNull(editedTemplate);

        templates.setTemplate(target, editedTemplate);
    }

    /**
     * Removes {@code key} from this {@code Weme}.
     * {@code key} must exist in weme.
     */
    public void removeMeme(Meme key) {
        memes.remove(key);
    }

    /**
     * Removes {@code key} from this {@code Weme}.
     * {@code key} must exist in weme.
     */
    public void removeTemplate(Template key) {
        templates.remove(key);
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
    public ObservableList<Template> getTemplateList() {
        return templates.asUnmodifiableObservableList();
    }

    public Stats getStats() {
        return stats;
    }

    public LikeData getLikeData() {
        return stats.getLikeData();
    }

    public ObservableMap<String, Integer> getObservableLikeData() {
        return stats.getObservableLikeData();
    }

    public void incrementMemeLikeCount(Meme meme) {
        stats.incrementMemeLikeCount(meme);
    }

    public void clearMemeStats(Meme meme) {
        stats.deleteLikesByMeme(meme);
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
