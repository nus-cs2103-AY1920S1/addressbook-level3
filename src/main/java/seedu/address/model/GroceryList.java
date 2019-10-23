package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.food.Food;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.UniqueFoodList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class GroceryList implements ReadOnlyGroceryList {

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

    public GroceryList() {}

    /**
     * Creates an GroceryList using the Persons in the {@code toBeCopied}
     */
    public GroceryList(ReadOnlyGroceryList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<GroceryItem> foods) {
        this.persons.setPersons(foods);
    }

    /**
     * Resets the existing data of this {@code GroceryList} with {@code newData}.
     */
    public void resetData(ReadOnlyGroceryList newData) {
        requireNonNull(newData);

        setPersons(newData.getGroceryList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(GroceryItem food) {
        requireNonNull(food);
        return persons.contains(food);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(GroceryItem p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setGroceryItem(GroceryItem target, GroceryItem editedFood) {
        requireNonNull(editedFood);

        persons.setPerson(target, editedFood);
    }

    /**
     * Removes {@code key} from this {@code GroceryList}.
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
    public ObservableList<GroceryItem> getGroceryList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroceryList // instanceof handles nulls
                && persons.equals(((GroceryList) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
