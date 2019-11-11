package seedu.system.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameElement comparison)
 */
public class Data<T extends UniqueElement> implements ReadOnlyData<T> {

    private final UniqueElementList<T> uniqueElements;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        uniqueElements = new UniqueElementList<T>();
    }

    public Data() {}

    /**
     * Creates an Data using the UniqueElements in the {@code toBeCopied}
     */
    public Data(ReadOnlyData toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the unique element list with {@code uniqueElements}.
     * {@code uniqueElements} must not contain duplicate elements.
     */
    public void setListOfElements(List<T> listOfElements) {
        this.uniqueElements.setElements(listOfElements);
    }

    /**
     * Resets the existing data of this {@code Data} with {@code newData}.
     */
    public void resetData(ReadOnlyData newData) {
        requireNonNull(newData);

        setListOfElements(newData.getListOfElements());
    }

    //// uniqueElements-level operations

    /**
     * Returns true if an element with the same identity as {@code uniqueElement} exists in the address book.
     */
    public boolean hasUniqueElement(T uniqueElement) {
        requireNonNull(uniqueElement);
        return uniqueElements.contains(uniqueElement);
    }

    /**
     * Adds a unique element to the address book.
     * The unique element must not already exist in the address book.
     */
    public void addUniqueElement(T t) {
        uniqueElements.add(t);
    }

    /**
     * Replaces the given element {@code target} in the list with {@code editedElement}.
     * {@code target} must exist in the address book.
     * The element identity of {@code editedElement} must not be the same as another existing element in the system.
     */
    public void setElement(T target, T editedElement) {
        requireNonNull(editedElement);

        uniqueElements.setElement(target, editedElement);
    }

    /**
     * Removes {@code key} from this {@code Data}.
     * {@code key} must exist in the address book.
     */
    public void removeElement(T key) {
        uniqueElements.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return "List of elements of size " + uniqueElements.asUnmodifiableObservableList().size();
        // TODO: refine later
    }

    @Override
    public ObservableList<T> getListOfElements() {
        return uniqueElements.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Data // instanceof handles nulls
                && uniqueElements.equals(((Data) other).uniqueElements));
    }

    @Override
    public int hashCode() {
        return uniqueElements.hashCode();
    }
}
