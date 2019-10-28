package seedu.address.logic.commands.scorecommand;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;

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

    public static final String MESSAGE_SCORE_TEAM_SUCCESS = "Subtracted %1$s points from %2$s's score";
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

        try {
            teamToScore = model.getTeam(id);
        } catch (AlfredException ae) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        try {
            model.subtractTeamScore(teamToScore, score);
        } catch (AlfredException e) {
            throw new CommandException(e.getMessage());
        }

        logger.info("Subtracting " + this.score + " from Score of Team " + this.id);
        return new CommandResult(String.format(MESSAGE_SCORE_TEAM_SUCCESS,
                score.toString(), teamToScore.getName().toString()), CommandType.T);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetScoreCommand // instanceof handles nulls
                && id.equals(((SubtractScoreCommand) other).id))
                && score.equals(((SubtractScoreCommand) other).score);
    }

}
