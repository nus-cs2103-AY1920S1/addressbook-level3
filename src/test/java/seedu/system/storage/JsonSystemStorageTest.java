package seedu.system.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.system.testutil.Assert.assertThrows;
import static seedu.system.testutil.TypicalPersons.ALICE;
import static seedu.system.testutil.TypicalPersons.HOON;
import static seedu.system.testutil.TypicalPersons.IDA;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.system.commons.exceptions.DataConversionException;
import seedu.system.model.Data;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.person.Person;

public class JsonSystemStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSystemStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPersonData(null));
    }

    /**
     * Reads the person data at the specified {@code filePath}.
     */
    private java.util.Optional<ReadOnlyData<Person>> readPersonData(String filePath) throws Exception {
        return new JsonSystemStorage(
            Paths.get(filePath),
            Paths.get("SomeFile.json"),
            Paths.get("SomeOtherFile.json")
        ).readPersonData(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPersonData("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPersonData("notJsonFormatData.json"));
    }

    @Test
    public void readSystem_invalidPersonSystem_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPersonData("invalidPersonData.json"));
    }

    @Test
    public void readSystem_invalidAndValidPersonSystem_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPersonData("invalidAndValidPersonData.json"));
    }

    @Test
    public void readAndSaveSystem_allInOrder_success() throws Exception {
        Path personDataFilePath = testFolder.resolve("TempPersonData.json");
        Path competitionDataFilePath = testFolder.resolve("TempCompetitionData.json");
        Path participationDataFilePath = testFolder.resolve("TempParticipationData.json");
        Data<Person> personData = getTypicalPersonData();
        JsonSerializablePersonData originalJsonSerializablePersonData =
            new JsonSerializablePersonData(personData);
        JsonSystemStorage jsonSystemStorage =
            new JsonSystemStorage(
                personDataFilePath,
                competitionDataFilePath,
                participationDataFilePath
            );

        // Save in new file and read back
        jsonSystemStorage.saveData(originalJsonSerializablePersonData, personDataFilePath);
        ReadOnlyData readBack = jsonSystemStorage.readPersonData(personDataFilePath).get();
        assertEquals(personData, new Data(readBack));

        // Modify data, overwrite exiting file, and read back
        personData.addUniqueElement(HOON);
        personData.removeElement(ALICE);
        JsonSerializablePersonData modifiedJsonSerializablePersonData =
            new JsonSerializablePersonData(personData);
        jsonSystemStorage.saveData(modifiedJsonSerializablePersonData, personDataFilePath);
        readBack = jsonSystemStorage.readPersonData(personDataFilePath).get();
        assertEquals(personData, new Data(readBack));

        // Save and read without specifying file path
        personData.addUniqueElement(IDA);
        jsonSystemStorage.savePersonData(personData); // file path not specified
        readBack = jsonSystemStorage.readPersonData().get(); // file path not specified
        assertEquals(personData, new Data(readBack));

    }

    @Test
    public void saveSystem_nullSystem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePersonData(null, "SomeFile.json"));
    }

    /**
     * Saves {@code data} at the specified {@code filePath}.
     */
    private void savePersonData(ReadOnlyData data, String filePath) {
        try {
            new JsonSystemStorage(
                Paths.get(filePath),
                Paths.get("SomeFile.json"),
                Paths.get("SomeOtherFile.json")
            ).saveData(new JsonSerializablePersonData(data), addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSystem_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePersonData(new Data(), null));
    }
}
