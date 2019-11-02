package seedu.algobase.storage;

import seedu.algobase.storage.exceptions.StorageException;

/**
 * Runnable that saves to storage.
 */
public interface SaveStorageRunnable {
    void save() throws StorageException;
}
