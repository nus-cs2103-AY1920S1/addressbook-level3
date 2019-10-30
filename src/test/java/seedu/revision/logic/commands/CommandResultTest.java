package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.CommandResult;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult().withFeedBack("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult().withFeedBack("feedback").build()));
        assertTrue(commandResult.equals(new CommandResult().withFeedBack("feedback")
                .withHelp(false).withExit(false).build()));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult().withFeedBack("different").build()));

        // different withHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult().withFeedBack("feedback")
                .withHelp(true).withExit(false).build()));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult().withFeedBack("feedback")
                .withHelp(false).withExit(true).build()));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult().withFeedBack("feedback").build();

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult().withFeedBack("feedback").build().hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult().withFeedBack("different").build().hashCode());

        // different withHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult().withFeedBack("feedback")
                .withHelp(true).withExit(false).build().hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult().withFeedBack("feedback")
                .withHelp(false).withExit(true).build().hashCode());
    }
}
