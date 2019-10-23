package mams.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    public static final String VALID_COMMAND_1 = "list";
    public static final String VALID_COMMAND_2 = "list /s /m /a";
    public static final String INVALID_COMMAND_1 = "afoaref";
    public static final String INVALID_COMMAND_2 = "view";

    private CommandHistory commandHistory;

    @BeforeEach
    public void initialize() {
        this.commandHistory = new CommandHistory();
    }

    @Test
    public void addTest1() {
        this.commandHistory.add(VALID_COMMAND_1);
        this.commandHistory.add(VALID_COMMAND_2);
        assertEquals(Arrays.asList(VALID_COMMAND_1, VALID_COMMAND_2), this.commandHistory.getInputHistory());
    }

    @Test
    public void addTest2() {
        this.commandHistory.add(INVALID_COMMAND_1);
        this.commandHistory.add(INVALID_COMMAND_2);
        assertEquals(Arrays.asList(INVALID_COMMAND_1, INVALID_COMMAND_2), this.commandHistory.getInputHistory());
    }

    @Test
    public void hashCodeTest1() {
        this.commandHistory.add(VALID_COMMAND_1);

        CommandHistory sameCommandHistory = new CommandHistory();
        sameCommandHistory.add(VALID_COMMAND_1);

        CommandHistory differentCommandHistory = new CommandHistory();
        differentCommandHistory.add(VALID_COMMAND_2);

        assertEquals(commandHistory.hashCode(), sameCommandHistory.hashCode());

        assertNotEquals(commandHistory.hashCode(), differentCommandHistory.hashCode());
    }

    @Test
    public void hashCodeTest2() {
        this.commandHistory.add(VALID_COMMAND_1);

        CommandHistory appendedCommandHistory = new CommandHistory();
        appendedCommandHistory.add(VALID_COMMAND_1);
        appendedCommandHistory.add(VALID_COMMAND_2);

        CommandHistory anotherAppendedCommandHistory = new CommandHistory();
        anotherAppendedCommandHistory.add(VALID_COMMAND_1);
        anotherAppendedCommandHistory.add(INVALID_COMMAND_2);


        assertNotEquals(commandHistory.hashCode(), appendedCommandHistory.hashCode());

        assertNotEquals(commandHistory.hashCode(), anotherAppendedCommandHistory.hashCode());
    }

    @Test
    public void equals() {
        this.commandHistory.add(VALID_COMMAND_1);

        CommandHistory sameCommandHistory = new CommandHistory();
        sameCommandHistory.add(VALID_COMMAND_1);

        CommandHistory differentCommandHistory = new CommandHistory();
        differentCommandHistory.add(INVALID_COMMAND_1);

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
