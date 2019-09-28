package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Meme;
import seedu.address.model.person.UniqueMemeList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

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

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setMemes(newData.getMemeList());
    }

    //// meme-level operations

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in the address book.
     */
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return memes.contains(meme);
    }

    /**
     * Adds a meme to the address book.
     * The meme must not already exist in the address book.
     */
    public void addMeme(Meme p) {
        memes.add(p);
    }

    /**
     * Replaces the given meme {@code target} in the list with {@code editedMeme}.
     * {@code target} must exist in the address book.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in the address book.
     */
    public void setMeme(Meme target, Meme editedMeme) {
        requireNonNull(editedMeme);

        memes.setMeme(target, editedMeme);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeMeme(Meme key) {
        memes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return memes.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Meme> getMemeList() {
        return memes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && memes.equals(((AddressBook) other).memes));
    }

    @Override
    public int hashCode() {
        return memes.hashCode();
    }
}
