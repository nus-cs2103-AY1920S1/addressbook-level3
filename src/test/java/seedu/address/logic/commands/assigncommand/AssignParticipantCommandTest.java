package seedu.address.logic.commands.assigncommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PARTICIPANT;
import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

public class AssignParticipantCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignParticipantCommand(ID_FIRST_PARTICIPANT, null));
    }

    @Test
    public void constructor_nullParticipant_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignParticipantCommand(null, ID_FIRST_TEAM));
    }

    @Test
    public void constructor_nullBoth_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignParticipantCommand(null, null));
    }

    @Test
    public void execute_participantNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = TypicalParticipants.A.copy();
        Team validTeam = TypicalTeams.EMPTY.copy();

        modelStub.addTeam(validTeam);


        AssignParticipantCommand command = new AssignParticipantCommand(validParticipant.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                "The Participant ID provided is invalid or does not exist.", () -> command.execute(modelStub));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = TypicalParticipants.A.copy();
        Team validTeam = TypicalTeams.EMPTY.copy();

        modelStub.addParticipant(validParticipant);


        AssignParticipantCommand command = new AssignParticipantCommand(validParticipant.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                "The Team ID provided is invalid or does not exist.", () -> command.execute(modelStub));
    }

    @Test
    public void execute_participantAssignedInModel_assignSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = TypicalParticipants.A.copy();
        Team validTeam = TypicalTeams.EMPTY.copy();

        modelStub.addTeam(validTeam);
        modelStub.addParticipant(validParticipant);

        CommandResult commandResult =
                new AssignParticipantCommand(validParticipant.getId(), validTeam.getId()).execute(modelStub);

        assertEquals(
                String.format(
                        AssignParticipantCommand.MESSAGE_ASSIGN_PARTICIPANT_SUCCESS,
                        validParticipant.getName(),
                        validParticipant.getId(),
                        validTeam.getName(),
                        validTeam.getId()
                ),
                commandResult.getFeedbackToUser()
        );
        assertEquals(validParticipant, modelStub.getTeam(validTeam.getId()).getParticipants().get(0));
    }

    @Test
    public void execute_participantAlreadyInTeam_throwsCommandException() throws AlfredException {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = TypicalParticipants.A.copy();
        Team validTeam = TypicalTeams.EMPTY.copy();

        modelStub.addTeam(validTeam);
        modelStub.addParticipant(validParticipant);
        modelStub.addParticipantToTeam(validTeam.getId(), validParticipant);

        AssignParticipantCommand command = new AssignParticipantCommand(validParticipant.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                "Participant is already present in team", () -> command.execute(modelStub));

    }

}




