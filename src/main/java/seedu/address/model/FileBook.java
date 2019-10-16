package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.UniqueFileList;

/**
 * Wraps all data at the file-book level
 * Duplicates are not allowed
 */
public class FileBook implements ReadOnlyFileBook {

    private final UniqueFileList files;

    /**
     * Creates an empty FileBook.
     */
    public FileBook() {
        files = new UniqueFileList();
    }

    /**
     * Creates a FileBook using the Files in the {@code toBeCopied}
     */
    public FileBook(ReadOnlyFileBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the file list with {@code files}.
     * {@code files} must not contain duplicate files.
     */
    public void setFiles(List<EncryptedFile> files) {
        this.files.setFiles(files);
    }

    /**
     * Resets the existing data of this {@code FileBook} with {@code newData}.
     */
    public void resetData(ReadOnlyFileBook newData) {
        requireNonNull(newData);

        setFiles(newData.getFileList());
    }

    /**
     * Returns true if a file with the same identity as {@code file} exists in the file book.
     */
    public boolean hasFile(EncryptedFile file) {
        requireNonNull(file);
        return files.contains(file);
    }

    /**
     * Adds a file to the file book.
     * The file must not already exist in the file book.
     */
    public void addFile(EncryptedFile p) {
        files.add(p);
    }

    /**
     * Replaces the given file {@code target} in the list with {@code editedFile}.
     * {@code target} must exist in the address book.
     * The file identity of {@code editedFile} must not be the same as another existing file in the file book.
     */
    public void setFile(EncryptedFile target, EncryptedFile editedFile) {
        requireNonNull(editedFile);

        files.setFile(target, editedFile);
    }

    /**
     * Removes {@code key} from this {@code FileBook}.
     * {@code key} must exist in the file book.
     */
    public void removeFile(EncryptedFile key) {
        files.remove(key);
    }

    @Override
    public String toString() {
        return files.asUnmodifiableObservableList().size() + " files";
    }

    @Override
    public ObservableList<EncryptedFile> getFileList() {
        return files.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileBook // instanceof handles nulls
                && files.equals(((FileBook) other).files));
    }

    @Override
    public int hashCode() {
        return files.hashCode();
    }
}
