package seedu.address.logic.commands.commandresults;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.flashcard.Flashcard;

/**
 * Represents the result of a command execution.
 */
public class FlashcardCommandResult extends CommandResult {

    private boolean isTimeTrial;
    private boolean isShowAns;

    private final String feedbackToUser;

    /** Flashcard to display (if any) */
    private final Optional<Flashcard> flashcard;

    private final Optional<ArrayList<Flashcard>> deck;

    /**
     * Constructs a {@code FlashcardCommandResult} with the specified fields.
     */
    public FlashcardCommandResult(String feedbackToUser, Optional<Flashcard> flashcard) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.flashcard = flashcard;
        deck = Optional.empty();
        isTimeTrial = false;
        isShowAns = false;
    }

    /**
     * Constructs a {@code FlashcardCommandResult} with the specified fields.
     */
    public FlashcardCommandResult(String feedbackToUser, boolean isTimeTrial, Optional<ArrayList<Flashcard>> deck) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.flashcard = Optional.empty();
        this.isTimeTrial = isTimeTrial;
        this.deck = deck;
    }

    /**
     * Constructs a {@code FlashcardCommandResult} with the specified fields.
     */
    public FlashcardCommandResult(String feedbackToUser, boolean isShowAns) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.flashcard = Optional.empty();
        this.deck = Optional.empty();
        this.isTimeTrial = false;
        this.isShowAns = isShowAns;
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

    public boolean isTimeTrial() {
        return isTimeTrial;
    }

    public boolean isShowAns() {
        return isShowAns;
    }

    public Optional<ArrayList<Flashcard>> getDeck() {
        return deck;
    }

    @Override
    public boolean isFlashcardCommandResult() {
        return true;
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
