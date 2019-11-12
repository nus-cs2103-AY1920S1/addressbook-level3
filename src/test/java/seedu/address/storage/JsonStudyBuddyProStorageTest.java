package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashcards.CS_ONE;
import static seedu.address.testutil.TypicalFlashcards.MATH_ONE;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;
import static seedu.address.testutil.TypicalNotes.PIPELINE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyStudyBuddyProCheatSheets;
import seedu.address.model.ReadOnlyStudyBuddyProFlashcards;
import seedu.address.model.ReadOnlyStudyBuddyProNotes;
import seedu.address.model.StudyBuddyPro;
import seedu.address.testutil.FlashcardBuilder;

public class JsonStudyBuddyProStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonStudyBuddyProStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readStudyBuddyProFlashcards_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudyBuddyProFlashcards(null,
                "SomeFile.json", "SomeFile.json"));
    }

    @Test
    public void readStudyBuddyProNotes_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudyBuddyProNotes(null,
                "SomeFile.json", "SomeFile.json"));
    }

    @Test
    public void readStudyBuddyProCheatSheets_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudyBuddyProCheatSheets(null,
                "SomeFile.json", "SomeFile.json"));
    }

    private Optional<ReadOnlyStudyBuddyProFlashcards> readStudyBuddyProFlashcards(String flashcardFilePath,
                                                                                  String noteFilePath,
                                                                                  String cheatsheetFilePath)
            throws Exception {
        return new JsonStudyBuddyProStorage(Paths.get(flashcardFilePath),
                Paths.get(noteFilePath), Paths.get(cheatsheetFilePath))
                .readStudyBuddyProFlashcards(addToTestDataPathIfNotNull(flashcardFilePath));
    }

    private Optional<ReadOnlyStudyBuddyProNotes> readStudyBuddyProNotes(String flashcardFilePath,
                                                                        String noteFilePath,
                                                                        String cheatsheetFilePath)
            throws Exception {
        return new JsonStudyBuddyProStorage(Paths.get(flashcardFilePath),
                Paths.get(noteFilePath), Paths.get(cheatsheetFilePath))
                .readStudyBuddyProNotes(addToTestDataPathIfNotNull(noteFilePath));
    }

    private Optional<ReadOnlyStudyBuddyProCheatSheets> readStudyBuddyProCheatSheets(String flashcardFilePath,
                                                                                    String noteFilePath,
                                                                                    String cheatsheetFilePath)
            throws Exception {
        return new JsonStudyBuddyProStorage(Paths.get(flashcardFilePath),
                Paths.get(noteFilePath), Paths.get(cheatsheetFilePath))
                .readStudyBuddyProCheatSheets(addToTestDataPathIfNotNull(cheatsheetFilePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFlashcardFile_emptyResult() throws Exception {
        assertFalse(readStudyBuddyProFlashcards("NonExistentFile.json",
                "validNoteStudyBuddyPro.json", "validCheatSheetStudyBuddyPro.json").isPresent());
    }

    @Test
    public void read_missingNoteFile_emptyResult() throws Exception {
        assertFalse(readStudyBuddyProNotes("validFlashcardStudyBuddyPro.json",
                "NonExistentFile.json", "NonExistentFile.json").isPresent());
    }

    @Test
    public void read_missingCheatSheetFile_emptyResult() throws Exception {
        assertFalse(readStudyBuddyProCheatSheets("validFlashcardStudyBuddyPro.json",
                "validNoteStudyBuddyPro.json", "NonExistentFile.json").isPresent());
    }

    @Test
    public void read_flashcardsNotJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProFlashcards("notJsonFormatFlashcards.json",
            "validNoteStudyBuddyPro.json", "validCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void read_notesNotJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProNotes("validFlashcardStudyBuddyPro.json",
                "notJsonFormatNotes.json", "validCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void read_cheatsheetsNotJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
                readStudyBuddyProCheatSheets("validFlashcardStudyBuddyPro.json",
                "validNoteStudyBuddyPro.json", "notJsonFormatCheatSheets.json"));
    }

    @Test
    public void readStudyBuddyPro_invalidFlashcardStudyBuddyPro_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProFlashcards("invalidFlashcardStudyBuddyPro"
                + ".json", "validNoteStudyBuddyPro.json", "validCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void readStudyBuddyPro_invalidNoteStudyBuddyPro_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProNotes("validFlashcardStudyBuddyPro"
                + ".json", "invalidNoteStudyBuddyPro.json", "validCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void readStudyBuddyPro_invalidCheatSheetStudyBuddyPro_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProCheatSheets("validFlashcardStudyBuddyPro"
                + ".json", "validNoteStudyBuddyPro.json", "invalidCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void readStudyBuddyPro_invalidAndValidFlashcardStudyBuddyPro_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProFlashcards(
                "invalidAndValidFlashcardStudyBuddyPro.json", "validNoteStudyBuddyPro.json",
                "validCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void readStudyBuddyPro_invalidAndValidNoteStudyBuddyPro_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProNotes(
                "validFlashcardStudyBuddyPro.json", "invalidAndValidNoteStudyBuddyPro.json",
                "validCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void readStudyBuddyPro_invalidAndValidCheatSheetStudyBuddyPro_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProCheatSheets(
                "validFlashcardStudyBuddyPro.json", "validNoteStudyBuddyPro.json",
                "invalidAndValidCheatSheetStudyBuddyPro.json"));
    }

    @Test
    public void readAndSaveStudyBuddyPro_allInOrder_success() throws Exception {
        Path flashcardsFilePath = testFolder.resolve("TempStudyBuddyProFlashcards.json");
        Path notesFilePath = testFolder.resolve("TempStudyBuddyProNotes.json");
        Path cheatsheetsFilePath = testFolder.resolve("TempStudyBuddyProCheatSheets.json");

        StudyBuddyPro original = getTypicalStudyBuddyPro();
        JsonStudyBuddyProStorage jsonStudyBuddyProStorage = new JsonStudyBuddyProStorage(flashcardsFilePath,
                notesFilePath, cheatsheetsFilePath);

        // Save in new file and read back
        jsonStudyBuddyProStorage.saveStudyBuddyPro(original, flashcardsFilePath, notesFilePath, cheatsheetsFilePath);
        ReadOnlyStudyBuddyProFlashcards readBackFlashcards = jsonStudyBuddyProStorage
                .readStudyBuddyProFlashcards(flashcardsFilePath).get();
        ReadOnlyStudyBuddyProNotes readBackNotes = jsonStudyBuddyProStorage
                .readStudyBuddyProNotes(notesFilePath).get();
        ReadOnlyStudyBuddyProCheatSheets readBackCheatSheets = jsonStudyBuddyProStorage
                .readStudyBuddyProCheatSheets(cheatsheetsFilePath).get();
        assertEquals(original, new StudyBuddyPro(readBackFlashcards, readBackNotes, readBackCheatSheets));

        // Modify data, overwrite exiting file, and read back. Can add more comprehensive tests for notes and
        // cheatsheets as well
        original.addFlashcard(new FlashcardBuilder(MATH_ONE).withQuestion("New Question")
                .withTitle("New Title").build());
        original.removeFlashcard(CS_ONE);
        jsonStudyBuddyProStorage.saveStudyBuddyPro(original, flashcardsFilePath, notesFilePath, cheatsheetsFilePath);
        readBackFlashcards = jsonStudyBuddyProStorage.readStudyBuddyProFlashcards(flashcardsFilePath).get();
        readBackNotes = jsonStudyBuddyProStorage.readStudyBuddyProNotes(notesFilePath).get();
        readBackCheatSheets = jsonStudyBuddyProStorage.readStudyBuddyProCheatSheets(cheatsheetsFilePath).get();
        assertEquals(original, new StudyBuddyPro(readBackFlashcards, readBackNotes, readBackCheatSheets));

        // Save and read without specifying file path
        original.addNote(PIPELINE);
        jsonStudyBuddyProStorage.saveStudyBuddyPro(original); // file path not specified
        readBackFlashcards = jsonStudyBuddyProStorage.readStudyBuddyProFlashcards().get(); // file path not specified
        readBackNotes = jsonStudyBuddyProStorage.readStudyBuddyProNotes().get(); // file path not specified
        readBackCheatSheets = jsonStudyBuddyProStorage.readStudyBuddyProCheatSheets().get(); // file path not specified
        assertEquals(original, new StudyBuddyPro(readBackFlashcards, readBackNotes, readBackCheatSheets));

    }

    @Test
    public void saveStudyBuddyPro_nullStudyBuddyPro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudyBuddyPro(null,
                "SomeFile.json", "SomeFile.json", "SomeFile.json"));
    }

    /**
     * Saves {@code studyBuddyPro} at the specified file paths.
     */
    private void saveStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro, String flashcardFilePath,
                                   String noteFilePath, String cheatsheetFilePath) {
        try {
            new JsonStudyBuddyProStorage(Paths.get(flashcardFilePath), Paths.get(noteFilePath),
                    Paths.get(cheatsheetFilePath))
                    .saveStudyBuddyPro(studyBuddyPro, addToTestDataPathIfNotNull(flashcardFilePath),
                            addToTestDataPathIfNotNull(noteFilePath),
                            addToTestDataPathIfNotNull(cheatsheetFilePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveStudyBuddyPro_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudyBuddyPro(new StudyBuddyPro(),
                null, null, null));
        assertThrows(NullPointerException.class, () -> saveStudyBuddyPro(new StudyBuddyPro(),
                "SomeFile.json", "SomeFile.json", null));
        assertThrows(NullPointerException.class, () -> saveStudyBuddyPro(new StudyBuddyPro(),
                "SomeFile.json", null, "SomeFile.json"));
        assertThrows(NullPointerException.class, () -> saveStudyBuddyPro(new StudyBuddyPro(),
                null, "SomeFile.json", "SomeFile.json"));
    }
}
