package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.accommodation.UniqueAccommodationList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactList;
import seedu.address.model.day.Day;
import seedu.address.model.day.Itinerary;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Planner implements ReadOnlyPlanner {
    private String country;
    private final UniqueActivityList activities;
    private final UniqueAccommodationList accommodations;
    private final UniqueContactList contacts;
    private final Itinerary days;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        activities = new UniqueActivityList();
        accommodations = new UniqueAccommodationList();
        contacts = new UniqueContactList();
        days = new Itinerary();
    }

    public Planner() {}

    /**
     * Creates an Planner using the Persons in the {@code toBeCopied}
     */
    public Planner(ReadOnlyPlanner toBeCopied) {
        this();
        resetDataContact(toBeCopied);
        //resetDataActivity(toBeCopied);
        //resetDataAccommodation(toBeCopied);
    }

    //// For ACCOMMODATION list overwrite operations

    /**
     * Resets the existing data of this {@code Planner} with {@code newData}.
     */
    public void resetDataAccommodation(ReadOnlyPlanner newData) {
        requireNonNull(newData);

        setAccommodations(newData.getAccommodationList());
    }

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations.setAccommodations(accommodations);
    }

    //// accommodation-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    public boolean hasAccommodation(Accommodation accommodation) {
        requireNonNull(accommodation);
        return accommodations.contains(accommodation);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addAccommodation(Accommodation a) {
        accommodations.add(a);
    }

    /**
     * Replaces the given contacts {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedPerson} must not be the same as another existing contacts in the
     * address book.
     */
    public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
        requireNonNull(editedAccommodation);

        accommodations.setAccommodation(target, editedAccommodation);
    }

    /**
     * Removes {@code key} from this {@code Planner}.
     * {@code key} must exist in the address book.
     */
    public void removeAccommodation(Accommodation key) {
        accommodations.remove(key);
    }

    //// For ACTIVITY list overwrite operations

    /**
     * Resets the existing data of this {@code Planner} with {@code newData}.
     */
    public void resetDataActivity(ReadOnlyPlanner newData) {
        requireNonNull(newData);

        setActivities(newData.getActivityList());
    }

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setActivities(activities);
    }

    //// activity-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addActivity(Activity a) {
        activities.add(a);
    }

    /**
     * Replaces the given contacts {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedPerson} must not be the same as another existing contacts in the address
     * book.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setActivity(target, editedActivity);
    }

    /**
     * Removes {@code key} from this {@code Planner}.
     * {@code key} must exist in the address book.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    //// For CONTACT list overwrite operations

    /**
     * Resets the existing data of this {@code Planner} with {@code newData}.
     */
    public void resetDataContact(ReadOnlyPlanner newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
    }

    /**
     * Replaces the contents of the contacts list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    //// contacts-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addContact(Contact c) {
        contacts.add(c);
    }

    /**
     * Replaces the given contacts {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the
     * address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code Planner}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        contacts.remove(key);
    }

    //// For DAY list overwrite operations

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDays(int n) {
        this.days.setDays(n);
    }

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDays(List<Day> days) {
        this.days.setDays(days);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addDays(int n) {
        this.days.addDays(n);
    }

    /**
     * Removes {@code key} from this {@code Planner}.
     * {@code key} must exist in the address book.
     */
    public void removeDay(int n) {
        this.days.removeDay(n);
    }

    //// util methods

    @Override
    public String toString() {
        return accommodations.asUnmodifiableObservableList().size() + " accommodations, "
               + activities.asUnmodifiableObservableList().size() + " activities, "
               + contacts.asUnmodifiableObservableList().size() + " contacts.";
    }

    @Override
    public ObservableList<Accommodation> getAccommodationList() {
        return accommodations.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Activity> getActivityList() {
        return activities.asUnmodifiableObservableList();
    }


    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Planner // instanceof handles nulls
                && accommodations.equals(((Planner) other).accommodations))
                && activities.equals(((Planner) other).activities)
                && contacts.equals(((Planner) other).contacts)
                && days.equals(((Planner) other).days);
    }

    @Override
    public int hashCode() {
        return accommodations.hashCode() * activities.hashCode() * contacts.hashCode();
    }
}
