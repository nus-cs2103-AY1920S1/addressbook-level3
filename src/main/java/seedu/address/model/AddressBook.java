package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.food.Food;
import seedu.address.model.food.UniqueFoodList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueFoodList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueFoodList();
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
     * Replaces the contents of the food list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setFoods(List<Food> foods) {
        this.persons.setFoods(foods);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setFoods(newData.getPersonList());
    }

    //// food-level operations

    /**
     * Returns true if a food with the same identity as {@code food} exists in the address book.
     */
    public boolean hasPerson(Food food) {
        requireNonNull(food);
        return persons.contains(food);
    }

    /**
     * Adds a food to the address book.
     * The food must not already exist in the address book.
     */
    public void addPerson(Food p) {
        persons.add(p);
    }

    /**
     * Replaces the given food {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the address book.
     * The food identity of {@code editedFood} must not be the same as another existing food in the address book.
     */
    public void setFood(Food target, Food editedFood) {
        requireNonNull(editedFood);

        persons.setFood(target, editedFood);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Food key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Food> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
