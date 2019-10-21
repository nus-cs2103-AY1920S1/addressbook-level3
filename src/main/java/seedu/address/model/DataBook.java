package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the data book level.
 * Duplicates are not allowed by .isSameAs comparison.
 * @param <T> A class that implements {@code Identifiable}.
 */
public class DataBook<T extends Identifiable<T>> implements ReadOnlyDataBook<T> {

    private final UniqueList<T> dataList;

    public DataBook() {
        dataList = new UniqueList<>();
    }

    /**
     * Creates a DataBook using the data in the {@code toBeCopied}.
     */
    public DataBook(ReadOnlyDataBook<T> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents with the given {@code data}.
     * {@code data} must not contain duplicate objects.
     */
    public void setData(List<T> data) {
        this.dataList.setList(data);
    }

    /**
     * Resets the existing data of this {@code DataBook} with {@code newData}.
     */
    public void resetData(ReadOnlyDataBook<T> newData) {
        requireNonNull(newData);
        setData(newData.getList());
    }

    /**
     * Returns true if an object with the same identity as {@code object} exists in the data book.
     */
    public boolean has(T object) {
        requireNonNull(object);
        return dataList.contains(object);
    }

    /**
     * Adds an object to the data book.
     * The object must not already exist in the data book.
     */
    public void add(T object) {
        requireNonNull(object);
        dataList.add(object);
    }

    /**
     * Replaces the given object {@code target} in the list with {@code editedObject}.
     * {@code target} must exist in the data book.
     * The identity of {@code editedObject} must not be the same as another existing object.
     */
    public void set(T target, T editedObject) {
        requireAllNonNull(target, editedObject);
        dataList.set(target, editedObject);
    }

    /**
     * Removes {@code key} from this {@code DataBook}.
     * {@code key} must exist in the data book.
     */
    public void remove(T key) {
        dataList.remove(key);
    }

    @Override
    public String toString() {
        return dataList.asUnmodifiableObservableList().size() + " data";
    }

    @Override
    public ObservableList<T> getList() {
        return dataList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DataBook // instanceof handles nulls
                && dataList.equals(((DataBook) other).dataList));
    }

    @Override
    public int hashCode() {
        return dataList.hashCode();
    }

}
