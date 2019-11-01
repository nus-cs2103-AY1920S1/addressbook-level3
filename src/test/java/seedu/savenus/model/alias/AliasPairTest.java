package seedu.savenus.model.alias;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.SortCommand;

public class AliasPairTest {

    private AliasPair aliasPair;

    @BeforeEach
    public void setUp() {
        this.aliasPair = new AliasPair(SortCommand.COMMAND_WORD, "");
    }

    @Test
    public void emptyCommandWordTests() {
        assertTrue(aliasPair.isValidCommandWord(SortCommand.COMMAND_WORD));
        assertFalse(aliasPair.isValidCommandWord(""));
    }

    @Test
    public void equalsTest() {
        assertTrue(aliasPair.equals(aliasPair));
        assertTrue(aliasPair.equals(new AliasPair(SortCommand.COMMAND_WORD, "")));

        assertFalse(aliasPair.equals(new Object()));
    }
}
