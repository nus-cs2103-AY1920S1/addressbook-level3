package seedu.address.logic.commands.scorecommand;

import static seedu.address.commons.core.Messages.MESSAGE_NON_EXISTENT_TEAM;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TeamBuilder;

class SetScoreCommandTest {

    public static final Id VALID_TEAM_ID = new Id(PrefixType.T, 1);
    public static final Score VALID_SCORE = new Score(20);

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        // Null team id inputted.
        assertThrows(NullPointerException.class, () -> new SetScoreCommand(null, VALID_SCORE));
        // Null score inputted.
        assertThrows(NullPointerException.class, () -> new SetScoreCommand(VALID_TEAM_ID, null));
        // Both inputs are null.
        assertThrows(NullPointerException.class, () -> new SetScoreCommand(null, null));
    }

    @Test
    public void execute_validParameters_success() throws AlfredException {
        Model model = new ModelManagerStub();
        Team teamToScore = new TeamBuilder().build();
        model.addTeam(teamToScore);
        SetScoreCommand setScoreCommand = new SetScoreCommand(VALID_TEAM_ID, VALID_SCORE);

        String expectedMessage = String.format(SetScoreCommand.MESSAGE_SCORE_TEAM_SUCCESS,
                teamToScore.getName().toString(), VALID_SCORE.toString());

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(teamToScore);
        expectedModel.setTeamScore(teamToScore, VALID_SCORE);

        assertCommandSuccess(setScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTeamId_commandFailure() {
        Model model = new ModelManagerStub(); // empty model
        SetScoreCommand setScoreCommand = new SetScoreCommand(VALID_TEAM_ID, VALID_SCORE);

        assertCommandFailure(setScoreCommand, model, MESSAGE_NON_EXISTENT_TEAM);
    }
}
