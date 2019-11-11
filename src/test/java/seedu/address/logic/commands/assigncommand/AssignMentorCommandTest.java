package seedu.address.logic.commands.assigncommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_MENTOR;
import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalTeams;


public class AssignMentorCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignMentorCommand(ID_FIRST_MENTOR, null));
    }

    @Test
    public void constructor_nullMentor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignMentorCommand(null, ID_FIRST_TEAM));
    }

    @Test
    public void constructor_nullBoth_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignMentorCommand(null, null));
    }

    @Test
    public void execute_mentorNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Team validTeam = TypicalTeams.EMPTY.copy();
        Mentor validMentor = TypicalMentors.A.copy();

        modelStub.addTeam(validTeam);


        AssignMentorCommand command = new AssignMentorCommand(validMentor.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                "The mentor ID provided is invalid or does not exist.", () -> command.execute(modelStub));
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Team validTeam = TypicalTeams.EMPTY.copy();
        Mentor validMentor = TypicalMentors.A.copy();

        modelStub.addMentor(validMentor);


        AssignMentorCommand command = new AssignMentorCommand(validMentor.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                "The Team ID provided is invalid or does not exist.", () -> command.execute(modelStub));
    }

    @Test
    public void execute_mentorAssignedInModel_assignSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Team validTeam = TypicalTeams.EMPTY.copy();
        Mentor validMentor = TypicalMentors.A.copy();

        modelStub.addTeam(validTeam);
        modelStub.addMentor(validMentor);

        CommandResult commandResult =
                new AssignMentorCommand(validMentor.getId(), validTeam.getId()).execute(modelStub);

        assertEquals(
                String.format(
                        AssignMentorCommand.MESSAGE_ASSIGN_MENTOR_SUCCESS,
                        validMentor.getName(),
                        validMentor.getId(),
                        validTeam.getName(),
                        validTeam.getId()
                ),
                commandResult.getFeedbackToUser()
        );
        assertEquals(validMentor, modelStub.getTeam(validTeam.getId()).getMentor().get());
    }

    @Test
    public void execute_teamAlreadyHasMentor_throwsCommandException() throws AlfredException {
        ModelManagerStub modelStub = new ModelManagerStub();
        Team validTeam = TypicalTeams.EMPTY.copy();
        Mentor validMentor = TypicalMentors.A.copy();
        Mentor validMentor2 = TypicalMentors.B.copy();

        modelStub.addTeam(validTeam);
        modelStub.addMentor(validMentor);
        modelStub.addMentor(validMentor2);

        AssignMentorCommand command = new AssignMentorCommand(validMentor.getId(), validTeam.getId());
        command.execute(modelStub);

        AssignMentorCommand command2 = new AssignMentorCommand(validMentor2.getId(), validTeam.getId());

        assertThrows(
                CommandException.class,
                AssignMentorCommand.MESSAGE_TEAM_HAS_MENTOR, () -> command2.execute(modelStub));

    }

}
