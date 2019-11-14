package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.address.commons.Predicates;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.storage.AlfredStorage;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelManagerTest {
    private AlfredStorage storage;
    private UserPrefs userPrefs;
    private ModelManager modelManager;

    ModelManagerTest() {
        this.storage = mock(AlfredStorage.class);
        this.userPrefs = spy(new UserPrefs());
        this.modelManager = spy(new ModelManager(storage, userPrefs));
    }

    @BeforeEach
    public void clearTeamA() {
        modelManager = spy(new ModelManager(storage, userPrefs));
        TypicalTeams.clearTeamA();
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setUserPrefs_normal_success() {
        new ModelManager(this.storage, this.userPrefs).setUserPrefs(this.userPrefs);
    }

    @Test
    public void setUserPrefs_nullObj_error() {
        assertThrows(
                NullPointerException.class, () -> new ModelManager(this.storage, this.userPrefs).setUserPrefs(null));
    }

    @Test
    public void getUserPrefs_normal_success() {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        assertNotNull(this.modelManager.getUserPrefs());
    }

    @Test
    public void getGuiSettings_normal_success() {
        GuiSettings guiSettings = new GuiSettings();
        when(this.userPrefs.getGuiSettings()).thenReturn(guiSettings);
        GuiSettings result = this.modelManager.getGuiSettings();
        assertEquals(result, guiSettings);
    }

    @Test
    public void setGuiSettings_normal_success() {
        GuiSettings guiSettings = new GuiSettings();
        Mockito.doNothing().when(this.modelManager).setGuiSettings(any());
        this.modelManager.setGuiSettings(new GuiSettings());
        verify(this.modelManager).setGuiSettings(guiSettings);
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        assertThrows(NullPointerException.class, () -> m.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void getTeamListFilePath_normal_success() {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        Path result = m.getTeamListFilePath();
        assertNotNull(result);
    }

    @Test
    public void getParticipantListFilePath_normal_success() {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        Path result = m.getParticipantListFilePath();
        assertNotNull(result);
    }

    @Test
    public void getMentorListFilePath_normal_success() {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        Path result = m.getMentorListFilePath();
        assertNotNull(result);
    }

    @Test
    public void getFilteredParticipantList_normal_success() {
        FilteredList result =
                new ModelManager(this.storage, this.userPrefs).getFilteredParticipantList();
        assertNotNull(result);
    }

    @Test
    public void getFilteredTeamList_normal_success() {
        FilteredList result = new ModelManager(this.storage, this.userPrefs).getFilteredTeamList();
        assertNotNull(result);
    }

    @Test
    public void getFilteredMentorList_normal_success() {
        FilteredList result =
                new ModelManager(this.storage, this.userPrefs).getFilteredMentorList();
        assertNotNull(result);
    }

    @Test
    public void resetFilteredLists_normal_success () {
        ModelManager m = new ModelManager();
        assertDoesNotThrow(() -> m.resetFilteredLists());
    }

    @Test
    public void addAndGetParticipant_validId_returnsParticipant() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addParticipant(TypicalParticipants.A);
            Participant participant = modelManager.getParticipant(new Id(PrefixType.P, 1));
            assertTrue(participant.equals(TypicalParticipants.A));
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void getSortedTeamList_normal_success() {
        SortedList<Team> result = new ModelManager(this.storage, this.userPrefs)
                .getSortedTeamList();
        assertNotNull(result);
    }

    @Test
    public void deleteParticipant_validId_returnsParticipant() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addParticipant(TypicalParticipants.A);
            modelManager.addTeam(TypicalTeams.A);
            Participant participant = modelManager.deleteParticipant(new Id(PrefixType.P, 1));
            assertTrue(participant.equals(TypicalParticipants.A));
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void updateParticipant_validId_returnsTrue() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addParticipant(TypicalParticipants.A);
            modelManager.addTeam(TypicalTeams.A);
            modelManager.updateParticipant(new Id(PrefixType.P, 1),
                    TypicalParticipants.A_UPDATED);
            assertTrue(TypicalTeams.A.getParticipants().get(0)
                    .equals(TypicalParticipants.A_UPDATED));
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void getTeamByParticipantId_validId_returnsTeam() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addTeam(TypicalTeams.A);
            assertTrue(TypicalTeams.A
                    .equals(modelManager.getTeamByParticipantId(new Id(PrefixType.P, 1))));
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void getTeamByMentorId_validId_returnsMentor() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addTeam(TypicalTeams.A);
            assertTrue(TypicalTeams.A
                    .equals(modelManager.getTeamByMentorId(new Id(PrefixType.M, 3))));
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void updateMentor_validMentor_updatesMentor() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addTeam(TypicalTeams.A);
            modelManager.addMentor(TypicalMentors.A);
            modelManager.updateMentor(new Id(PrefixType.M, 3),
                    TypicalMentors.A_UPDATED);
            assertTrue(modelManager.getTeamByMentorId(new Id(PrefixType.M, 3))
                    .equals(TypicalTeams.A));

        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void addParticipantToTeam_validParticipant_addsParticipant() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addTeam(TypicalTeams.A);
            modelManager.addParticipantToTeam(new Id(PrefixType.T, 1),
                    TypicalParticipants.B);
            assertTrue(modelManager.getTeam(new Id(PrefixType.T, 1))
                    .getParticipants().size() == 2);
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void findParticipantByName_validName_correctResult() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addParticipant(TypicalParticipants.A);
            modelManager.addParticipant(TypicalParticipants.B);
            assertEquals(modelManager.getParticipantList().list().size(), 2);
            assertEquals(modelManager.findParticipant(
                    Predicates.getPredicateFindParticipantByName("A", false)).size(), 2);
            assertEquals(modelManager.findParticipant(
                    Predicates.getPredicateFindParticipantByName("Part B", false)).size(), 1);
        } catch (AlfredException | IOException e) {
            // do nothing
        }
    }

    @Test
    public void findTeamByName_validName_correctResult() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addParticipant(TypicalParticipants.A);
            modelManager.addMentor(TypicalMentors.A);
            modelManager.addTeam(TypicalTeams.A);
        } catch (AlfredException | IOException e) {
            // do nothing
        }
        assertEquals(modelManager.getTeamList().list().size(), 1);
        assertEquals(modelManager.findTeam(
                Predicates.getPredicateFindTeamByName("A", false)).size(), 1);
    }

    @Test
    public void findMentorByName_validName_correctResult() {
        try {
            Mockito.doNothing().when(storage).saveParticipantList(any());
            Mockito.doNothing().when(storage).saveTeamList(any());
            Mockito.doNothing().when(storage).saveMentorList(any());
            modelManager.addMentor(TypicalMentors.A);
            modelManager.addMentor(TypicalMentors.B);
        } catch (AlfredException | IOException e) {
            // do nothing
        }
        assertEquals(modelManager.getMentorList().list().size(), 2);
        assertEquals(modelManager.findMentor(
                Predicates.getPredicateFindMentorByName("B", false)).size(), 1);
    }

    @Test
    public void addParticipantToTeam_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.B);
        m.addTeam(TypicalTeams.EMPTY);
        assertDoesNotThrow(() -> m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.B));
    }

    @Test
    public void addParticipantToTeam_invalidParticipant_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(
                ModelValidationException.class, () -> m.addParticipantToTeam(TypicalTeams.EMPTY.getId(),
                        TypicalParticipants.B));
    }

    @Test
    public void addParticipantToTeam_invalidTeam_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        assertThrows(
                MissingEntityException.class, () -> m.addParticipantToTeam(TypicalTeams.EMPTY.getId(),
                        TypicalParticipants.A));
    }

    @Test
    public void addParticipantToTeam_pAlreadyInT_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamA();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        m.addMentor(TypicalMentors.A);
        m.addTeam(TypicalTeams.A);
        assertThrows(
                AlfredModelException.class, () -> m.addParticipantToTeam(TypicalTeams.A.getId(),
                        TypicalParticipants.A));
    }

    @Test
    public void removeParticipantToTeam_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.B);
        m.addTeam(TypicalTeams.EMPTY);
        m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.B);
        assertDoesNotThrow(() -> m.removeParticipantFromTeam(
                TypicalTeams.EMPTY.getId(), TypicalParticipants.B));
    }

    @Test
    public void removeParticipantToTeam_invalidParticipant_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(
                ModelValidationException.class, () -> m.removeParticipantFromTeam(TypicalTeams.EMPTY.getId(),
                        TypicalParticipants.B));
    }

    @Test
    public void removeParticipantToTeam_invalidTeam_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        assertThrows(
                MissingEntityException.class, () -> m.removeParticipantFromTeam(TypicalTeams.EMPTY.getId(),
                        TypicalParticipants.A));
    }

    @Test
    public void removeParticipantFromTeam_pNotInT_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.B);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(
                AlfredModelException.class, () -> m.removeParticipantFromTeam(TypicalTeams.EMPTY.getId(),
                        TypicalParticipants.B));
    }

    @Test
    public void addMentorToTeam_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.B);
        m.addTeam(TypicalTeams.EMPTY);
        assertDoesNotThrow(() -> m.addMentorToTeam(TypicalTeams.EMPTY.getId(), TypicalMentors.B));
    }

    @Test
    public void addMentorToTeam_invalidMentor_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(
                ModelValidationException.class, () -> m.addMentorToTeam(TypicalTeams.EMPTY.getId(),
                        TypicalMentors.B));
    }

    @Test
    public void addMentorToTeam_invalidTeam_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertThrows(
                MissingEntityException.class, () -> m.addMentorToTeam(TypicalTeams.EMPTY.getId(),
                        TypicalMentors.A));
    }

    @Test
    public void addMentorToTeam_mAlreadyInT_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamA();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertThrows(
                AlfredModelException.class, () -> m.addMentorToTeam(TypicalTeams.A.getId(),
                        TypicalMentors.A));
    }

    @Test
    public void removeMentorToTeam_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.B);
        m.addTeam(TypicalTeams.EMPTY);
        m.addMentorToTeam(TypicalTeams.EMPTY.getId(), TypicalMentors.B);
        assertDoesNotThrow(() -> m.removeMentorFromTeam(
                TypicalTeams.EMPTY.getId(), TypicalMentors.B));
    }

    @Test
    public void removeMentorToTeam_invalidParticipant_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(
                ModelValidationException.class, () -> m.removeMentorFromTeam(TypicalTeams.EMPTY.getId(),
                        TypicalMentors.B));
    }

    @Test
    public void removeMentorToTeam_invalidTeam_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertThrows(
                MissingEntityException.class, () -> m.removeMentorFromTeam(TypicalTeams.EMPTY.getId(),
                        TypicalMentors.A));
    }

    @Test
    public void removeMentorToTeam_mNotInT_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.B);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(
                AlfredModelException.class, () -> m.removeMentorFromTeam(TypicalTeams.EMPTY.getId(),
                        TypicalMentors.B));
    }

    @Test
    public void updateParticipant_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        m.addTeam(TypicalTeams.EMPTY);
        m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.A);
        assertDoesNotThrow(() -> m.updateParticipant(
                TypicalParticipants.A.getId(), TypicalParticipants.A_UPDATED));
    }

    @Test
    public void updateParticipant_noTeam_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        assertDoesNotThrow(() -> m.updateParticipant(
                TypicalParticipants.A.getId(), TypicalParticipants.A_UPDATED));
    }

    @Test
    public void updateParticipant_idNotExist_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        assertThrows(MissingEntityException.class, () -> m.updateParticipant(
                TypicalParticipants.B.getId(), TypicalParticipants.A_UPDATED));
    }

    @Test
    public void deleteParticipant_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        m.addTeam(TypicalTeams.EMPTY);
        m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.A);
        assertEquals(TypicalParticipants.A, m.deleteParticipant(TypicalParticipants.A.getId()));
    }

    @Test
    public void deleteParticipant_noTeam_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        assertEquals(TypicalParticipants.A, m.deleteParticipant(TypicalParticipants.A.getId()));
    }

    @Test
    public void deleteParticipant_idNotExist_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addParticipant(TypicalParticipants.A);
        assertThrows(MissingEntityException.class, () -> m.deleteParticipant(TypicalParticipants.B.getId()));
    }

    @Test
    public void updateMentor_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        m.addTeam(TypicalTeams.EMPTY);
        m.addMentorToTeam(TypicalTeams.EMPTY.getId(), TypicalMentors.A);
        assertDoesNotThrow(() -> m.updateMentor(
                TypicalMentors.A.getId(), TypicalMentors.A_UPDATED));
    }

    @Test
    public void updateMentor_noTeam_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertDoesNotThrow(() -> m.updateMentor(
                TypicalMentors.A.getId(), TypicalMentors.A_UPDATED));
    }

    @Test
    public void updateMentor_idNotExist_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertThrows(MissingEntityException.class, () -> m.updateMentor(
                TypicalMentors.B.getId(), TypicalMentors.A_UPDATED));
    }

    @Test
    public void deleteMentor_valid_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        m.addTeam(TypicalTeams.EMPTY);
        m.addMentorToTeam(TypicalTeams.EMPTY.getId(), TypicalMentors.A);
        assertEquals(TypicalMentors.A, m.deleteMentor(TypicalMentors.A.getId()));
    }

    @Test
    public void deleteMentor_noTeam_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertEquals(TypicalMentors.A, m.deleteMentor(TypicalMentors.A.getId()));
    }

    @Test
    public void deleteMentor_idNotExist_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addMentor(TypicalMentors.A);
        assertThrows(MissingEntityException.class, () -> m.deleteMentor(TypicalMentors.B.getId()));
    }

    @Test
    public void deleteTeam_noParticipants_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        assertEquals(TypicalTeams.EMPTY, m.deleteTeam(TypicalTeams.EMPTY.getId()));
    }

    @Test
    public void deleteTeam_haveParticipants_success() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        m.addParticipant(TypicalParticipants.A);
        m.addParticipant(TypicalParticipants.B);
        m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.A);
        m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.B);
        assertEquals(TypicalTeams.EMPTY, m.deleteTeam(TypicalTeams.EMPTY.getId()));
        assertEquals(m.getParticipantList().list().size(), 2);
    }

    @Test
    public void deleteTeam_wrongTeam_error() throws IOException, AlfredException {
        Mockito.doNothing().when(storage).saveParticipantList(any());
        Mockito.doNothing().when(storage).saveTeamList(any());
        Mockito.doNothing().when(storage).saveMentorList(any());
        TypicalTeams.clearTeamEmpty();
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(MissingEntityException.class, () -> m.deleteTeam(
                TypicalTeams.D.getId()));
    }

    @Test
    public void getTeamByParticipantId_validId_success() throws AlfredException {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        TypicalTeams.clearTeamEmpty();
        m.addParticipant(TypicalParticipants.A);
        m.addMentor(TypicalMentors.A);
        m.addParticipant(TypicalParticipants.B);
        m.addTeam(TypicalTeams.EMPTY);
        m.addTeam(TypicalTeams.A);
        m.addParticipantToTeam(TypicalTeams.EMPTY.getId(), TypicalParticipants.A);
        assertEquals(m.getTeamByParticipantId(TypicalParticipants.A.getId()).size(), 2);
    }

    @Test
    public void getTeamByParticipantId_noTeam_error() throws AlfredException {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        TypicalTeams.clearTeamEmpty();
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(MissingEntityException.class, () -> m.getTeamByParticipantId(
                TypicalParticipants.B.getId()));
    }

    @Test
    public void getTeamByMentorId_validId_success() throws AlfredException {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        TypicalTeams.clearTeamEmpty();
        m.addParticipant(TypicalParticipants.A);
        m.addMentor(TypicalMentors.A);
        m.addTeam(TypicalTeams.EMPTY);
        m.addTeam(TypicalTeams.A);
        m.addMentorToTeam(TypicalTeams.EMPTY.getId(), TypicalMentors.A);
        assertEquals(m.getTeamByMentorId(TypicalMentors.A.getId()).size(), 2);
    }

    @Test
    public void getTeamByMentorId_noTeam_error() throws AlfredException {
        ModelManager m = new ModelManager(this.storage, this.userPrefs);
        TypicalTeams.clearTeamEmpty();
        m.addTeam(TypicalTeams.EMPTY);
        assertThrows(MissingEntityException.class, () -> m.getTeamByMentorId(
                TypicalMentors.B.getId()));
    }

    @Test
    public void equals() {
        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));
    }
}
