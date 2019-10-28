package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    public CommandHistory commandHistory = CommandHistory.getCommandHistory();

    @Test
    public void constructor_withCommandHistory_copiesCommandHistory() {
        final CommandHistory commandHistoryWithAddCustomer = commandHistory;
        commandHistoryWithAddCustomer.add("add-c");

        assertEquals(commandHistoryWithAddCustomer, new CommandHistory(commandHistoryWithAddCustomer));
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Boy";

        commandHistory.clear();
        commandHistory.add(validCommand);
        commandHistory.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), commandHistory.getCommandHistoryList());
    }

}
