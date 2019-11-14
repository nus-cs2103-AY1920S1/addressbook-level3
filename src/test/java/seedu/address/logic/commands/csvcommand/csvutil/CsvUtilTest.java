package seedu.address.logic.commands.csvcommand.csvutil;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.FileUtil;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

public class CsvUtilTest {

    private MentorList mentorList;
    private ParticipantList participantList;
    private TeamList teamList;

    /**
     * Initializes mentor list.
     */
    private void initializeMentors() throws AlfredException {
        mentorList = new MentorList();
        mentorList.add(TypicalMentors.A);
        mentorList.add(TypicalMentors.B);
        mentorList.add(TypicalMentors.C);
    }

    /**
     * Initializes participant list.
     */
    private void initializeParticipants() throws AlfredException {
        participantList = new ParticipantList();
        participantList.add(TypicalParticipants.A);
        participantList.add(TypicalParticipants.B);
        participantList.add(TypicalParticipants.C);
    }

    /**
     * Initializes team list.
     */
    private void initializeTeams() throws AlfredException {
        teamList = new TeamList();
        teamList.add(TypicalTeams.A);
        teamList.add(TypicalTeams.B);
        teamList.add(TypicalTeams.C);
    }

    @Test
    public void parseToMentor_validParameters_noExceptionThrown() {
        // EntityType (M), ID (may be blank), Name, Phone, Email, Organization, SubjectName
        String[] mentorData = new String[] {
            "M",
            "",
            "Alfred the Mentor",
            "+6512345678",
            "alfred@batcave.com",
            "Bruce Ent.",
            "Environmental"
        };
        assertDoesNotThrow(() -> CsvUtil.parseToMentor(mentorData));
    }

    @Test
    public void parseToMentor_invalidParameters_illegalArgumentExceptionThrown() {
        // EntityType (M), ID (may be blank), Name, Phone, Email, Organization, SubjectName
        String[] invalidEntityType = new String[] {
            "P", // Should be "M"
            "",
            "Alfred the Mentor",
            "+6512345678",
            "alfred@batcave.com",
            "Bruce Ent.",
            "Environmental"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToMentor(invalidEntityType));

        // If ID is invalid, CsvUtil will generate a valid one

        String[] invalidName = new String[] {
            "M",
            "",
            "Alfred the Mentor^^", // Name cannot contain special characters other than ",.-'"
            "+6512345678",
            "alfred@batcave.com",
            "Bruce Ent.",
            "Environmental"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToMentor(invalidName));

        String[] invalidPhone = new String[] {
            "M",
            "",
            "Alfred the Mentor",
            "+6512345678^^", // Phone number cannot contain special characters other than "-. "
            "alfred@batcave.com",
            "Bruce Ent.",
            "Environmental"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToMentor(invalidPhone));

        String[] invalidEmail = new String[] {
            "M",
            "",
            "Alfred the Mentor",
            "+6512345678",
            "alfred", // Emails must contain a domain
            "Bruce Ent.",
            "Environmental"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToMentor(invalidEmail));

        // Organization is skipped because it is a Name object

        String[] invalidSubject = new String[] {
            "M",
            "",
            "Alfred the Mentor",
            "+6512345678",
            "alfred",
            "Bruce Ent.",
            "Healthy" // Should be "Health" (or "HEALTH"...)
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToMentor(invalidSubject));
    }

    @Test
    public void parseToParticipant_validParameters_noExceptionThrown() {
        // EntityType (P), ID, Name, Phone, Email
        String[] participantData = new String[] {
            "P",
            "",
            "Bruce the Participant",
            "+6523456789",
            "batman@batcave.com"
        };
        assertDoesNotThrow(() -> CsvUtil.parseToParticipant(participantData));
    }

    @Test
    public void parseToParticipant_invalidParameters_illegalArgumentExceptionThrown() {
        // EntityType (P), ID, Name, Phone, Email
        String[] invalidEntityType = new String[] {
            "T", // Should be "P"
            "",
            "Bruce the Participant",
            "+6523456789",
            "batman@batcave.com"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToParticipant(invalidEntityType));

        // If ID is invalid, CsvUtil will generate a valid one

        String[] invalidName = new String[] {
            "P",
            "",
            "Bruce the Participant^^", // Name cannot contain special characters other than ",.-'"
            "+6523456789",
            "batman@batcave.com"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToParticipant(invalidName));

        String[] invalidPhone = new String[] {
            "P",
            "",
            "Bruce the Participant",
            "+6523456789^^", // Phone number cannot contain special characters other than "-. "
            "batman@batcave.com"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToParticipant(invalidPhone));

        String[] invalidEmail = new String[] {
            "P",
            "",
            "Bruce the Participant",
            "+6523456789",
            "batman" // Emails must contain a domain
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToParticipant(invalidEmail));
    }

    @Test
    public void parseToTeam_validParameters_noExceptionThrown() {
        // EntityType (T), ID, Name, Participants, Mentor, SubjectName, Score, ProjectName, Location
        Model model = new ModelManagerStub();
        String[] teamData = new String[] {
            "T",
            "",
            "Team Batman",
            "",
            "",
            "Social",
            "100",
            "Project Cleanup Gotham",
            "1"
        };
        assertDoesNotThrow(() -> CsvUtil.parseToTeam(teamData, model));
    }

