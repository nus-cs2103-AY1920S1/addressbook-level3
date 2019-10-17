package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.file.EncryptedFile;

/**
 * Unmodifiable view of a file book
 */
public interface ReadOnlyFileBook {

    /**
     * Returns an unmodifiable view of the files list.
     * This list will not contain any duplicate files.
     */
    ObservableList<EncryptedFile> getFileList();

}
