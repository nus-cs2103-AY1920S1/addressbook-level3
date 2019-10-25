package seedu.address.logic.commands.commandresults;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.testutil.FlashcardBuilder;

public class FlashcardCommandResultTest {


    public static final FlashcardBuilder FLASHCARD_BUILDER = new FlashcardBuilder();

    @Test
    public void equals() {

        //FlashcardCommandResult with variables feedbackToUser: "feedback", Optional<Flashcard>: Optional.empty()
        FlashcardCommandResult commandResult = new FlashcardCommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new FlashcardCommandResult("feedback")));
        assertTrue(commandResult.equals(new FlashcardCommandResult("feedback", Optional.empty())));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new FlashcardCommandResult("different")));

        // different Optional<Flashcard> value -> returns false
        assertFalse(commandResult.equals(new FlashcardCommandResult("feedback",
                Optional.of(FLASHCARD_BUILDER.build()))));

    }

    @Test
    public void hashcode() {

        //FlashcardCommandResult with variables feedbackToUser: "feedback", Optional<Flashcard>: Optional.empty()
        CommandResult commandResult = new FlashcardCommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new FlashcardCommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new FlashcardCommandResult("different").hashCode());

        // different Optional<Flashcard> value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new FlashcardCommandResult("feedback",
                Optional.of(FLASHCARD_BUILDER.build())).hashCode());
    }
}