    @Test
    public void parseToTeam_invalidParameters_illegalArgumentExceptionThrown() {
        // EntityType (T), ID, Name, Participants, Mentor, SubjectName, Score, ProjectName, Location
        Model model = new ModelManagerStub();
        String[] invalidEntityType = new String[] {
            "M", // Should be "T"
            "",
            "Team Batman",
            "",
            "",
            "Social",
            "100",
            "Project Cleanup Gotham",
            "1"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToTeam(invalidEntityType, model));

        // If ID is invalid, CsvUtil will generate a valid one

        String[] invalidName = new String[] {
            "T",
            "",
            "Team Batman^^", // Name cannot contain special characters other than ",.-'"
            "",
            "",
            "Social",
            "100",
            "Project Cleanup Gotham",
            "1"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToTeam(invalidName, model));

        String[] invalidParticipants = new String[] {
            "T",
            "",
            "Team Batman^^",
            "[P-1]", // Participant with ID P-1 does not exist in model
            "",
            "Social",
            "100",
            "Project Cleanup Gotham",
            "1"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToTeam(invalidParticipants, model));

        String[] invalidMentor = new String[] {
            "T",
            "",
            "Team Batman^^",
            "",
            "M-1", // Mentor with ID M-1 does not exist in model
            "Social",
            "100",
            "Project Cleanup Gotham",
            "1"
        };

        String[] invalidSubject = new String[] {
            "T",
            "",
            "Team Batman",
            "",
            "",
            "Socially", // Should be one of the enum values
            "100",
            "Project Cleanup Gotham",
            "1"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToTeam(invalidSubject, model));

        String[] invalidScore = new String[] {
            "T",
            "",
            "Team Batman",
            "",
            "",
            "Social",
            "1000", // Should be an integer between 0 and 100
            "Project Cleanup Gotham",
            "1"
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToTeam(invalidScore, model));

        // ProjectName is skipped because it is a Name object

        String[] invalidLocation = new String[] {
            "T",
            "",
            "Team Batman",
            "",
            "",
            "Social",
            "100",
            "Project Cleanup Gotham",
            "10000" // Should be an integer between 0 and 1000
        };
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.parseToTeam(invalidLocation, model));
    }

    @Test
    public void writeToCsv_entityListPassed_correctContentWrittenToFile() throws IOException, AlfredException {
        // Initialize EntityLists and create a temporary file (to be deleted upon exit)
        initializeMentors();
        initializeParticipants();
        initializeTeams();
        File csvFile = new File(TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString());
        csvFile.deleteOnExit();

        // Test Mentors
        CsvUtil.writeToCsv(csvFile, this.mentorList, false);
        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedMentors.csv").toFile();
        assertTrue(FileUtil.hasEqualContents(csvFile, expectedFile));

        // Test Participants
        CsvUtil.writeToCsv(csvFile, this.participantList, false);
        expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedParticipants.csv").toFile();
        assertTrue(FileUtil.hasEqualContents(csvFile, expectedFile));

        // Test Teams
        CsvUtil.writeToCsv(csvFile, this.teamList, false);
        expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedTeams.csv").toFile();
        assertTrue(FileUtil.hasEqualContents(csvFile, expectedFile));
    }

    @Test
    public void writeToCsv_shouldAppend_contentAppendedToFile() throws IOException, AlfredException {
        // Initialize EntityLists and create a temporary file (to be deleted upon exit)
        initializeMentors();
        initializeParticipants();
        initializeTeams();
        File csvFile = new File(TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString());
        csvFile.deleteOnExit();

        // Test Mentors
        CsvUtil.writeToCsv(csvFile, this.mentorList, false);
        CsvUtil.writeToCsv(csvFile, this.mentorList, true);
        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedMentorsAppended.csv").toFile();
        assertTrue(FileUtil.hasEqualContents(csvFile, expectedFile));

        // Test Participants
        CsvUtil.writeToCsv(csvFile, this.participantList, false);
        CsvUtil.writeToCsv(csvFile, this.participantList, true);
        expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedParticipantsAppended.csv").toFile();
        assertTrue(FileUtil.hasEqualContents(csvFile, expectedFile));

        // Test Teams
        CsvUtil.writeToCsv(csvFile, this.teamList, false);
        CsvUtil.writeToCsv(csvFile, this.teamList, true);
        expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedTeamsAppended.csv").toFile();
        assertTrue(FileUtil.hasEqualContents(csvFile, expectedFile));
    }

    @Test
    public void isCsvHeader_validHeaderPassed_returnTrue() {
        String validHeader = "EntityType,ID,Name,Phone,Email,Organization,SubjectName";
        assertTrue(CsvUtil.isCsvHeader(validHeader));

        validHeader = "EntityType, ID ,  Name   , Phone, Email, Organization,SubjectName";
        assertTrue(CsvUtil.isCsvHeader(validHeader));

        validHeader = "EntityType, ID ,  NaME   , Phone, EMAil, ORGANIZATION,SubjectName";
        assertTrue(CsvUtil.isCsvHeader(validHeader));

        validHeader = "EntityTyPE, ID, Name , Phone,Email";
        assertTrue(CsvUtil.isCsvHeader(validHeader));

        validHeader = "EntityType,ID,Name , Participants, MeNTOr,SubjectName,Score,  ProjectName,Location";
        assertTrue(CsvUtil.isCsvHeader(validHeader));
    }

}
