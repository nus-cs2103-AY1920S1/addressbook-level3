package seedu.address.logic.commands.session;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.session.ParticipationAttempt;
import seedu.address.model.session.exceptions.CompetitionEndedException;
import seedu.address.model.session.exceptions.IncompleteAttemptSubmissionException;
import seedu.address.model.session.exceptions.NoOngoingSessionException;
import seedu.address.model.session.exceptions.PreviousAttemptNotDoneException;

/**
 * Gets the next lifter in line to make his/her attempt.
 */
public class NextLifterCommand extends Command {

    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_NEXT_LIFTER = "The next lifter is ";

    private ParticipationAttempt next = null;
    private ParticipationAttempt following = null;

    /**
     * Executes the NextLifterCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        try {
            next = model.getNextLifter();
            following = model.getFollowingLifter();
        } catch (NoOngoingSessionException | IncompleteAttemptSubmissionException
                | PreviousAttemptNotDoneException e) {
            return new CommandResult(e.getMessage());
        } catch (CompetitionEndedException e) {
            return new CommandResult(e.getMessage());
        }
        return new CommandResult(String.format("%S%n%s", next.toString(), followingAttemptToString()));
    }

    /**
     * Returns the information for the following attempt and the lifter.
     */
    private String followingAttemptToString() {
        if (following == null) {
            return "This is the last attempt for the competition.";
        } else {
            return MESSAGE_NEXT_LIFTER + following.toString();
        }
    }
}
