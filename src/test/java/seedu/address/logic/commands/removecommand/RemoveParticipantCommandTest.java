package seedu.address.logic.commands.removecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PARTICIPANT;
import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.assigncommand.AssignParticipantCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;


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

        TypicalTeams.clearTeamEmpty();

        modelStub.addTeam(TypicalTeams.EMPTY.copy());


        RemoveParticipantCommand command =
                new RemoveParticipantCommand(
                        TypicalParticipants.A.copy().getId(),
                        TypicalTeams.EMPTY.getId());

        assertThrows(
                CommandException.class,
                RemoveParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();

        modelStub.addParticipant(TypicalParticipants.A.copy());


        RemoveParticipantCommand command =
                new RemoveParticipantCommand(
                        TypicalParticipants.A.copy().getId(),
                        TypicalTeams.EMPTY.getId());

        assertThrows(
                CommandException.class,
                RemoveParticipantCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_removeParticipantFromTeam_removeSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();

        modelStub.addTeam(TypicalTeams.EMPTY.copy());

        modelStub.addParticipant(TypicalParticipants.A.copy());

        //add mentor to team
        AssignParticipantCommand assignParticipantCommand =
                new AssignParticipantCommand(TypicalParticipants.A.copy().getId(), TypicalTeams.EMPTY.getId());
        assignParticipantCommand.execute(modelStub);

        CommandResult commandResult =
                new RemoveParticipantCommand(
                        TypicalParticipants.A.copy().getId(),
                        TypicalTeams.EMPTY.getId())
                        .execute(modelStub);

        assertEquals(
                String.format(
                        RemoveParticipantCommand.MESSAGE_REMOVE_PARTICIPANT_SUCCESS,
                        TypicalParticipants.A.getName(),
                        TypicalParticipants.A.getId(),
                        TypicalTeams.EMPTY.getName(),
                        TypicalTeams.EMPTY.getId()
                ),
                commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_participantNotInTeam_throwsCommandException() throws AlfredException {
        ModelManagerStub modelStub = new ModelManagerStub();



        modelStub.addTeam(TypicalTeams.EMPTY);
        modelStub.addParticipant(TypicalParticipants.A.copy());

        RemoveParticipantCommand command =
                new RemoveParticipantCommand(TypicalParticipants.A.getId(), TypicalTeams.EMPTY.getId());

        assertThrows(
                CommandException.class,
                RemoveParticipantCommand.MESSAGE_TEAM_DOES_NOT_HAVE_PARTICIPANT, () -> command.execute(modelStub));

    }

}
