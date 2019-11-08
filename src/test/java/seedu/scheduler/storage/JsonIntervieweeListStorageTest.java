package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.HOON_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.IDA_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.scheduler.commons.exceptions.DataConversionException;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.person.Interviewee;

public class JsonIntervieweeListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonIntervieweeListStorageTest");

    @TempDir
    public Path testFolder;

    private Optional<ReadOnlyList<Interviewee>> readIntervieweeList(String filePath) throws Exception {
        return new JsonIntervieweeListStorage(Paths.get(filePath)).readIntervieweeList(
                addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readIntervieweeList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readIntervieweeList(null));
    }

    @Test
    public void readIntervieweeList_missingFile_emptyResult() throws Exception {
        assertFalse(readIntervieweeList("NonExistentFile.json").isPresent());
    }

    @Test
    public void readIntervieweeList_fileNotJsonFormat_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readIntervieweeList("fileNotJsonFormat.json"));
    }

    @Test
    public void readIntervieweeList_invalidIntervieweeList_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readIntervieweeList("invalidIntervieweeList.json"));
    }

    @Test
    public void readIntervieweeList_invalidAndValidIntervieweeList_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readIntervieweeList("invalidAndValidIntervieweeList.json"));
    }

    @Test
    public void readAndSaveIntervieweeList_validData_success() throws Exception {
        Path filePath = testFolder.resolve("TempIntervieweeList.json");
        IntervieweeList original = getTypicalIntervieweeList();
        JsonIntervieweeListStorage jsonIntervieweeListStorage = new JsonIntervieweeListStorage(filePath);

        // Save in new file and read back
        jsonIntervieweeListStorage.saveIntervieweeList(original, filePath);
        ReadOnlyList<Interviewee> readBack = jsonIntervieweeListStorage.readIntervieweeList(filePath).get();
        assertEquals(original, new IntervieweeList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEntity(HOON_INTERVIEWEE);
        original.removeEntity(ALICE_INTERVIEWEE);
        jsonIntervieweeListStorage.saveIntervieweeList(original, filePath);
        readBack = jsonIntervieweeListStorage.readIntervieweeList(filePath).get();
        assertEquals(original, new IntervieweeList(readBack));

        // Save and read without specifying file path
        original.addEntity(IDA_INTERVIEWEE);
        jsonIntervieweeListStorage.saveIntervieweeList(original); // file path not specified
        readBack = jsonIntervieweeListStorage.readIntervieweeList().get(); // file path not specified
        assertEquals(original, new IntervieweeList(readBack));

    }

    @Test
    public void saveIntervieweeList_nullIntervieweeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveIntervieweeList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code IntervieweeList} at the specified {@code filePath}.
     */
    private void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList, String filePath) {
        try {
            new JsonIntervieweeListStorage(Paths.get(filePath))
                    .saveIntervieweeList(intervieweeList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveIntervieweeList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveIntervieweeList(new IntervieweeList(), null));
    }
}
