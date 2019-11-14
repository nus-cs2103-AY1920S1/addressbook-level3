package seedu.address.logic.commands.addcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TeamBuilder;

public class AddTeamCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTeamCommand(null));
    }

    @Test
    public void execute_teamAcceptedByModel_addSuccessful() throws Exception {
        ModelManagerStub modelStub = new ModelManagerStub();
        Team validTeam = new TeamBuilder().build();

        CommandResult commandResult = new AddTeamCommand(validTeam).execute(modelStub);

        assertEquals(String.format(AddTeamCommand.MESSAGE_SUCCESS, validTeam),
                commandResult.getFeedbackToUser());
        assertEquals(validTeam, modelStub.getTeam(ID_FIRST_TEAM));
    }

    @Test
    public void execute_duplicateTeam_throwsCommandException() throws AlfredException {
        Team validTeam = new TeamBuilder().build();
        AddTeamCommand addTeamCommand = new AddTeamCommand(validTeam);
        ModelManagerStub modelStub = new ModelManagerStub();
        addTeamCommand.execute(modelStub);

        Assert.assertThrows(CommandException.class,
                AddTeamCommand.MESSAGE_DUPLICATE_TEAM, () -> addTeamCommand.execute(modelStub));
    }

}
