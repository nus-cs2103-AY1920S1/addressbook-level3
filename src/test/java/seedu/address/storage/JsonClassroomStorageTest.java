package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.HOON;
import static seedu.address.testutil.TypicalStudents.IDA;
import static seedu.address.testutil.TypicalStudents.getTypicalClassroom;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;

public class JsonClassroomStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonClassroomStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readClassroom_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readClassroom(null));
    }

    private java.util.Optional<ReadOnlyClassroom> readClassroom(String filePath) throws Exception {
        return new JsonClassroomStorage(Paths.get(filePath)).readClassroom(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClassroom("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readClassroom("notJsonFormatClassroom.json"));
    }

    @Test
    public void readClassroom_invalidStudentClassroom_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readClassroom("invalidStudentClassroom.json"));
    }

    @Test
    public void readClassroom_invalidAndValidStudentClassroom_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readClassroom("invalidAndValidStudentClassroom.json"));
    }

    @Test
    public void readAndSaveClassroom_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempClassroom.json");
        Classroom original = getTypicalClassroom();
        JsonClassroomStorage jsonClassroomStorage = new JsonClassroomStorage(filePath);

        // Save in new file and read back
        jsonClassroomStorage.saveClassroom(original, filePath);
        ReadOnlyClassroom readBack = jsonClassroomStorage.readClassroom(filePath).get();
        assertEquals(original, new Classroom(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        jsonClassroomStorage.saveClassroom(original, filePath);
        readBack = jsonClassroomStorage.readClassroom(filePath).get();
        assertEquals(original, new Classroom(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonClassroomStorage.saveClassroom(original); // file path not specified
        readBack = jsonClassroomStorage.readClassroom().get(); // file path not specified
        assertEquals(original, new Classroom(readBack));

    }

    @Test
    public void saveClassroom_nullClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClassroom(null, "SomeFile.json"));
    }

    /**
     * Saves {@code classroom} at the specified {@code filePath}.
     */
    private void saveClassroom(ReadOnlyClassroom classroom, String filePath) {
        try {
            new JsonClassroomStorage(Paths.get(filePath))
                    .saveClassroom(classroom, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClassroom_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClassroom(new Classroom(), null));
    }
}
