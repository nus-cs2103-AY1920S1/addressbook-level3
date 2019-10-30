package seedu.mark.logic.commands.results;

import seedu.mark.logic.commands.TabCommand.Tab;

/**
 * Represents the result of an expand/collapse command
 */
public class ExpandCommandResult extends CommandResult {

    private int levelsToExpand;

    /**
     * Constructs a {@code ExpandCommandResult} with the feedback and level to expand.
     *
     * @param feedbackToUser
     * @param levelsToExpand the number of levels to expand (negative if collapsing)
     */
    public ExpandCommandResult(String feedbackToUser, int levelsToExpand) {
        super(feedbackToUser);
        this.levelsToExpand = levelsToExpand;
    }

    @Override
    public int getLevelsToExpand() {
        return this.levelsToExpand;
    }

    @Override
    public Tab getTab() {
        return Tab.DASHBOARD;
    }
}
