package seedu.mark.logic.commands.results;

import static java.util.Objects.requireNonNull;

import seedu.mark.model.bookmark.Url;

/**
 * Represents the result of a goto command execution.
 */
public class GotoCommandResult extends CommandResult {
    private Url url;

    /**
     * Constructs a {@code GotoCommandResult} with the feedback and url.
     *
     * @param feedbackToUser The feedback to be displayed to user
     * @param url The url to be opened
     */
    public GotoCommandResult(String feedbackToUser, Url url) {
        super(feedbackToUser);
        this.url = requireNonNull(url);
    }

    @Override
    public Url getUrl() {
        return url;
    }
}
