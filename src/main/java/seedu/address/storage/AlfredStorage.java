package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * Alfred Storage is the API to the storage of the system.
 */
public interface AlfredStorage extends UserPrefsStorage, TeamListStorage,
        ParticipantListStorage, MentorListStorage {

    //=========================== UserPrefs ===========================
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //=========================== TeamList ===========================
    @Override
    Path getTeamListFilePath();

    @Override
    Optional<TeamList> readTeamList() throws DataConversionException;

    @Override
    void saveTeamList(TeamList teamList) throws IOException;

    //=========================== ParticipantList ===========================
    @Override
    Path getParticipantListFilePath();

    @Override
    Optional<ParticipantList> readParticipantList() throws DataConversionException;

    @Override
    void saveParticipantList(ParticipantList participantList) throws IOException;

    //=========================== MentorList ===========================
    @Override
    Path getMentorListFilePath();

    @Override
    Optional<MentorList> readMentorList() throws DataConversionException;

    @Override
    void saveMentorList(MentorList mentorList) throws IOException;
}
