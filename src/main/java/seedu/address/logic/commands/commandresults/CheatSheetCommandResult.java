package seedu.address.logic.commands.commandresults;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.cheatsheet.CheatSheet;

/**
 * Represents the result of a command execution.
 */
public class CheatSheetCommandResult extends CommandResult {

    private final String feedbackToUser;

    /** CheatSheet to display (if any) */
    private final Optional<CheatSheet> cheatsheet;

    private boolean isSwitchTags;

    private Optional<Integer> tagIndex;

    /**
     * Constructs a {@code CheatSheetCommandResult} with the specified fields.
     */
    public CheatSheetCommandResult(String feedbackToUser, Optional<CheatSheet> cheatsheet) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.cheatsheet = cheatsheet;
        tagIndex = Optional.empty();
        isSwitchTags = false;
    }

    /**
     * Constructs a {@code CheatSheetCommandResult} with the specified fields.
     */
    public CheatSheetCommandResult(String feedbackToUser) {
        this(feedbackToUser, Optional.empty());
        tagIndex = Optional.empty();
        isSwitchTags = false;
    }

    public CheatSheetCommandResult(String feedbackToUser, int tagIndex, boolean isSwitchTags) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.cheatsheet = Optional.empty();
        this.tagIndex = Optional.of(tagIndex);
        this.isSwitchTags = isSwitchTags;
    }

    public Optional<Integer> getTagIndex() {
        return tagIndex;
    }

    public boolean isSwitchTags() {
        return isSwitchTags;
    }

    @Override
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Optional<CheatSheet> getCheatSheet() {
        return cheatsheet;
    }

    @Override
    public boolean isCheatSheetCommandResult() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheatSheetCommandResult)) {
            return false;
        }

        CheatSheetCommandResult otherCheatSheetCommandResult = (CheatSheetCommandResult) other;
        return feedbackToUser.equals(otherCheatSheetCommandResult.feedbackToUser)
                && cheatsheet == otherCheatSheetCommandResult.cheatsheet;
    }
    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, cheatsheet);
    }

}
