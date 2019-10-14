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
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.TeamList;
import seedu.address.testutil.TypicalTeams;

class JsonTeamListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTeamListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTeamList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTeamList(null));
    }

    private java.util.Optional<TeamList> readTeamList(String filePath) throws Exception {
        return new JsonTeamListStorage(Paths.get(filePath))
                .readTeamList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTeamList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTeamList("notJsonFormatTeamList.json"));
    }

    @Test
    public void readTeamList_invalidTeamList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTeamList("invalidTeamList.json"));
    }

    @Test
    public void readTeamList_invalidAndValidTeamList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTeamList("invalidAndValidTeamList.json"));
    }

    @org.junit.jupiter.api.Test
    void readAndSaveTeamList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTeamList.json");
        TeamList original = TypicalTeams.getTypicalTeamList();
        JsonTeamListStorage tStorage = new JsonTeamListStorage(filePath);

        //Save and read participantList to and from JSON
        tStorage.saveTeamList(original);
        tStorage.saveTeamList(original, Paths.get("data" , "teamlist.json"));
        Optional<TeamList> returnedList = tStorage.readTeamList();

        if (returnedList.isEmpty()) {
            fail("Team List read from storage is empty. Optional<TeamList> is empty.");
        } else {
            List<Team> returnedTeamList = returnedList.get().getSpecificTypedList();
            assertEquals(returnedTeamList, TypicalTeams.getTypicalTeams());
        }
    }

    @Test
    public void saveTeamList_nullTeamList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTeamList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code tList} at the specified {@code filePath}.
     */
    private void saveTeamList(TeamList tList, String filePath) {
        try {
            new JsonTeamListStorage(Paths.get(filePath))
                    .saveTeamList(tList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTeamList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTeamList(new TeamList(), null));
    }
}
