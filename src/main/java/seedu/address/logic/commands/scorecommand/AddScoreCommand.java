package seedu.address.logic.commands.scorecommand;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.Team;

/**
 * Command for adding a certain score to a team's current score.
 */
public class AddScoreCommand extends ScoreCommand {

    public static final String MESSAGE_SCORE_TEAM_SUCCESS = "Added %1$s points to %2$s's score"
            + "\n%2$s's score is now: %3$s";
    public static final String COMMAND_WORD = "score add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds the specified score to specified team's current score. "
            + "If the team's new score exceeds " + Score.MAX_SCORE + " it will be set to " + Score.MAX_SCORE + "\n"
            + "Format: " + COMMAND_WORD + " [teamID] score \n"
            + "For example: " + COMMAND_WORD + " T-5 25";
    private final Logger logger = LogsCenter.getLogger(AddScoreCommand.class);

    public AddScoreCommand(Id teamId, Score score) {
        super(teamId, score);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToScore = getTeamFromModel(model, id);
        addScoreToTeam(model, teamToScore, score);

        logger.info("Adding " + this.score + " to Score of Team " + this.id);
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(String.format(MESSAGE_SCORE_TEAM_SUCCESS,
                score, teamToScore.getName().toString(), teamToScore.getScore()), CommandType.T);
    }

    /**
     * Fetches the team {@code team} from {@code model} and adds the score {@code}
     * to their current score.
     *
     * @param model the {@code Model} object from which the team is supposed to be fetched and updated.
     * @param team the Team from model whose score is to be updated.
     * @param score the score to added to the team's current score.
     * @throws CommandException if any exceptional case is encountered while adding the score.
     */
    private void addScoreToTeam(Model model, Team team, Score score) throws CommandException {
        try {
            model.addTeamScore(team, score);
        } catch (AlfredException ae) {
            logger.severe("Error while adding score to team.");
            throw new CommandException(ae.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScoreCommand // instanceof handles nulls
                && id.equals(((AddScoreCommand) other).id))
                && score.equals(((AddScoreCommand) other).score);
    }

}
