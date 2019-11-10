package seedu.address.logic.parser.diary.entry;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.diary.entry.DoneEditEntryTextCommand;

/**
 * Simple unit tests of {@link DoneEditEntryTextParser}.
 */
class DoneEditEntryTextParserTest {

    @Test
    void parse_inputNull_returnsDoneEditEntryCommand() {
        assertParseSuccess(new DoneEditEntryTextParser(), null, new DoneEditEntryTextCommand());
    }

    @Test
    void parse_inputNotNull_returnsDoneEditEntryCommand() {
        assertParseSuccess(new DoneEditEntryTextParser(), "abcdefg", new DoneEditEntryTextCommand());
    }
}
