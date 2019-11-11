package seedu.elisa.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.elisa.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.elisa.logic.commands.DoneCommand;
import seedu.elisa.logic.commands.PriorityCommand;
import seedu.elisa.logic.parser.exceptions.FocusModeException;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.ElisaCommandHistoryManager;

public class FocusElisaParserTest {
    private FocusElisaParser testParser = new FocusElisaParser(new ElisaCommandHistoryManager());

    @Test
    // Test for command with a single command word
    public void parse_approvedInputPriority_returnsCommand() {
        try {
            assertTrue(testParser.parseCommand("priority") instanceof PriorityCommand);
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    // Test for commands with an index
    public void parse_approvedInputDone_returnsCommand() {
        try {
            assertEquals(testParser.parseCommand("done 1"), new DoneCommand(ParserUtil.parseIndex("1")));
        } catch (ParseException e) {
            fail(e);
        }
    }

    @Test
    // Test for commands that are not allowed
    public void parse_bannedCommandGame_throwsFocusModeException() {
        assertThrows(FocusModeException.class, () -> testParser.parseCommand("game"));
        assertThrows(FocusModeException.class, () -> testParser.parseCommand("event test -d 1.min.later"));
    }

    @Test
    public void parse_unrecognizedCommandWord_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand("abcde"));
    }
}
