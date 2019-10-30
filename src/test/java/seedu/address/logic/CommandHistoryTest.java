package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory commandHistory = CommandHistory.getCommandHistory();

    @Test
    public void constructor_withCommandHistory_copiesCommandHistory() {
        CommandHistory commandHistoryWithAddCustomer = commandHistory;
        commandHistoryWithAddCustomer.add("add-c");

        assertEquals(commandHistoryWithAddCustomer, new CommandHistory(commandHistoryWithAddCustomer));
    }

    @Test
    public void add() {
        String validCommand = "clear";
        String invalidCommand = "adds Boy";

        commandHistory.clear();
        commandHistory.add(validCommand);
        commandHistory.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), commandHistory.getCommandHistoryList());
    }

    @Test
    public void equals() {
        commandHistory.clear();
        CommandHistory commandHistoryWithAdd = new CommandHistory(commandHistory);
        commandHistoryWithAdd.add("add");
        CommandHistory commandHistoryWithAddCopy = new CommandHistory(commandHistory);
        commandHistoryWithAddCopy.add("add");
        CommandHistory emptyCommandHistory = new CommandHistory(commandHistory);

        // same object -> returns true
        assertTrue(commandHistoryWithAdd.equals(commandHistoryWithAdd));

        // same values -> returns true
        assertTrue(commandHistoryWithAdd.equals(commandHistoryWithAddCopy));

        // null -> returns false
        assertFalse(commandHistoryWithAdd.equals(null));

        // different types -> returns false
        assertFalse(commandHistoryWithAdd.equals(5.0f));

        // different values -> returns false
        assertFalse(commandHistoryWithAdd.equals(emptyCommandHistory));
    }

    @Test
    public void hashcode() {
        commandHistory.clear();
        CommandHistory commandHistoryWithAdd = new CommandHistory(commandHistory);
        commandHistoryWithAdd.add("add");
        CommandHistory commandHistoryWithAddCopy = new CommandHistory(commandHistory);
        commandHistoryWithAddCopy.add("add");
        CommandHistory emptyCommandHistory = new CommandHistory(commandHistory);

        // same values -> returns same hashcode
        assertEquals(commandHistoryWithAdd.hashCode(), commandHistoryWithAddCopy.hashCode());

        // different values -> returns different hashcode
        assertNotEquals(commandHistoryWithAdd.hashCode(), emptyCommandHistory.hashCode());
    }

}
