package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class UndoableCommandTest {

    @Test
    void test_commandStateEnum() {
        assertEquals(UndoableCommand.UndoableCommandState.UNDOABLE.toString(), "UNDOABLE");
    }
    @Test
    void setUndoable() {
    }        // todo: write test for this.

    @Test
    void setRedoable() {
    }

    @Test
    void getCommandState() {
    }
}
