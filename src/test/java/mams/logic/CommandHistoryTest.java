package mams.logic;

import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_1;
import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_2;
import static mams.testutil.TypicalCommandHistory.SUCCESS_COMMAND_1;
import static mams.testutil.TypicalCommandHistory.SUCCESS_COMMAND_2;
import static mams.testutil.TypicalCommandHistory.SUCCESS_COMMAND_OUTPUT_1;
import static mams.testutil.TypicalCommandHistory.SUCCESS_COMMAND_OUTPUT_2;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_COMMAND_1;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_COMMAND_2;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_COMMAND_OUTPUT;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_IO_1;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_IO_2;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_1;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_2;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_3;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    private CommandHistory commandHistory;

    @BeforeEach
    public void initialize() {
        this.commandHistory = new CommandHistory();
    }

    @Test
    public void addTest1() {
        this.commandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);
        this.commandHistory.add(SUCCESS_COMMAND_2, SUCCESS_COMMAND_OUTPUT_2, true, TIME_STAMP_2);
        assertEquals(Arrays.asList(SUCCESSFUL_IO_1, SUCCESSFUL_IO_2), this.commandHistory.getInputOutputHistory());
    }

    @Test
    public void addTest2() {
        this.commandHistory.add(UNSUCCESSFUL_COMMAND_1, UNSUCCESSFUL_COMMAND_OUTPUT, false, TIME_STAMP_3);
        this.commandHistory.add(UNSUCCESSFUL_COMMAND_2, UNSUCCESSFUL_COMMAND_OUTPUT, false, TIME_STAMP_4);
        assertEquals(Arrays.asList(UNSUCCESSFUL_IO_1, UNSUCCESSFUL_IO_2), this.commandHistory.getInputOutputHistory());
    }

    @Test
    public void hashCodeTest1() {
        this.commandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);

        CommandHistory sameCommandHistory = new CommandHistory();
        sameCommandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);

        CommandHistory differentCommandHistory = new CommandHistory();
        differentCommandHistory.add(SUCCESS_COMMAND_2, SUCCESS_COMMAND_OUTPUT_2, true, TIME_STAMP_2);

        // same object -> same hashcode
        assertEquals(commandHistory.hashCode(), commandHistory.hashCode());

        // same internal values -> same hashcode
        assertEquals(commandHistory.hashCode(), sameCommandHistory.hashCode());

        // different internal values -> different hashcode
        assertNotEquals(commandHistory.hashCode(), differentCommandHistory.hashCode());

        // different object types -> different hashcode
        assertNotEquals(commandHistory.hashCode(), Objects.hash(0));
    }

    @Test
    public void hashCodeTest2() {
        this.commandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);

        CommandHistory appendedCommandHistory = new CommandHistory();
        appendedCommandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);
        appendedCommandHistory.add(SUCCESS_COMMAND_2, SUCCESS_COMMAND_OUTPUT_2, true, TIME_STAMP_2);

        CommandHistory anotherAppendedCommandHistory = new CommandHistory();
        anotherAppendedCommandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);
        anotherAppendedCommandHistory.add(UNSUCCESSFUL_COMMAND_1, UNSUCCESSFUL_COMMAND_OUTPUT, false, TIME_STAMP_3);

        // internal value different -> different hashcode
        assertNotEquals(commandHistory.hashCode(), appendedCommandHistory.hashCode());
        assertNotEquals(commandHistory.hashCode(), anotherAppendedCommandHistory.hashCode());
    }

    @Test
    public void equals() {
        this.commandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);

        CommandHistory sameCommandHistory = new CommandHistory();
        sameCommandHistory.add(SUCCESS_COMMAND_1, SUCCESS_COMMAND_OUTPUT_1, true, TIME_STAMP_1);

        CommandHistory differentCommandHistory = new CommandHistory();
        differentCommandHistory.add(UNSUCCESSFUL_COMMAND_1, UNSUCCESSFUL_COMMAND_OUTPUT, false, TIME_STAMP_3);

        // not same type, equals should return false
        assertFalse(commandHistory.equals(5));
        assertFalse(commandHistory.equals(new ArrayList<String>()));

        // compare valid history to null, should return false
        assertFalse(commandHistory.equals(null));

        // same object, returns true
        assertTrue(commandHistory.equals(commandHistory));

        // same internal values but different objects, returns true
        assertTrue(commandHistory.equals(sameCommandHistory));

        // different internal values, returns false
        assertFalse(commandHistory.equals(differentCommandHistory));
    }
}
