package seedu.planner.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import seedu.planner.commons.core.index.Index;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.Day;
import seedu.planner.model.day.DayList;
import seedu.planner.model.field.Name;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class Itinerary implements ReadOnlyItinerary {
    private final DayList days;
    private SimpleObjectProperty<Name> nameProperty;
    private SimpleObjectProperty<LocalDate> startDateProperty;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        days = new DayList();
    }

    public Itinerary() {
        this.nameProperty = new SimpleObjectProperty<>(new Name("Sample"));
        this.startDateProperty = new SimpleObjectProperty<>(LocalDate.now());
    }

    /**
     * Creates an Itinerary using the Persons in the {@code toBeCopied}
     */
    public Itinerary(ReadOnlyItinerary toBeCopied) {
        this();
        resetDataItinerary(toBeCopied);
    }

    public void setName(Name name) {
        requireNonNull(name);
        this.nameProperty.setValue(name);
    }

    public void setStartDate(LocalDate date) {
        requireNonNull(date);
        this.startDateProperty.setValue(date);
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

    public void shiftDatesInItinerary(long days, Index startIndex, Index endIndex) {
        this.days.shiftDatesInItinerary(days, startIndex, endIndex);
    }

    public void setDay(Day oldDay, Day newDay) {
        this.days.setDay(oldDay, newDay);
    }

    /**
     * Adds a day to the itinerary.
     */
    public void addDay(Day d) {
        this.days.add(d);
    }

    /**
     * Adds a day to the itinerary at a specific index.
     */
    public void addDayAtIndex(Index index, Day d) {
        this.days.addAtIndex(index, d);
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
     * Removes the last n number of days in the Day list.
     * @param n number of days to be removed.
     */
    public void deleteDays(int n) {
        this.days.deleteDays(n);
    }

    /**
     * Resets the existing Day data of this {@code Itinerary} with {@code newData}.
     */
    public void resetDataItinerary(ReadOnlyItinerary newData) {
        requireNonNull(newData);
        setDays(newData.getItinerary());
        setName(newData.getName());
        setStartDate(newData.getStartDate());
    }

    public List<Day> getDays(Activity activity) {
        return days.getDays(activity);
    }

    public int getNumberOfDays() {
        return days.getNumberOfDays();
    }

    @Override
    public Name getName() {
        return this.nameProperty.getValue();
    }

    @Override
    public SimpleObjectProperty<Name> getNameProperty() {
        return this.nameProperty;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDateProperty.getValue();
    }

    @Override
    public SimpleObjectProperty<LocalDate> getStartDateProperty() {
        return this.startDateProperty;
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
