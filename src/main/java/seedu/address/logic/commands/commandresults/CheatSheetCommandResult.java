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
    private final Optional<CheatSheet> CheatSheet;

    /**
     * Constructs a {@code CheatSheetCommandResult} with the specified fields.
     */
    public CheatSheetCommandResult(String feedbackToUser, Optional<CheatSheet> CheatSheet) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.CheatSheet = CheatSheet;
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
        return CheatSheet;
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
                && CheatSheet == otherCheatSheetCommandResult.CheatSheet;
    }
    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, CheatSheet);
    }

}
