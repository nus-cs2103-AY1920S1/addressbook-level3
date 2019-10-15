package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.UniqueCheatSheetList;

/**
 * Wraps all data at the cheatsheet-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CheatSheetBook implements ReadOnlyCheatSheetBook {

    private final UniqueCheatSheetList cheatSheets;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cheatSheets = new UniqueCheatSheetList();
    }

    public CheatSheetBook() {}

    /**
     * Creates an CheatSheetBook using the Persons in the {@code toBeCopied}
     */
    public CheatSheetBook(ReadOnlyCheatSheetBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code CheatSheetBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCheatSheetBook newData) {
        requireNonNull(newData);

        setCheatSheets(newData.getCheatSheetList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheatSheetBook // instanceof handles nulls
                && cheatSheets.equals(((CheatSheetBook) other).cheatSheets));
    }

    @Override
    public String toString() {
        return cheatSheets.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public int hashCode() {
        return cheatSheets.hashCode();
    }


    //=============================CheatSheet tools====================================================

    /**
     * Adds a cheatSheet to the cheatSheet book.
     * The cheatSheet must not already exist in the cheatSheet book.
     */
    public void addCheatSheet(CheatSheet cs) {
        cheatSheets.add(cs);
    }

    /**
     * Deletes a cheatSheet to the cheatSheet book.
     * The cheatSheet must already exist in the cheatSheet book.
     */
    public void deleteCheatSheet(CheatSheet cs) {
        cheatSheets.remove(cs);
    }

    /**
     * Checks if the list of cheatsheets contains this cheatsheet
     * @param cheatSheet
     * @return
     */
    public boolean hasCheatSheet(CheatSheet cheatSheet) {
        requireNonNull(cheatSheet);
        return cheatSheets.contains(cheatSheet);
    }

    /**
     * Replaces the contents of the cheatsheet list with {@code cheatsheets}.
     * {@code cheatsheets} must not contain duplicate cheatsheets.
     */
    public void setCheatSheets(List<CheatSheet> cheatsheets) {
        this.cheatSheets.setCheatSheets(cheatsheets);
    }

    /**
     * Replaces the given cheatsheet {@code target} in the list with {@code editedCheatSheet}.
     * {@code target} must exist in the StudyBuddy application.
     * The cheatsheet identity of {@code editedCheatSheet}
     * must not be the same as another existing cheatsheet in the StudyBuddy application.
     */
    public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
        requireNonNull(editedCheatSheet);

        cheatSheets.setCheatSheet(target, editedCheatSheet);
    }

    @Override
    public ObservableList<CheatSheet> getCheatSheetList() {
        return cheatSheets.asUnmodifiableObservableList();
    }
}
