package seedu.address.model.file;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.file.exceptions.DuplicateFileException;
import seedu.address.model.file.exceptions.FileNotFoundException;

/**
 * A list of files that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueFileList implements Iterable<EncryptedFile> {
    private final ObservableList<EncryptedFile> internalList = FXCollections.observableArrayList();
    private final ObservableList<EncryptedFile> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent file as the given argument.
     */
    public boolean contains(EncryptedFile toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFile);
    }

    /**
     * Adds a file to the list.
     * The file must not already exist in the list.
     */
    public void add(EncryptedFile toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFileException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the file {@code target} in the list with {@code editedFile}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedFile} must not be the same as another existing file in the list.
     */
    public void setFile(EncryptedFile target, EncryptedFile editedFile) {
        requireAllNonNull(target, editedFile);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FileNotFoundException();
        }

        if (!target.isSameFile(editedFile) && contains(editedFile)) {
            throw new DuplicateFileException();
        }

        internalList.set(index, editedFile);
    }

    /**
     * Removes the equivalent file from the list.
     * The file must exist in the list.
     */
    public void remove(EncryptedFile toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FileNotFoundException();
        }
    }

    public void setFiles(UniqueFileList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code files}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setFiles(List<EncryptedFile> files) {
        requireAllNonNull(files);
        if (!filesAreUnique(files)) {
            throw new DuplicateFileException();
        }

        internalList.setAll(files);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<EncryptedFile> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<EncryptedFile> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFileList // instanceof handles nulls
                && internalList.equals(((UniqueFileList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique files.
     */
    private boolean filesAreUnique(List<EncryptedFile> files) {
        for (int i = 0; i < files.size() - 1; i++) {
            for (int j = i + 1; j < files.size(); j++) {
                if (files.get(i).isSameFile(files.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
