package seedu.address.logic.commands.commandresults;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.cheatsheet.CheatSheet;

/**
 * Represents the result of a command execution.
 */
public class CheatSheetCommandResult implements CommandResult {

    private final String feedbackToUser;

    /** CheatSheet to display (if any) */
    private final Optional<CheatSheet> cheatsheet;

    /**
     * Constructs a {@code CheatSheetCommandResult} with the specified fields.
     */
    public CheatSheetCommandResult(String feedbackToUser, Optional<CheatSheet> cheatsheet) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.cheatsheet = cheatsheet;
    }

    /**
     * Constructs a {@code CheatSheetCommandResult} with the specified fields.
     */
    public CheatSheetCommandResult(String feedbackToUser) {
        this(feedbackToUser, Optional.empty());
    }

    @Override
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Optional<CheatSheet> getCheatSheet() {
        return cheatsheet;
    }

    @Override
    public boolean isGlobalCommandResult() {
        return false;
    }

    @Override
    public boolean isFlashcardCommandResult() {
        return false;
    }

    @Override
    public boolean isCheatSheetCommandResult() {
        return true;
    }

    @Override
    public boolean isNoteCommandResult() {
        return false;
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
