package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class CommandResultTest {

    private CommandResult commandResult;
    private String feedback = "feedback";

    @BeforeEach
    void init() {
        commandResult = new CommandResult(feedback);
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));

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
                new CommandResultBuilder("feedback").setShowHelp().build()));

        // different exit value -> returns false
        assertFalse(commandResult.equals(
                new CommandResultBuilder("feedback").setExit().build()));
    }

    @Test
    public void hashcode() {
        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());
    }


    @Test
    void getFeedbackToUser() {
        assertEquals(feedback, commandResult.getFeedbackToUser());
    }

    @Test
    void isShowHelp() {
        assertFalse(commandResult.isShowHelp());
    }

    @Test
    void isExit() {
        assertFalse(commandResult.isExit());
    }

    @Test
    void isExport() {
        assertFalse(commandResult.isExport());
    }

    @Test
    void isScroll() {
        assertFalse(commandResult.isScroll());
    }

    @Test
    void isPopUp() {
        assertFalse(commandResult.isPopUp());
    }

    @Test
    void isToggleNextWeek() {
        assertFalse(commandResult.isToggleNextWeek());
    }

    @Test
    void isHome() {
        assertFalse(commandResult.isHome());
    }

    @Test
    void isSwitchTabs() {
        assertFalse(commandResult.isSwitchTabs());
    }

    @Test
    void isSelect() {
        assertFalse(commandResult.isSelect());
    }

    @Test
    void isFilter() {
        assertFalse(commandResult.isFilter());
    }

    @Test
    void getPersonTimeslotData() {
        assertEquals(Optional.empty(), commandResult.getPersonTimeslotData());
    }

    @Test
    void getLocationData() {
        assertThrows(CommandException.class, () ->
                commandResult.getLocationData());
    }
}
