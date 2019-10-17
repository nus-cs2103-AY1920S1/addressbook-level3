package seedu.mark.logic.commands.results;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.commands.TabCommand.Tab;

/**
 * Represents the result of a tab command execution.
 */
public class TabCommandResult extends CommandResult {

    private Tab tab;

    public TabCommandResult(String feedbackToUser, Tab tab) {
        super(feedbackToUser);
        requireNonNull(tab);
        this.tab = tab;
    }

    @Override
    public Tab getTab() {
        return tab;
    }

}
