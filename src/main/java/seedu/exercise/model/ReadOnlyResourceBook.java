package seedu.exercise.model;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.exercise.model.resource.Resource;

/**
 * Encapsulates a Resource Book that can contain {@code Resource} objects of type {@code T}.
 */
public class ReadOnlyResourceBook<T extends Resource> {

    private final SortedUniqueResourceList<T> resources;

    public ReadOnlyResourceBook(Comparator<T> comparator) {
        resources = new SortedUniqueResourceList<>(comparator);
    }

    public ReadOnlyResourceBook(ReadOnlyResourceBook<T> toBeCopied, Comparator<T> comparator) {
        this(comparator);
        resetData(toBeCopied);
    }

    /**
     * Sets the data of a {@code ReadOnlyResourceBook} with the {@code resources}.
     */
    public void setResources(List<T> resources) {
        requireNonNull(resources);
        this.resources.setAll(resources);
    }

    /**
     * Resets the data of a {@code ReadOnlyResourceBook} with the {@code newData}.
     */
    public void resetData(ReadOnlyResourceBook<T> newData) {
        requireNonNull(newData);
        setResources(newData.getSortedResourceList());
    }

    /**
     * Returns true if the {@code ReadOnlyResourceBook} instance contains {@code resource}.
     */
    public boolean hasResource(T resource) {
        requireNonNull(resource);
        return resources.contains(resource);
    }

    /**
     * Adds {@code resource} into the {@code ReadOnlyResourceBook} instance.
     */
    public void addResource(T resource) {
        requireNonNull(resource);
        resources.add(resource);
    }

    /**
     * Replaces {@code target} with {@code editResource} in the {@code ReadOnlyResourceBook} instance.
     */
    public void setResource(T target, T editedResource) {
        requireAllNonNull(target, editedResource);
        resources.set(target, editedResource);
    }

    /**
     * Removes {@code key} from the {@code ReadOnlyResourceBook} instance.
     */
    public void removeResource(T key) {
        requireNonNull(key);
        resources.remove(key);
    }


    /**
     * Retrieves the index of {@code toGet} from the sorted list.
     * Returns -1 if the item is not present in the sorted list.
     */
    public int getResourceIndex(T toGet) {
        int i = 0;
        for (T resource : resources) {
            if (resource.equals(toGet)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Returns an unmodifiable sorted list of {@code Resource} of type {@code T}.
     */
    public ObservableList<T> getSortedResourceList() {
        return resources.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReadOnlyResourceBook // instanceof handles nulls
            && resources.equals(((ReadOnlyResourceBook) other).resources));
    }

    @Override
    public int hashCode() {
        return resources.hashCode();
    }


    @Override
    public String toString() {
        return "" + resources.asUnmodifiableObservableList().size() + " items";
    }
}
