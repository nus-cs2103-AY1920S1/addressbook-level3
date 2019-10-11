package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.entity.Participant;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.testutil.TypicalParticipants;

class JsonParticipantListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonParticipantListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readParticipantList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readParticipantList(null));
    }

    private java.util.Optional<ParticipantList> readParticipantList(String filePath) throws Exception {
        return new JsonParticipantListStorage(Paths.get(filePath))
                        .readParticipantList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readParticipantList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readParticipantList("notJsonFormatParticipantList.json"));
    }

    @Test
    public void readParticipantList_invalidParticipantList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readParticipantList("invalidParticipantList.json"));
    }

    @Test
    public void readParticipantList_invalidAndValidParticipantList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readParticipantList("invalidAndValidParticipantList.json"));
    }

    @org.junit.jupiter.api.Test
    void readAndSaveParticipantList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempParticipantList.json");
        ParticipantList original = TypicalParticipants.getTypicalParticipantList();
        JsonParticipantListStorage pStorage = new JsonParticipantListStorage(filePath);

        //Save and read participantList to and from JSON
        pStorage.saveParticipantList(original);
        Optional<ParticipantList> returnedList = pStorage.readParticipantList();

        if (returnedList.isEmpty()) {
            fail("Participant List read from storage is empty. Optional<ParticipantList> is empty.");
        } else {
            List<Participant> returnedParticipantList = returnedList.get().getSpecificTypedList();
            assertEquals(returnedParticipantList, TypicalParticipants.getTypicalParticipants());
        }
    }

    @Test
    public void saveParticipantList_nullParticipantList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveParticipantList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code pList} at the specified {@code filePath}.
     */
    private void saveParticipantList(ParticipantList pList, String filePath) {
        try {
            new JsonParticipantListStorage(Paths.get(filePath))
                    .saveParticipantList(pList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveParticipantList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveParticipantList(new ParticipantList(), null));
    }
}
