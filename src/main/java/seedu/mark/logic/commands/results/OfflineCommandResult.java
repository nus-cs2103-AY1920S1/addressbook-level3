package seedu.mark.logic.commands.results;

import seedu.mark.logic.commands.TabCommand.Tab;

/**
 * Represents the result of an offline command execution.
 */
public class OfflineCommandResult extends CommandResult {

    /**
     * Constructs a {@code OfflineCommandResult} with the feedback.
     *
     * @param feedbackToUser the feedback to the user
     */
    public OfflineCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    @Override
    public Tab getTab() {
        return Tab.OFFLINE;
    }
}
