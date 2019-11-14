package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandRecordTest {
    private CommandRecord cr;

    @BeforeEach
    void setUp() {
        this.cr = new CommandRecord(1,
                      "add participant n/Clarke Kent p/+6593211234 e/superman@dailyplanet.com",
                                    CommandRecord.CommandType.UNDO);
    }

    @Test
    void getUndoEndPoint() {
        CommandRecord undoEndPoint = CommandRecord.getUndoEndPoint();
        assertTrue(undoEndPoint.getCommandType().equals(CommandRecord.CommandType.END));
        assertTrue(undoEndPoint.getIndex() == null);
        assertTrue(undoEndPoint.getCommandString().equals("UNDO DELIMITER: Cannot Undo Beyond This Point"));
    }

    @Test
    void getRedoEndPoint() {
        CommandRecord redoEndPoint = CommandRecord.getRedoEndPoint();
        assertTrue(redoEndPoint.getCommandType().equals(CommandRecord.CommandType.END));
        assertTrue(redoEndPoint.getIndex() == null);
        assertTrue(redoEndPoint.getCommandString().equals("REDO DELIMITER: Cannot Redo Beyond This Point"));
    }

    @Test
    void getCurrentStatePoint() {
        CommandRecord currEndPoint = CommandRecord.getCurrentStatePoint();
        assertTrue(currEndPoint.getCommandType().equals(CommandRecord.CommandType.CURR));
        assertTrue(currEndPoint.getIndex() == null);
        assertTrue(currEndPoint.getCommandString().equals("CURRENT STATE: You are here!"));
    }

    @Test
    void getCommandString() {
        String commandString = "add participant n/Clarke Kent p/+6593211234 e/superman@dailyplanet.com";
        assertTrue(this.cr.getCommandString().equals(commandString));
    }

    @Test
    void getCommandType() {
        assertTrue(this.cr.getCommandType().equals(CommandRecord.CommandType.UNDO));
    }

    @Test
    void getIndex() {
        assertTrue(this.cr.getIndex() == 1);
    }

    @Test
    void testEquals() {
        CommandRecord other = new CommandRecord(1,
                                 "add participant n/Clarke Kent p/+6593211234 e/superman@dailyplanet.com",
                                               CommandRecord.CommandType.UNDO);
        assertTrue(this.cr.equals(other));
    }
}
