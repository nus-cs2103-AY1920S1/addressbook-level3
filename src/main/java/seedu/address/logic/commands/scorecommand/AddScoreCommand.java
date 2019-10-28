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
 * Command for adding a certain score to a team's current score.
 */
public class AddScoreCommand extends ScoreCommand {

    public static final String MESSAGE_SCORE_TEAM_SUCCESS = "Added %1$s points to %2$s's score";
    public static final String COMMAND_WORD = "score add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds the specified score to specified team's current score. "
            + "If the team's new score exceeds " + Score.MAX_SCORE + " it will be set to " + Score.MAX_SCORE + "\n"
            + "Format: " + COMMAND_WORD + " [teamID] score \n"
            + "For example: " + COMMAND_WORD + " T-5 25";
    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    public AddScoreCommand(Id teamId, Score score) {
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
            model.addTeamScore(teamToScore, score);
        } catch (AlfredException ae) {
            throw new CommandException(ae.getMessage());
        }

        logger.info("Adding " + this.score + " to Score of Team " + this.id);
        return new CommandResult(String.format(MESSAGE_SCORE_TEAM_SUCCESS,
                score.toString(), teamToScore.getName().toString()), CommandType.T);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetScoreCommand // instanceof handles nulls
                && id.equals(((AddScoreCommand) other).id))
                && score.equals(((AddScoreCommand) other).score);
    }

}
