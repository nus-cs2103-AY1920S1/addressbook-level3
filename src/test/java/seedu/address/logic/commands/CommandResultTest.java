package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ClaimBuilder;
import seedu.address.testutil.ContactBuilder;

public class CommandResultTest {

    private CommandResult commandResult = new CommandResult("feedback");
    private Claim claim = new ClaimBuilder().build();
    private Contact contact = new ContactBuilder().build();
    private CommandResult commandResult1 = new CommandResult("", false, false,
            true, claim);
    private CommandResult commandResult2 = new CommandResult("", false, false,
            false, true, contact);
    private CommandResult commandResult3 = new CommandResult("", false, false,
            false, false, false, true);

    //@@author{lawncegoh}
    @Test
    public void checkGiveClaim() {
        assertTrue(commandResult1.giveClaim().equals(claim));
    }

    //@@author{lawncegoh}
    @Test
    public void checkGiveContact() {
        assertTrue(commandResult2.giveContact().equals(contact));
    }

    //@@author{lawncegoh}
    @Test
    public void checkShowClaim() {
        assertTrue(commandResult1.hasClaim());
    }

    //@@author{lawncegoh}
    @Test
    public void checkShowContact() {
        assertTrue(commandResult2.hasContact());
    }

    @Test
    public void checkIsExit() {
        assertFalse(commandResult1.isExit());
    }

    @Test
    public void checkIsShortCut() {
        assertFalse(commandResult3.isCreateShortCut());
    }

    @Test
    public void checkShowHelp() {
        assertFalse(commandResult3.isShowHelp());
    }

    //@@author{lawncegoh}
    @Test
    public void checkToClear() {
        assertTrue(commandResult3.isToClear());
    }

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false, false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false, false).hashCode());
    }
}
