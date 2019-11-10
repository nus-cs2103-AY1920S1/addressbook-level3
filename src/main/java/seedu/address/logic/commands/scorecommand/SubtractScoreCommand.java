package seedu.address.logic.commands.scorecommand;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.Team;

/**
 * Subtracts a score from a team's current score.
 */
public class SubtractScoreCommand extends ScoreCommand {

    public static final String MESSAGE_SCORE_TEAM_SUCCESS = "Subtracted %1$s points from %2$s's score"
            + "\n%2$s's score is now: %3$s";
    public static final String COMMAND_WORD = "score sub";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": subtracts the specified score from the specified team's current score. "
            + "If the team's new score goes below " + Score.MIN_SCORE + " it will be set to " + Score.MIN_SCORE + "\n"
            + "Format: " + COMMAND_WORD + " teamID score \n"
            + "For example: " + COMMAND_WORD + " T-5 15";
    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    public SubtractScoreCommand(Id teamId, Score score) {
        super(teamId, score);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Team teamToScore;

        teamToScore = getTeamFromModel(model, id);
        subtractScoreFromTeam(model, teamToScore, score);

        logger.info("Subtracting " + this.score + " from Score of Team " + this.id);
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(String.format(MESSAGE_SCORE_TEAM_SUCCESS,
                score, teamToScore.getName(), teamToScore.getScore()), CommandType.T);
    }

    /**
     * Fetches the {@code team} from {@code model} and subtracts {@code score} from their
     * current score.
     *
     * @param model the {@code Model} object from which the team is supposed to be fetched and updated.
     * @param team the Team from model whose score is to be updated.
     * @param score the score to subtract from the team's current score.
     * @throws CommandException if an exceptional case arises when subtracting the team's score.
     */
    private void subtractScoreFromTeam(Model model, Team team, Score score) throws CommandException {
        try {
            model.subtractTeamScore(team, score);
        } catch (AlfredException ae) {
            logger.severe("Error while subtracting score from team.");
            throw new CommandException(ae.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubtractScoreCommand // instanceof handles nulls
                && id.equals(((SubtractScoreCommand) other).id))
                && score.equals(((SubtractScoreCommand) other).score);
    }

}
