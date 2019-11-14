package seedu.address.logic.commands.scorecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

class AddScoreCommandTest {

    public static final Id VALID_TEAM_ID = new Id(PrefixType.T, 1);
    public static final Score VALID_SCORE = new Score(20);

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        // Null team id inputted.
        assertThrows(NullPointerException.class, () -> new AddScoreCommand(null, VALID_SCORE));
        // Null score inputted.
        assertThrows(NullPointerException.class, () -> new AddScoreCommand(VALID_TEAM_ID, null));
        // Both inputs are null.
        assertThrows(NullPointerException.class, () -> new AddScoreCommand(null, null));
    }

    @Test
    public void execute_validParameters_success() throws AlfredException {
        Model model = new ModelManagerStub();
        Team teamToScore = new TeamBuilder().build();
        Team addedScoreTeam = new TeamBuilder()
                .withScore(teamToScore.getScore().getScore() + VALID_SCORE.getScore()).build();
        model.addTeam(teamToScore);
        AddScoreCommand addScoreCommand = new AddScoreCommand(VALID_TEAM_ID, VALID_SCORE);

        String expectedMessage = String.format(AddScoreCommand.MESSAGE_SCORE_TEAM_SUCCESS,
                VALID_SCORE, teamToScore.getName(), teamToScore.getScore().getScore() + VALID_SCORE.getScore());

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(addedScoreTeam);
        assertCommandSuccess(addScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTeamId_commandFailure() {
        Model model = new ModelManagerStub(); // empty model
        AddScoreCommand addScoreCommand = new AddScoreCommand(VALID_TEAM_ID, VALID_SCORE);

        assertCommandFailure(addScoreCommand, model, MESSAGE_NON_EXISTENT_TEAM);
    }

    @Test
    public void execute_summedScoreExceedsMaximum_scoreSetToMaximum() throws AlfredException {
        Model model = new ModelManagerStub(); // empty model
        Team teamToScore = new TeamBuilder().withScore(99).build();
        Team teamWithMaxScore = new TeamBuilder().withScore(100).build();
        model.addTeam(teamToScore);
        AddScoreCommand addScoreCommand = new AddScoreCommand(teamToScore.getId(), VALID_SCORE);

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(teamWithMaxScore);
        String expectedMessage = String.format(AddScoreCommand.MESSAGE_SCORE_TEAM_SUCCESS,
                VALID_SCORE, teamToScore.getName(), teamWithMaxScore.getScore());

        assertCommandSuccess(addScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_teamScoreAtMaximum_scoreUnchanged() throws AlfredException {
        Model model = new ModelManagerStub(); // empty model
        Team teamToScore = new TeamBuilder().withScore(100).build();
        Team teamWithMaxScore = new TeamBuilder().withScore(100).build();
        model.addTeam(teamToScore);
        AddScoreCommand addScoreCommand = new AddScoreCommand(teamToScore.getId(), VALID_SCORE);

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(teamWithMaxScore);

        String expectedMessage = Score.MAX_SCORE_MESSAGE;

        // Checks if both teams are equal after adding score, which should be true as the score shouldn't be beyond 100.
        assertEquals(teamToScore, teamWithMaxScore);
        // Checks if the appropriate message is shown when this command is run.
        assertCommandFailure(addScoreCommand, model, expectedMessage);
    }
}
