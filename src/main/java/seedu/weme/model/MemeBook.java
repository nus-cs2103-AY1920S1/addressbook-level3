package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.UniqueMemeList;

/**
 * Wraps all data at the weme-book level
 * Duplicates are not allowed (by .isSameMeme comparison)
 */
public class MemeBook implements ReadOnlyMemeBook {

    private final UniqueMemeList memes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        memes = new UniqueMemeList();
    }

    public MemeBook() {}

    /**
     * Creates an MemeBook using the Memes in the {@code toBeCopied}
     */
    public MemeBook(ReadOnlyMemeBook toBeCopied) {
        this();
        resetData(toBeCopied);
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
     * Resets the existing data of this {@code MemeBook} with {@code newData}.
     */
    public void resetData(ReadOnlyMemeBook newData) {
        requireNonNull(newData);

        setMemes(newData.getMemeList());
    }

    //// meme-level operations

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in the meme book.
     */
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return memes.contains(meme);
    }

    /**
     * Adds a meme to the meme book.
     * The meme must not already exist in the meme book.
     */
    public void addMeme(Meme p) {
        memes.add(p);
    }

    /**
     * Replaces the given meme {@code target} in the list with {@code editedMeme}.
     * {@code target} must exist in the meme book.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in the meme book.
     */
    public void setMeme(Meme target, Meme editedMeme) {
        requireNonNull(editedMeme);

        memes.setMeme(target, editedMeme);
    }

    /**
     * Removes {@code key} from this {@code MemeBook}.
     * {@code key} must exist in the meme book.
     */
    public void removeMeme(Meme key) {
        memes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return memes.asUnmodifiableObservableList().size() + " memes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Meme> getMemeList() {
        return memes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeBook // instanceof handles nulls
                && memes.equals(((MemeBook) other).memes));
    }

    @Override
    public int hashCode() {
        return memes.hashCode();
    }
}
