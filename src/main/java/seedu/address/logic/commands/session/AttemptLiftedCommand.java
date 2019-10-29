package seedu.address.logic.commands.session;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attempt.exceptions.AttemptHasBeenAttemptedException;
import seedu.address.model.participation.Participation;
import seedu.address.model.session.ParticipationAttempt;
import seedu.address.model.session.exceptions.IncompleteAttemptSubmissionException;
import seedu.address.model.session.exceptions.NoOngoingSessionException;

/**
 * Records and updates the success of failure of the attempt that was lifted.
 */
public class AttemptLiftedCommand extends Command {

    public static final String COMMAND_WORD = "lift";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " Y/N (where 'Y' represents success and 'N' represents failure)";
    public static final String MESSAGE_NEXT_LIFTER = "The next lifter is ";

    private final boolean isSuccess;

    public AttemptLiftedCommand(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * Executes AttemptLiftedCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ParticipationAttempt nextPartAttempt;
        ParticipationAttempt followingPartAttempt;
        Participation participation;

        try {
            followingPartAttempt = model.getFollowingLifter();
            nextPartAttempt = model.makeAttempt();
            participation = nextPartAttempt.getParticipation();
            Participation updatedPart = participation;
            updatedPart.updateAttempt(nextPartAttempt.getAttemptIndex(), isSuccess);
            model.setParticipation(participation, updatedPart);
        } catch (NoOngoingSessionException | AttemptHasBeenAttemptedException
                | IncompleteAttemptSubmissionException e) {
            return new CommandResult(e.getMessage());
        }

        return new CommandResult(String.format("%S%s%n%s%s", nextPartAttempt.toString(), getSuccessOrFailure(),
                MESSAGE_NEXT_LIFTER, followingPartAttempt.toString()));
    }

    private String getSuccessOrFailure() {
        if (isSuccess) {
            return "  is a success.";
        } else {
            return "  is a failure.";
        }
    }
}
