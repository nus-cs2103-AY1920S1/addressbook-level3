package seedu.address.logic.commands.deletecommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TeamBuilder;

public class DeleteTeamCommandTest {

    @Test
    public void execute_validIndexTeamList_success() throws AlfredException {
        Model model = new ModelManagerStub();
        Team teamToDelete = new TeamBuilder().build();
        model.addTeam(teamToDelete);
        DeleteTeamCommand deleteCommand = new DeleteTeamCommand(teamToDelete.getId());

        String expectedMessage = String.format(
                DeleteTeamCommand.MESSAGE_DELETE_TEAM_SUCCESS,
                teamToDelete
        );

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(teamToDelete);
        expectedModel.deleteTeam(teamToDelete.getId());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexTeamList_throwsCommandException() {
        Model model = new ModelManagerStub(); // empty model
        Id outOfBoundId = ID_FIRST_TEAM;
        DeleteTeamCommand deleteCommand = new DeleteTeamCommand(outOfBoundId);

        assertCommandFailure(
                deleteCommand,
                model,
                DeleteTeamCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX
        );
    }

}
