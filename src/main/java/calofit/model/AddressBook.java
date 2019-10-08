package calofit.model;

import calofit.model.meal.Dish;
import calofit.model.meal.UniqueDishList;
import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueDishList dishes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dishes = new UniqueDishList();
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
     * Replaces the contents of the dish list with {@code dishes}.
     * {@code dishes} must not contain duplicate dishes.
     */
    public void setDishes(List<Dish> dishes) {
        this.dishes.setDishes(dishes);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setDishes(newData.getDishList());
    }

    //// dish-level operations

    /**
     * Returns true if a dish with the same identity as {@code dish} exists in the address book.
     */
    public boolean hasDish(Dish dish) {
        requireNonNull(dish);
        return dishes.contains(dish);
    }

    /**
     * Adds a dish to the address book.
     * The dish must not already exist in the address book.
     */
    public void addDish(Dish p) {
        dishes.add(p);
    }

    /**
     * Replaces the given dish {@code target} in the list with {@code editedDish}.
     * {@code target} must exist in the address book.
     * The dish identity of {@code editedDish} must not be the same as another existing dish in the address book.
     */
    public void setDish(Dish target, Dish editedDish) {
        requireNonNull(editedDish);

        dishes.setDish(target, editedDish);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeDish(Dish key) {
        dishes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return dishes.asUnmodifiableObservableList().size() + " dishes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Dish> getDishList() {
        return dishes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && dishes.equals(((AddressBook) other).dishes));
    }

    @Override
    public int hashCode() {
        return dishes.hashCode();
    }
}
