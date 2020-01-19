package seedu.mark.logic.commands.results;

import seedu.mark.logic.commands.TabCommand;

/**
 * Represents the result of an goto command execution.
 */
public class GotoCommandResult extends CommandResult {

    /**
     * Constructs a {@code GotoCommandResult} with the feedback.
     *
     * @param feedbackToUser the feedback to the user
     */
    public GotoCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    @Override
    public TabCommand.Tab getTab() {
        return TabCommand.Tab.ONLINE;
    }
}
