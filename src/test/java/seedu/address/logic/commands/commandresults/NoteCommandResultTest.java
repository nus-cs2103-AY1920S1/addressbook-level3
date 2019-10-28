package seedu.address.logic.commands.commandresults;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.testutil.NoteBuilder;

public class NoteCommandResultTest {


    public static final NoteBuilder NOTE_BUILDER = new NoteBuilder();

    @Test
    public void equals() {

        //NoteCommandResult with variables feedbackToUser: "feedback", Optional<Note>: Optional.empty()
        NoteCommandResult commandResult = new NoteCommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new NoteCommandResult("feedback")));
        assertTrue(commandResult.equals(new NoteCommandResult("feedback", Optional.empty())));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new NoteCommandResult("different")));

        // different Optional<Note> value -> returns false
        assertFalse(commandResult.equals(new NoteCommandResult("feedback",
                Optional.of(NOTE_BUILDER.build()))));

    }

    @Test
    public void hashcode() {

        //NoteCommandResult with variables feedbackToUser: "feedback", Optional<Note>: Optional.empty()
        CommandResult commandResult = new NoteCommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new NoteCommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new NoteCommandResult("different").hashCode());

        // different Optional<Note> value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new NoteCommandResult("feedback",
                Optional.of(NOTE_BUILDER.build())).hashCode());
    }
}
