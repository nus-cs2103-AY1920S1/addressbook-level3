package seedu.address.logic.commands.csvcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.csvcommand.csvutil.CsvUtil;
import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker.Error;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

public class ImportCommandTest {

    private final File entityCsv = TestUtil.getFilePathInCsvUtilTestFolder("ImportEntities.csv").toFile();
    private Model model = new ModelManagerStub();

    @Test
    public void execute_validFilePassedIn_success() throws AlfredException {
        // Initialize fields
        MentorList mentorList = new MentorList();
        mentorList.add(TypicalMentors.A);
        mentorList.add(TypicalMentors.B);
        mentorList.add(TypicalMentors.C);
        ParticipantList participantList = new ParticipantList();
        participantList.add(TypicalParticipants.A);
        participantList.add(TypicalParticipants.B);
        participantList.add(TypicalParticipants.C);
        TeamList teamList = new TeamList();
        teamList.add(TypicalTeams.A);
        teamList.add(TypicalTeams.B);
        teamList.add(TypicalTeams.C);

        new ImportCommand(entityCsv.getAbsolutePath()).execute(model);
        assertEquals(mentorList, model.getMentorList());
        assertEquals(participantList, model.getParticipantList());
        assertEquals(teamList, model.getTeamList());
    }

    @Test
    public void execute_nonCsvFilePassedIn_assertionErrorThrown() {
        File tempFile = TestUtil.getFilePathInSandboxFolder("temp.txt").toFile();
        tempFile.deleteOnExit();
        assertThrows(AssertionError.class, () -> new ImportCommand(tempFile.getAbsolutePath()),
                ImportCommand.ASSERTION_FAILED_NOT_CSV);
    }

    @Test
    public void execute_nonexistentFilePassedIn_commandExceptionThrown() {
        File tempFile = TestUtil.getFilePathInSandboxFolder("nonexistent.csv").toFile();
        tempFile.deleteOnExit();
        assertThrows(CommandException.class, () -> new ImportCommand(tempFile.getAbsolutePath()).execute(model),
                ImportCommand.MESSAGE_FILE_NOT_FOUND);
    }

    @Test
    public void execute_invalidFormattingOfFile_commandExceptionThrownWithErrorTrackerMessage() {
        File tempFile = TestUtil.getFilePathInCsvUtilTestFolder("InvalidFormat.csv").toFile();
        String expected = String.join(
                "\n",
                ImportCommand.MESSAGE_PARTIAL_SUCCESS,
                new Error(2, "M,,Alfred^^,+65 12345678,,org,Health", ImportCommand.CAUSE_INVALID_DATA).toString(),
                new Error(3, "P,,,,,,,,,,", ImportCommand.CAUSE_INVALID_DATA).toString(),
                new Error(4, "T,,", ImportCommand.CAUSE_INVALID_DATA).toString(),
                new Error(6, "M,,Alfred,+65 1234,A@mail.com,o1,Health",
                        ImportCommand.CAUSE_DUPLICATE_ENTITY).toString(),
                new Error(7, "IN,,,,,,", ImportCommand.CAUSE_INVALID_DATA).toString(),
                ImportCommand.MESSAGE_INVALID_FORMAT
        );
        assertThrows(CommandException.class, () -> new ImportCommand(tempFile.getAbsolutePath()).execute(model),
                expected);
    }

    @Test
    public void execute_csvFileWithTeamsListedFirst_noExceptionThrown() throws CommandException {
        Model expectedModel = new ModelManagerStub();
        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ImportEntities.csv").toFile();
        Model actualModel = new ModelManagerStub();
        File teamFirstFile = TestUtil.getFilePathInCsvUtilTestFolder("TeamFirstValid.csv").toFile();

        String expected = new ImportCommand(expectedFile.toString()).execute(expectedModel).getFeedbackToUser();
        String actual = new ImportCommand(teamFirstFile.toString()).execute(actualModel).getFeedbackToUser();

        assertEquals(expected, actual);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void execute_missingEntities_throwsCommandExceptionAndCorrectCauseGiven() throws CommandException {
        File missingEntity = TestUtil.getFilePathInCsvUtilTestFolder("MissingEntity.csv").toFile();

        String expected = String.join(
                "\n",
                ImportCommand.MESSAGE_PARTIAL_SUCCESS,
                new Error(2, "T,1,Team A,[P-1],,ENVIRONMENTAL,1,Project Alpha,1",
                        String.format(CsvUtil.MESSAGE_MISSING_PARTICIPANT, "P-1")).toString(),
                new Error(3, "T,2,Team B,,M-3,ENVIRONMENTAL,1,Project Alpha,1",
                        String.format(CsvUtil.MESSAGE_MISSING_MENTOR, "M-3")).toString(),
                ImportCommand.MESSAGE_INVALID_FORMAT
        );
        Executable command = () -> new ImportCommand(missingEntity.toString()).execute(model);

        assertThrows(CommandException.class, command, expected);
        assertEquals(new ModelManagerStub(), model);
    }

}
