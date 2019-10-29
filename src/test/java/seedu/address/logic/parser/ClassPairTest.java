package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.switches.OpenCommand;

class ClassPairTest {

    private ClassPair classPair;

    @BeforeEach
    void setUp() {
        classPair = new ClassPair(OpenCommand.class, null);
    }

    @Test
    void getCommand() {
        assertTrue(classPair.getCommand() == OpenCommand.class);
    }

    @Test
    void getParser() {
        assertTrue(classPair.getParser() == null);
    }
}
