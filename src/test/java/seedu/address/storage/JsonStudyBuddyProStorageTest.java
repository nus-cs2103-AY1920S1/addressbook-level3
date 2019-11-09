package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

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
                "ExistentFile.json", "ExistentFile.json").isPresent());
    }

    @Test
    public void read_missingNoteFile_emptyResult() throws Exception {
        assertFalse(readStudyBuddyProNotes("ExistentFile.json",
                "NonExistentFile.json", "ExistentFile.json").isPresent());
    }

    @Test
    public void read_missingCheatSheetFile_emptyResult() throws Exception {
        assertFalse(readStudyBuddyProCheatSheets("ExistentFile.json",
                "ExistentFile.json", "NonExistentFile.json").isPresent());
    }

    @Test
    public void read_flashcardsNotJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProFlashcards("notJsonFormatFlashcards.json",
            "JsonFormatNotes.json", "JsonFormatCheatSheets.json"));
    }

    @Test
    public void read_notesNotJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProNotes("JsonFormatFlashcards.json",
                "notJsonFormatNotes.json", "JsonFormatCheatSheets.json"));
    }

    @Test
    public void read_cheatsheetsNotJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudyBuddyProCheatSheets("JsonFormatFlashcards.json",
                "JsonFormatNotes.json", "notJsonFormatCheatSheets.json"));
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

    /*
    @Test
    public void readAndSaveStudyBuddyPro_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        StudyBuddyPro original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveStudyBuddyPro(original, filePath);
        ReadOnlyStudyBuddyPro readBack = jsonAddressBookStorage.readStudyBuddyPro(filePath).get();
        assertEquals(original, new StudyBuddyPro(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveStudyBuddyPro(original, filePath);
        readBack = jsonAddressBookStorage.readStudyBuddyPro(filePath).get();
        assertEquals(original, new StudyBuddyPro(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveStudyBuddyPro(original); // file path not specified
        readBack = jsonAddressBookStorage.readStudyBuddyPro().get(); // file path not specified
        assertEquals(original, new StudyBuddyPro(readBack));

    }
    */

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
