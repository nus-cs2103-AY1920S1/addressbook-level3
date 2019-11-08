package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.HOON_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.IDA_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.scheduler.commons.exceptions.DataConversionException;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.person.Interviewer;

public class JsonInterviewerListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonInterviewerListStorageTest");

    @TempDir
    public Path testFolder;

    private Optional<ReadOnlyList<Interviewer>> readInterviewerList(String filePath) throws Exception {
        return new JsonInterviewerListStorage(Paths.get(filePath)).readInterviewerList(
                addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readInterviewerList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readInterviewerList(null));
    }

    @Test
    public void readInterviewerList_missingFile_emptyResult() throws Exception {
        assertFalse(readInterviewerList("NonExistentFile.json").isPresent());
    }

    @Test
    public void readInterviewerList_fileNotJsonFormat_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readInterviewerList("fileNotJsonFormat.json"));
    }

    @Test
    public void readInterviewerList_invalidInterviewerList_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readInterviewerList("invalidInterviewerList.json"));
    }

    @Test
    public void readInterviewerList_invalidAndValidInterviewerList_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readInterviewerList("invalidAndValidInterviewerList.json"));
    }

    @Test
    public void readAndSaveInterviewerList_validData_success() throws Exception {
        Path filePath = testFolder.resolve("TempInterviewerList.json");
        InterviewerList original = getTypicalInterviewerList();
        JsonInterviewerListStorage jsonInterviewerListStorage = new JsonInterviewerListStorage(filePath);

        // Save in new file and read back
        jsonInterviewerListStorage.saveInterviewerList(original, filePath);
        ReadOnlyList<Interviewer> readBack = jsonInterviewerListStorage.readInterviewerList(filePath).get();
        assertEquals(original, new InterviewerList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEntity(HOON_INTERVIEWER);
        original.removeEntity(ALICE_INTERVIEWER);
        jsonInterviewerListStorage.saveInterviewerList(original, filePath);
        readBack = jsonInterviewerListStorage.readInterviewerList(filePath).get();
        assertEquals(original, new InterviewerList(readBack));

        // Save and read without specifying file path
        original.addEntity(IDA_INTERVIEWER);
        jsonInterviewerListStorage.saveInterviewerList(original); // file path not specified
        readBack = jsonInterviewerListStorage.readInterviewerList().get(); // file path not specified
        assertEquals(original, new InterviewerList(readBack));

    }

    @Test
    public void saveInterviewerList_nullInterviewerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInterviewerList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code InterviewerList} at the specified {@code filePath}.
     */
    private void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList, String filePath) {
        try {
            new JsonInterviewerListStorage(Paths.get(filePath))
                    .saveInterviewerList(interviewerList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInterviewerList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInterviewerList(new InterviewerList(), null));
    }
}
