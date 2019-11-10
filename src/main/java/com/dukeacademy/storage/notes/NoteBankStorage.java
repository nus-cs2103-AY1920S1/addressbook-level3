package com.dukeacademy.storage.notes;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.util.FileUtil;
import com.dukeacademy.commons.util.JsonUtil;
import com.dukeacademy.model.notes.NoteBank;

/**
 * Represents a storage for NoteBanks. The NoteBankStorage holds a default file path to save and load the NoteBank to.
 */
public interface NoteBankStorage {
    /**
     * Returns the file path that the NoteBankStorage instance is saving to.
     * @return the storage file path
     */
    Path getNoteBankFilePath();

    /**
     * Loads a NoteBank instance from the default file path.
     * @return the loaded NoteBank
     * @throws DataConversionException if the file contains corrupt data
     * @throws IOException if the file cannot be read
     */
    Optional<NoteBank> readNoteBank() throws DataConversionException, IOException;

    /**
     * Loads a NoteBank instance from the given file path.
     * @return the loaded NoteBank
     * @throws DataConversionException if the file contains corrupt data
     * @throws IOException if the file cannot be read
     */
    Optional<NoteBank> readNoteBank(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given NoteBank instance to the default file path.
     * @param noteBank the NoteBank instance to be saved
     * @throws IOException if the file cannot be saved
     */
    void saveNoteBank(NoteBank noteBank) throws IOException;

    /**
     * Static method to save a given NoteBank instance to a given file path.
     * @param noteBank the NoteBank instance to be saved
     * @param filePath the file path to save the NoteBank to
     * @throws IOException if the file cannot be saved
     */
    static void saveNoteBank(NoteBank noteBank, Path filePath) throws IOException {
        requireNonNull(noteBank);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNoteBank(noteBank), filePath);
    }
}
