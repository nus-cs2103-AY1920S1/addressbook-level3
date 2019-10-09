package seedu.mark.logic.commands.commandresult;

import static java.util.Objects.requireNonNull;

import seedu.mark.model.bookmark.Url;

/**
 * Represents the result of a goto command execution.
 */
public class GotoCommandResult extends CommandResult {
    private Url url;

    /**
     * Constructs an {@code GotoCommandResult} with the feedback and url.
     *
     * @param feedbackToUser feedback
     * @param url            url to be opened
     */
    public GotoCommandResult(String feedbackToUser, Url url) {
        super(feedbackToUser);
        this.url = requireNonNull(url);
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public Url getUrl() {
        return url;
    }
}
