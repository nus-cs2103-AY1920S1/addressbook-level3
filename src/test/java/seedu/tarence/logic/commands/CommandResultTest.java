package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.tutorial.Tutorial;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // no valid tab given
        assertFalse(commandResult.isChangeTabs());

        // no valid tutorial given
        assertFalse(commandResult.isShowAttendance());

        // different hasAttendanceDisplay value -> returns false
        Tutorial tutorial = new TutorialBuilder().build();
        assertFalse(commandResult.equals(new CommandResult("feedback", tutorial)));

        // Same tutorial, everything else same, returns true
        commandResult = new CommandResult("feedback", tutorial);
        assertTrue(commandResult.equals(new CommandResult("feedback", tutorial)));

        // Show attendance feature is set
        assertTrue(commandResult.isShowAttendance());

        // Same tutorial is received
        assertTrue(tutorial.equals(commandResult.getTutorialAttendance()));

        // Set tab to display
        commandResult = new CommandResult("feedback", TabNames.MODULES);
        assertTrue(TabNames.MODULES.equals(commandResult.getTabToDisplay()));
        assertFalse(TabNames.STUDENTS.equals(commandResult.getTabToDisplay()));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }
}
