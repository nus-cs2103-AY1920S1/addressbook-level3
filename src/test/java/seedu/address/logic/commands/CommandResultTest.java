package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.ui.tab.Tab;

public class CommandResultTest {
    @Test
    public void getFeedBackToUser() {
        CommandResult commandResult = new CommandResult("feedback");
        String feedback = commandResult.getFeedbackToUser();

        // same values -> returns true
        assertEquals(feedback, new CommandResult("feedback").getFeedbackToUser());

        // different values -> returns false
        assertNotEquals(feedback, new CommandResult("false").getFeedbackToUser());

        CommandResult warningCommandResult = new CommandResult(
            "feedback", "warnings1", "warnings2");
        String warningFeedback = warningCommandResult.getFeedbackToUser();

        // same values -> returns true
        assertEquals(warningFeedback, new CommandResult(
            "feedback", "warnings1", "warnings2").getFeedbackToUser());

        // different values -> returns false
        assertNotEquals(warningFeedback, new CommandResult(
            "feedback", "warnings", "warningsFalse"));
    }

    @Test
    public void isShowHelp() {
        CommandResult commandResult =
            new CommandResult("feedback", true, false, null, null);
        boolean showHelp = commandResult.isShowHelp();

        // same values -> returns true
        assertEquals(showHelp,
            new CommandResult("feedback", true, false, null, null).isShowHelp());

        // different values -> returns false
        assertNotEquals(showHelp,
            new CommandResult("feedback", false, false, null, null).isShowHelp());
    }

    @Test
    public void isExit() {
        CommandResult commandResult =
            new CommandResult("feedback", true, false, null, null);
        boolean exit = commandResult.isExit();

        // same values -> returns true
        assertEquals(exit,
            new CommandResult("feedback", true, false, null, null).isExit());

        // different values -> returns false
        assertNotEquals(exit,
            new CommandResult("feedback", false, true, null, null).isExit());
    }

    @Test
    public void isSwitchTab() {
        CommandResult commandResult =
            new CommandResult("feedback", true, false, Tab.BUDGET, null);
        Tab tab = commandResult.getTab();
        boolean switchTab = commandResult.isSwitchTab();

        // same values -> returns true
        assertEquals(tab,
            new CommandResult("feedback", true, false, Tab.BUDGET, null).getTab());
        assertEquals(switchTab,
            new CommandResult("feedback", true, false, Tab.BUDGET, null).isSwitchTab());

        // different values -> returns false
        assertNotEquals(tab,
            new CommandResult("feedback", false, false, Tab.TRANSACTION, null).getTab());
    }

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(
            new CommandResult("feedback", false, false, null, null)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(
            new CommandResult("feedback", true, false, null, null)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(
            new CommandResult("feedback", false, true, null, null)));

        // different switchTab value -> returns false
        assertFalse(commandResult.equals(
            new CommandResult("feedback", false, false, Tab.BUDGET, null)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
            new CommandResult("feedback", true, false, null, null).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
            new CommandResult("feedback", false, true, null, null).hashCode());

        // different switchTabValue -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
            new CommandResult("feedback", false, false, Tab.BUDGET, null).hashCode());
    }
}
