package com.dukeacademy.storage.notes;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.JsonUtil;
import com.dukeacademy.model.notes.NoteBank;

/**
 * Implementation of the NoteBankStorage interface that stores NoteBank data in the Json format.
 */
public class JsonNoteBankStorage implements NoteBankStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonNoteBankStorage.class);
    private final Path filePath;

    /**
     * Constructor, sets the default storage file path to the given file path.
     * @param filePath the default storage file path
     */
    public JsonNoteBankStorage(Path filePath) {
        this.filePath = filePath;
        logger.info("JsonNoteStorage file path set : " + filePath);
    }

    @Override
    public Path getNoteBankFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<NoteBank> readNoteBank() throws DataConversionException, IOException {
        return this.readNoteBank(filePath);
    }

    @Override
    public Optional<NoteBank> readNoteBank(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableNoteBank> jsonNoteBank = JsonUtil.readJsonFile(
                filePath, JsonSerializableNoteBank.class);

        if (jsonNoteBank.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNoteBank.get().toModelType());
        } catch (IllegalValueException e) {
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveNoteBank(NoteBank noteBank) throws IOException {
        NoteBankStorage.saveNoteBank(noteBank, filePath);
    }
}
