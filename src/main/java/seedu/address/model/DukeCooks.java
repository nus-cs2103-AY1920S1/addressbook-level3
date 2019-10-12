package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class DukeCooks implements ReadOnlyDukeCooks {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public DukeCooks() {}

    /**
     * Creates a DukeCooks using the Persons in the {@code toBeCopied}
     */
    public DukeCooks(ReadOnlyDukeCooks toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the diary list with {@code diaries}.
     * {@code diaries} must not contain duplicate diaries.
     */
    public void setPersons(List<Diary> diaries) {
        this.persons.setPersons(diaries);
    }

    /**
     * Resets the existing data of this {@code DukeCooks} with {@code newData}.
     */
    public void resetData(ReadOnlyDukeCooks newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// diary-level operations

    /**
     * Returns true if a diary with the same identity as {@code diary} exists in Duke Cooks.
     */
    public boolean hasPerson(Diary diary) {
        requireNonNull(diary);
        return persons.contains(diary);
    }

    /**
     * Adds a diary to Duke Cooks.
     * The diary must not already exist in Duke Cooks.
     */
    public void addPerson(Diary p) {
        persons.add(p);
    }

    /**
     * Replaces the given diary {@code target} in the list with {@code editedDiary}.
     * {@code target} must exist in Duke Cooks.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in Duke Cooks.
     */
    public void setPerson(Diary target, Diary editedDiary) {
        requireNonNull(editedDiary);

        persons.setPerson(target, editedDiary);
    }

    /**
     * Removes {@code key} from this {@code DukeCooks}.
     * {@code key} must exist in Duke Cooks.
     */
    public void removePerson(Diary key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Diary> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DukeCooks // instanceof handles nulls
                && persons.equals(((DukeCooks) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
