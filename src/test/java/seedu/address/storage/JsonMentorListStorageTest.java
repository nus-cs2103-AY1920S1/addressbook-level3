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
import seedu.address.model.entity.Mentor;
import seedu.address.model.entitylist.MentorList;
import seedu.address.testutil.TypicalMentors;

class JsonMentorListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMentorListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMentorList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMentorList(null));
    }

    private java.util.Optional<MentorList> readMentorList(String filePath) throws Exception {
        return new JsonMentorListStorage(Paths.get(filePath))
                .readMentorList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMentorList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMentorList("notJsonFormatMentorList.json"));
    }

    @Test
    public void readMentorList_invalidMentorList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMentorList("invalidMentorList.json"));
    }

    @Test
    public void readMentorList_invalidAndValidMentorList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMentorList("invalidAndValidMentorList.json"));
    }

    @org.junit.jupiter.api.Test
    void readAndSaveMentorList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMentorList.json");
        MentorList original = TypicalMentors.getTypicalMentorList();
        JsonMentorListStorage mStorage = new JsonMentorListStorage(filePath);

        //Save and read mentorList to and from JSON
        mStorage.saveMentorList(original);
        Optional<MentorList> returnedList = mStorage.readMentorList();

        if (returnedList.isEmpty()) {
            fail("Mentor List read from storage is empty. Optional<MentorList> is empty.");
        } else {
            List<Mentor> returnedMentorList = returnedList.get().getSpecificTypedList();
            assertEquals(returnedMentorList, TypicalMentors.getTypicalMentors());
        }
    }

    @Test
    public void saveMentorList_nullMentorList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMentorList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code mList} at the specified {@code filePath}.
     */
    private void saveMentorList(MentorList mList, String filePath) {
        try {
            new JsonMentorListStorage(Paths.get(filePath))
                    .saveMentorList(mList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMentorList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMentorList(new MentorList(), null));
    }

}
