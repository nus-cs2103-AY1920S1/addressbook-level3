// @@author sreesubbash
package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.switches.SwitchToOpenCommand;

class ClassPairTest {

    private ClassPair classPair;

    @BeforeEach
    void setUp() {
        classPair = new ClassPair(SwitchToOpenCommand.class, null);
    }

    @Test
    void getCommand() {
        assertTrue(classPair.getCommand() == SwitchToOpenCommand.class);
    }

    @Test
    void getParser() {
        assertTrue(classPair.getParser() == null);
    }
}
