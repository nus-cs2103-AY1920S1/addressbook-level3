package seedu.system.logic.commands.insession;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.OutOfSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.session.ParticipationAttempt;
import seedu.system.model.session.exceptions.CompetitionEndedException;
import seedu.system.model.session.exceptions.IncompleteAttemptSubmissionException;
import seedu.system.model.session.exceptions.NoOngoingSessionException;
import seedu.system.model.session.exceptions.PreviousAttemptNotDoneException;

/**
 * Gets the next lifter in line to make his/her attempt.
 */
public class NextLifterCommand extends Command {

    public static final String COMMAND_WORD = "next";
    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;
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
    public CommandResult execute(Model model) throws OutOfSessionCommandException {

        if (!model.hasOngoingSession()) {
            throw new OutOfSessionCommandException();
        }

        try {
            next = model.getNextLifter();
            following = model.getFollowingLifter();
        } catch (NoOngoingSessionException | IncompleteAttemptSubmissionException e) {
            return new CommandResult(e.getMessage());
        } catch (PreviousAttemptNotDoneException e) {
            return new CommandResult(e.getMessage() + followingAttemptToString());
        } catch (CompetitionEndedException e) {
            return new CommandResult(e.getMessage());
        }
        return new CommandResult(String.format("%S%n%s", next.toString(), followingAttemptToString()), COMMAND_TYPE);
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
