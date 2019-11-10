package seedu.address.logic.commands.removecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PARTICIPANT;
import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.assigncommand.AssignParticipantCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.ParticipantBuilder;
import seedu.address.testutil.TeamBuilder;


public class RemoveParticipantCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveParticipantCommand(ID_FIRST_PARTICIPANT, null));
    }

    @Test
    public void constructor_nullParticipant_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveParticipantCommand(null, ID_FIRST_TEAM));
    }

    @Test
    public void constructor_nullBoth_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveParticipantCommand(null, null));
    }

    @Test
    public void execute_participantNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = new ParticipantBuilder().build();
        Team validTeam = new TeamBuilder().build();

        modelStub.addTeam(validTeam);


        RemoveParticipantCommand command = new RemoveParticipantCommand(validParticipant.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                RemoveParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = new ParticipantBuilder().build();
        Team validTeam = new TeamBuilder().build();

        modelStub.addParticipant(validParticipant);


        RemoveParticipantCommand command = new RemoveParticipantCommand(validParticipant.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                RemoveParticipantCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_removeParticipantFromTeam_removeSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = new ParticipantBuilder().build();
        Team validTeam = new TeamBuilder().build();

        modelStub.addTeam(validTeam);
        modelStub.addParticipant(validParticipant);

        //add mentor to team
        AssignParticipantCommand assignParticipantCommand =
                new AssignParticipantCommand(validParticipant.getId(), validTeam.getId());
        assignParticipantCommand.execute(modelStub);

        CommandResult commandResult =
                new RemoveParticipantCommand(validParticipant.getId(), validTeam.getId()).execute(modelStub);

        assertEquals(
                String.format(
                        RemoveParticipantCommand.MESSAGE_REMOVE_PARTICIPANT_SUCCESS,
                        validParticipant.getName(), validParticipant.getId(), validTeam.getName(), validTeam.getId()
                ),
                commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_participantNotInTeam_throwsCommandException() throws AlfredException {
        ModelManagerStub modelStub = new ModelManagerStub();
        Participant validParticipant = new ParticipantBuilder().build();
        Team validTeam = new TeamBuilder().withId(10).build();

        modelStub.addTeam(validTeam);
        modelStub.addParticipant(validParticipant);


        RemoveParticipantCommand command = new RemoveParticipantCommand(validParticipant.getId(), validTeam.getId());


        assertThrows(
                CommandException.class,
                RemoveParticipantCommand.MESSAGE_TEAM_DOES_NOT_HAVE_PARTICIPANT, () -> command.execute(modelStub));

    }

}
