package seedu.address.logic.commands.commandresults;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.flashcard.Flashcard;

/**
 * Represents the result of a command execution.
 */
public class FlashcardCommandResult implements CommandResult {

    private final String feedbackToUser;

    /** Flashcard to display (if any) */
    private final Optional<Flashcard> flashcard;

    /**
     * Constructs a {@code FlashcardCommandResult} with the specified fields.
     */
    public FlashcardCommandResult(String feedbackToUser, Optional<Flashcard> flashcard) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.flashcard = flashcard;
    }

    /**
     * Constructs a {@code FlashcardCommandResult} with the specified fields.
     */
    public FlashcardCommandResult(String feedbackToUser) {
        this(feedbackToUser, Optional.empty());
    }

    @Override
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Optional<Flashcard> getFlashcard() {
        return flashcard;
    }

    @Override
    public boolean isGlobalCommandResult() {
        return false;
    }

    @Override
    public boolean isFlashcardCommandResult() {
        return true;
    }

    @Override
    public boolean isCheatSheetCommandResult() {
        return false;
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
        if (!(other instanceof FlashcardCommandResult)) {
            return false;
        }

        FlashcardCommandResult otherFlashcardCommandResult = (FlashcardCommandResult) other;
        return feedbackToUser.equals(otherFlashcardCommandResult.feedbackToUser)
                && flashcard == otherFlashcardCommandResult.flashcard;
    }
    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, flashcard);
    }

}
