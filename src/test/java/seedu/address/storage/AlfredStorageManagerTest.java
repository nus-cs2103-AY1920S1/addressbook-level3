package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

class AlfredStorageManagerTest {
    @TempDir
    public Path testFolder;

    private AlfredStorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonParticipantListStorage pStore = new JsonParticipantListStorage(getTempFilePath("pStore"));
        JsonMentorListStorage mStore = new JsonMentorListStorage(getTempFilePath("mStore"));
        JsonTeamListStorage tStore = new JsonTeamListStorage(getTempFilePath("tStore"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new AlfredStorageManager(pStore, mStore, tStore, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void participantListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the AlfredStorage is properly wired to the
         * {@link JsonParticipantListStorage} class.
         * More extensive testing of ParticipantList saving/reading is done in {@link JsonParticipantListStorage} class.
         */
        ParticipantList original = TypicalParticipants.getTypicalParticipantList();
        storageManager.saveParticipantList(original);
        ParticipantList retrieved = storageManager.readParticipantList().get();
        assertEquals(original.getSpecificTypedList(), retrieved.getSpecificTypedList());
    }

    @Test
    public void mentorListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the AlfredStorage is properly wired to the
         * {@link JsonMentorListStorage} class.
         * More extensive testing of MentorList saving/reading is done in {@link JsonMentorListStorage} class.
         */
        MentorList original = TypicalMentors.getTypicalMentorList();
        storageManager.saveMentorList(original);
        MentorList retrieved = storageManager.readMentorList().get();
        assertEquals(original.getSpecificTypedList(), retrieved.getSpecificTypedList());
    }

    @Test
    public void teamListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the AlfredStorage is properly wired to the
         * {@link JsonTeamListStorage} class.
         * More extensive testing of TeamList saving/reading is done in {@link JsonTeamListStorage} class.
         */
        TeamList original = TypicalTeams.getTypicalTeamList();
        storageManager.saveTeamList(original);
        TeamList retrieved = storageManager.readTeamList().get();
        assertEquals(original.getSpecificTypedList(), retrieved.getSpecificTypedList());
    }

    @Test
    public void getFilePaths() {
        assertNotNull(storageManager.getParticipantListFilePath());
        assertNotNull(storageManager.getMentorListFilePath());
        assertNotNull(storageManager.getTeamListFilePath());
        assertNotNull(storageManager.getUserPrefsFilePath());
    }
}
