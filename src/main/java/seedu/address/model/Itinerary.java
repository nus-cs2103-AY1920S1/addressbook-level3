package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.day.Day;
import seedu.address.model.day.UniqueDayList;
import seedu.address.model.field.Name;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class Itinerary implements ReadOnlyItinerary {
    private final UniqueDayList days;
    private Name name;
    private LocalDate startDate;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        days = new UniqueDayList();
    }

    public Itinerary() {
        this.name = new Name("Untitled");
        this.startDate = LocalDate.now();
    }

    /**
     * Creates an Itinerary using the Persons in the {@code toBeCopied}
     */
    public Itinerary(ReadOnlyItinerary toBeCopied) {
        this();
        resetDataItinerary(toBeCopied);
    }

    public Name getName() {
        return this.name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setName(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public void setStartDate(LocalDate date) {
        requireNonNull(date);
        this.startDate = date;
    }

    //// For DAY list overwrite
    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return days.contains(day);
    }

    /**
     * Replaces the contents of the contacts list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDays(List<Day> itinerary) {
        this.days.setDays(itinerary);
    }

    /**
     * Adds a day to the itinerary
     */
    public void addDay(Day d) {
        this.days.add(d);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addDays(int n) {
        this.days.adds(n);
    }

    /**
     * Removes {@code key} from this {@code Itinerary}.
     * {@code key} must exist in the address book.
     */
    public void removeDay(Day target) {
        this.days.remove(target);
    }

    /**
     * Resets the existing Day data of this {@code Itinerary} with {@code newData}.
     */
    public void resetDataItinerary(ReadOnlyItinerary newData) {
        requireNonNull(newData);

        setDays(newData.getItinerary());
    }

    //// util methods

    @Override
    public String toString() {
        return days.asUnmodifiableObservableList().size() + " days,";
    }

    @Override
    public ObservableList<Day> getItinerary() {
        return days.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Itinerary // instanceof handles nulls
                && days.equals(((Itinerary) other).days));
    }

    @Override
    public int hashCode() {
        return days.hashCode();
    }
}
