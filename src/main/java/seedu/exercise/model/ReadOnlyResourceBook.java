package seedu.exercise.model;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.model.resource.Resource;

/**
 * Encapsulates a Resource Book that can contain {@code Resource} objects of type {@code T}.
 */
public class ReadOnlyResourceBook<T extends Resource> {

    private final UniqueResourceList<T> resources;

    {
        resources = new UniqueResourceList<>();
    }

    public ReadOnlyResourceBook() {

    }

    public ReadOnlyResourceBook(ReadOnlyResourceBook<T> toBeCopied) {
        this();
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
        setResources(newData.getResourceList());
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
     * Retrieves the index of {@code toGet} from the list.
     * Returns -1 if the item is not present in the list.
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

    public List<T> getAllResourcesIndex(Collection<Index> indexes) {
        return resources.getAllResourcesIndex(indexes);
    }

    /**
     * Returns an unmodifiable list of {@code Resource} of type {@code T}.
     */
    public ObservableList<T> getResourceList() {
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
}
