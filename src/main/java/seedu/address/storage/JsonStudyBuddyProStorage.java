package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.CheatSheetDataConversionException;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.FlashcardDataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.NoteDataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyStudyBuddyProCheatSheets;
import seedu.address.model.ReadOnlyStudyBuddyProFlashcards;
import seedu.address.model.ReadOnlyStudyBuddyProNotes;
import seedu.address.model.StudyBuddyPro;

/**
 * A class to access StudyBuddyPro data stored as a json file on the hard disk.
 */
public class JsonStudyBuddyProStorage implements StudyBuddyProStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudyBuddyProStorage.class);

    private Path cheatSheetFilePath;
    private Path flashcardFilePath;
    private Path noteFilePath;

    /**
     * New constructor to now take in 3 filepaths, one for each mode
     * @param flashcardFilePath cannot be null.
     * @param noteFilePath cannot be null.
     * @param cheatSheetFilePath cannot be null.
     */
    public JsonStudyBuddyProStorage(Path flashcardFilePath, Path noteFilePath, Path cheatSheetFilePath) {
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);
        this.cheatSheetFilePath = cheatSheetFilePath;
        this.flashcardFilePath = flashcardFilePath;
        this.noteFilePath = noteFilePath;
    }

    public Path getCheatSheetFilePath() {
        return cheatSheetFilePath;
    }

    public Path getFlashcardFilePath() {
        return flashcardFilePath;
    }

    public Path getNoteFilePath() {
        return noteFilePath;
    }

    @Override
    public Optional<ReadOnlyStudyBuddyProFlashcards> readStudyBuddyProFlashcards()
            throws FlashcardDataConversionException {
        return readStudyBuddyProFlashcards(flashcardFilePath);
    }

    @Override
    public Optional<ReadOnlyStudyBuddyProFlashcards> readStudyBuddyProFlashcards(Path flashcardFilePath)
            throws FlashcardDataConversionException {
        requireNonNull(flashcardFilePath);
        Optional<JsonSerializableFlashcard> jsonFlashcard;
        try {
            jsonFlashcard = JsonUtil.readJsonFile(
                    flashcardFilePath, JsonSerializableFlashcard.class);
        } catch (DataConversionException ex) {
            throw new FlashcardDataConversionException(ex);
        }
        if (!jsonFlashcard.isPresent()) {
            return Optional.empty();
        } else {
            try {
                StudyBuddyPro studyBuddyProWithReadFlashcards = new StudyBuddyPro();
                jsonFlashcard.get().toModelType(studyBuddyProWithReadFlashcards);
                return Optional.of(studyBuddyProWithReadFlashcards);
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + flashcardFilePath + ": " + ive.getMessage());
                throw new FlashcardDataConversionException(ive);
            }
        }
    }

    @Override
    public Optional<ReadOnlyStudyBuddyProNotes> readStudyBuddyProNotes() throws NoteDataConversionException {
        return readStudyBuddyProNotes(noteFilePath);
    }

    @Override
    public Optional<ReadOnlyStudyBuddyProNotes> readStudyBuddyProNotes(Path notesFilePath)
            throws NoteDataConversionException {
        requireNonNull(notesFilePath);
        Optional<JsonSerializableNote> jsonNote;
        try {
            jsonNote = JsonUtil.readJsonFile(
                    notesFilePath, JsonSerializableNote.class);
        } catch (DataConversionException ex) {
            throw new NoteDataConversionException(ex);
        }
        if (!jsonNote.isPresent()) {
            return Optional.empty();
        } else {
            try {
                StudyBuddyPro studyBuddyProWithReadNotes = new StudyBuddyPro();
                jsonNote.get().toModelType(studyBuddyProWithReadNotes);
                return Optional.of(studyBuddyProWithReadNotes);
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + notesFilePath + ": " + ive.getMessage());
                throw new NoteDataConversionException(ive);
            }
        }
    }

    @Override
    public Optional<ReadOnlyStudyBuddyProCheatSheets> readStudyBuddyProCheatSheets()
            throws CheatSheetDataConversionException {
        return readStudyBuddyProCheatSheets(cheatSheetFilePath);
    }

    @Override
    public Optional<ReadOnlyStudyBuddyProCheatSheets> readStudyBuddyProCheatSheets(Path cheatSheetsFilePath)
            throws CheatSheetDataConversionException {
        requireNonNull(cheatSheetsFilePath);
        Optional<JsonSerializableCheatSheet> jsonCheatSheet;
        try {
            jsonCheatSheet = JsonUtil.readJsonFile(
                    cheatSheetsFilePath, JsonSerializableCheatSheet.class);
        } catch (DataConversionException ex) {
            throw new CheatSheetDataConversionException(ex);
        }
        if (!jsonCheatSheet.isPresent()) {
            return Optional.empty();
        } else {
            try {
                StudyBuddyPro studyBuddyProWithReadCheatSheets = new StudyBuddyPro();
                jsonCheatSheet.get().toModelType(studyBuddyProWithReadCheatSheets);
                return Optional.of(studyBuddyProWithReadCheatSheets);
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + cheatSheetsFilePath + ": " + ive.getMessage());
                throw new CheatSheetDataConversionException(ive);
            }
        }
    }

    @Override
    public void saveStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro) throws IOException {
        saveStudyBuddyPro(studyBuddyPro, flashcardFilePath, noteFilePath, cheatSheetFilePath);
    }

    /**
     * Similar to {@link #saveStudyBuddyPro(ReadOnlyStudyBuddyPro)}.
     * @param flashcardFilePath location of the data. Cannot be null.
     * @param noteFilePath location of the data. Cannot be null.
     * @param cheatSheetFilePath location of the data. Cannot be null.
     */
    public void saveStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro, Path flashcardFilePath,
                                  Path noteFilePath, Path cheatSheetFilePath) throws IOException {
        requireNonNull(studyBuddyPro);
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);

        FileUtil.createIfMissing(flashcardFilePath);
        FileUtil.createIfMissing(noteFilePath);
        FileUtil.createIfMissing(cheatSheetFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashcard(studyBuddyPro), flashcardFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableNote(studyBuddyPro), noteFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableCheatSheet(studyBuddyPro), cheatSheetFilePath);
    }
}
