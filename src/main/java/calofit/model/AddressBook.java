package calofit.model;

import calofit.model.meal.Meal;
import calofit.model.meal.UniqueMealList;
import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueMealList meals;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        meals = new UniqueMealList();
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
     * Replaces the contents of the meal list with {@code meals}.
     * {@code meals} must not contain duplicate meals.
     */
    public void setMeals(List<Meal> meals) {
        this.meals.setMeals(meals);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setMeals(newData.getMealList());
    }

    //// meal-level operations

    /**
     * Returns true if a meal with the same identity as {@code meal} exists in the address book.
     */
    public boolean hasMeal(Meal meal) {
        requireNonNull(meal);
        return meals.contains(meal);
    }

    /**
     * Adds a meal to the address book.
     * The meal must not already exist in the address book.
     */
    public void addMeal(Meal p) {
        meals.add(p);
    }

    /**
     * Replaces the given meal {@code target} in the list with {@code editedMeal}.
     * {@code target} must exist in the address book.
     * The meal identity of {@code editedMeal} must not be the same as another existing meal in the address book.
     */
    public void setMeal(Meal target, Meal editedMeal) {
        requireNonNull(editedMeal);

        meals.setMeal(target, editedMeal);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeMeal(Meal key) {
        meals.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return meals.asUnmodifiableObservableList().size() + " meals";
        // TODO: refine later
    }

    @Override
    public ObservableList<Meal> getMealList() {
        return meals.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && meals.equals(((AddressBook) other).meals));
    }

    @Override
    public int hashCode() {
        return meals.hashCode();
    }
}
