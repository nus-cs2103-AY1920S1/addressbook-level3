package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.TripList;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TravelPal implements ReadOnlyTravelPal {

    private final UniquePersonList persons;
    private final TripList tripList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tripList = new TripList();
    }

    public TravelPal() {}

    /**
     * Creates an TravelPal using the Persons in the {@code toBeCopied}
     */
    public TravelPal(ReadOnlyTravelPal toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setTripList(List<Trip> trips){
        this.tripList.set(trips);
    }

    /**
     * Resets the existing data of this {@code TravelPal} with {@code newData}.
     */
    public void resetData(ReadOnlyTravelPal newData) {
        requireNonNull(newData);

        setTripList(newData.getTripList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code TravelPal}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TravelPal // instanceof handles nulls
                && persons.equals(((TravelPal) other).persons)
                && tripList.equals(((TravelPal) other).tripList));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    // trip-level operations
    public boolean hasClashingTrip(Trip trip){
        requireNonNull(trip);
        return tripList.containsClashing(trip);
    }

    public void addTrip(Trip trip) throws ClashingTripException {
        tripList.add(trip);
    }

    public void setTrip(Trip target, Trip replacement) throws ClashingTripException, TripNotFoundException {
        tripList.set(target, replacement);
    }

    public void deleteTrip(Trip trip) throws TripNotFoundException {
        tripList.remove(trip);
    }

    public boolean hasTrip(Trip trip){
        requireNonNull(trip);
        return tripList.contains(trip);
    }

    @Override
    public ObservableList<Trip> getTripList() {
        return tripList.asUnmodifiableObservableList();
    }
}
