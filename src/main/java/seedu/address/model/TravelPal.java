package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.currency.CurrencyList;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.itinerary.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.TripList;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class TravelPal implements ReadOnlyTravelPal {

    private final UniquePersonList persons;
    private final TripList tripList;
    private final CurrencyList currencies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tripList = new TripList();
        currencies = new CurrencyList();

    }

    public TravelPal() {
        this.addCurrency(new CustomisedCurrency(new Name("SGD"), new Symbol("1"), new Rate("1.00")));
    }

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

    public void setTripList(List<Trip> trips) {
        this.tripList.set(trips);
    }

    public void setCurrencies(List<CustomisedCurrency> currencies) {
        this.currencies.set(currencies);
    }

    /**
     * Resets the existing data of this {@code TravelPal} with {@code newData}.
     */
    public void resetData(ReadOnlyTravelPal newData) {
        requireNonNull(newData);

        setTripList(newData.getTripList());

        setCurrencies(newData.getCurrencies());
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

    public void addTrip(Trip trip) throws ClashingTripException {
        tripList.add(trip);
    }

    public void setTrip(Trip target, Trip replacement) throws ClashingTripException, TripNotFoundException {
        tripList.set(target, replacement);
    }

    public void deleteTrip(Trip trip) throws TripNotFoundException {
        tripList.remove(trip);
    }

    /**
     * Convenience wrapper for checking whether the specified trip exists.
     *
     * @param trip The trip instance to check.
     * @return Boolean of whether the trip exists.
     */
    public boolean hasTrip(Trip trip) {
        requireNonNull(trip);
        return tripList.contains(trip);
    }

    /**
     * Convenience wrapper for checking whether the specified currency exists.
     *
     * @param currency The currency instance to check.
     * @return Boolean of whether the currency exists.
     */
    public boolean hasCurrency(CustomisedCurrency currency) {
        requireNonNull(currency);
        return currencies.contains(currency);
    }


    /**
     * Adds a customised currency to the address book.
     * The customised currency must not already exist in the address book.
     */
    public void addCurrency(CustomisedCurrency currency) {
        currencies.add(currency);
    }

    /**
     * Replaces the given customised currency {@code target} in the list with {@code editedCurrency}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setCurrency(CustomisedCurrency target, CustomisedCurrency editedCurrency) {
        requireNonNull(editedCurrency);
        currencies.set(target, editedCurrency);
    }

    /**
     * Deletes {@code key} from this {@code TravelPal}.
     * {@code key} must exist in the address book.
     */
    public void deleteCurrency(CustomisedCurrency key) {
        currencies.remove(key);
    }

    /**
     * Selects {@code key} from this {@code TravelPal}.
     * {@code key} must exist in the address book.
     */
    public void selectCurrency(CustomisedCurrency key) {
        currencies.promote(key);
    }

    /**
     * @return ObservableList of the trip list in the application.
     */
    @Override
    public ObservableList<Trip> getTripList() {
        return tripList.asUnmodifiableObservableList();
    }

    /**
     * @return ObservableList of the currencies in the application.
     */
    @Override
    public ObservableList<CustomisedCurrency> getCurrencies() {
        return currencies.asUnmodifiableObservableList();
    };
}
