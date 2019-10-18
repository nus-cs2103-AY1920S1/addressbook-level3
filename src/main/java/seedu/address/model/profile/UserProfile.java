package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.profile.person.Person;
import seedu.address.model.profile.person.UniqueUserProfile;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecord comparison)
 */
public class UserProfile implements ReadOnlyUserProfile {

    private final UniqueUserProfile userprofile;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        userprofile = new UniqueUserProfile();
    }

    public UserProfile() {}

    /**
     * Creates a UserProfile using the Persons in the {@code toBeCopied}
     */
    public UserProfile(ReadOnlyUserProfile dukeCooks) {
        this();
        resetData(dukeCooks);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code userprofile}.
     * {@code userprofile} must not contain duplicate userprofile.
     */
    public void setUserprofile(List<Person> userprofile) {
        this.userprofile.setPersons(userprofile);
    }

    /**
     * Resets the existing data of this {@code UserProfile} with {@code newData}.
     */
    public void resetData(ReadOnlyUserProfile newData) {
        requireNonNull(newData);

        setUserprofile(newData.getUserProfileList());
    }

    //// person-level operations
    /**
     * Adds a person to Duke Cooks.
     * The person must not already exist in Duke Cooks.
     */
    public void addPerson(Person p) {
        userprofile.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in Duke Cooks.
     * The person identity of {@code editedPerson} must not be the same as another existing person in Duke Cooks.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        userprofile.setPerson(target, editedPerson);
    }

    //// util methods

    @Override
    public String toString() {
        return userprofile.asUnmodifiableObservableList().size() + " userprofile";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getUserProfileList() {
        return userprofile.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserProfile // instanceof handles nulls
                && userprofile.equals(((UserProfile) other).userprofile));
    }

    @Override
    public int hashCode() {
        return userprofile.hashCode();
    }
}
