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

class SubtractScoreCommandTest {

    public static final Id VALID_TEAM_ID = new Id(PrefixType.T, 1);
    public static final Score VALID_SCORE = new Score(20);

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        // Null team id inputted.
        assertThrows(NullPointerException.class, () -> new SubtractScoreCommand(null, VALID_SCORE));
        // Null score inputted.
        assertThrows(NullPointerException.class, () -> new SubtractScoreCommand(VALID_TEAM_ID, null));
        // Both inputs are null.
        assertThrows(NullPointerException.class, () -> new SubtractScoreCommand(null, null));
    }

    @Test
    public void execute_validParameters_success() throws AlfredException {
        Model model = new ModelManagerStub();
        Team teamToScore = new TeamBuilder().build();
        Team subtractedScoreTeam = new TeamBuilder()
                .withScore(teamToScore.getScore().getScore() - VALID_SCORE.getScore()).build();
        model.addTeam(teamToScore);

        SubtractScoreCommand subtractScoreCommand = new SubtractScoreCommand(VALID_TEAM_ID, VALID_SCORE);

        String expectedMessage = String.format(SubtractScoreCommand.MESSAGE_SCORE_TEAM_SUCCESS,
                VALID_SCORE, teamToScore.getName(), teamToScore.getScore().getScore() - VALID_SCORE.getScore());

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(subtractedScoreTeam);
        assertCommandSuccess(subtractScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTeamId_commandFailure() {
        Model model = new ModelManagerStub(); // empty model
        SubtractScoreCommand subtractScoreCommand = new SubtractScoreCommand(VALID_TEAM_ID, VALID_SCORE);

        assertCommandFailure(subtractScoreCommand, model, MESSAGE_NON_EXISTENT_TEAM);
    }

    @Test
    public void execute_subtractedScoreBelowMinimum_scoreSetToMinimum() throws AlfredException {
        Model model = new ModelManagerStub(); // empty model
        Team teamToScore = new TeamBuilder().withScore(5).build();
        Team teamWithMinScore = new TeamBuilder().withScore(0).build();
        model.addTeam(teamToScore);
        SubtractScoreCommand subtractScoreCommand = new SubtractScoreCommand(teamToScore.getId(), VALID_SCORE);

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(teamWithMinScore);
        String expectedMessage = String.format(SubtractScoreCommand.MESSAGE_SCORE_TEAM_SUCCESS,
                VALID_SCORE, teamToScore.getName(), teamWithMinScore.getScore());

        assertCommandSuccess(subtractScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_teamScoreAtMinimum_scoreUnchanged() throws AlfredException {
        Model model = new ModelManagerStub(); // empty model
        Team teamToScore = new TeamBuilder().withScore(0).build();
        Team teamWithMinScore = new TeamBuilder().withScore(0).build();
        model.addTeam(teamToScore);
        SubtractScoreCommand subtractScoreCommand = new SubtractScoreCommand(teamToScore.getId(), VALID_SCORE);

        Model expectedModel = new ModelManagerStub();
        expectedModel.addTeam(teamWithMinScore);

        String expectedMessage = Score.MIN_SCORE_MESSAGE;

        // Checks if both teams are equal after adding score, which should be true as the score shouldn't be below 0.
        assertEquals(teamToScore, teamWithMinScore);
        // Checks if the appropriate message is shown when this command is run.
        assertCommandFailure(subtractScoreCommand, model, expectedMessage);
    }
}
