package seedu.address.logic.parser.diary;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.diary.EnterDiaryCommand;

/**
 * Simple unit tests for {@link EnterDiaryParser}.
 */
class EnterDiaryParserTest {
    @Test
    void parse_inputNull_returnsEnterDiaryCommand() {
        assertParseSuccess(new EnterDiaryParser(), null, new EnterDiaryCommand());
    }

    @Test
    void parse_inputNotNull_returnsEnterDiaryCommand() {
        assertParseSuccess(new EnterDiaryParser(), "abcdefg", new EnterDiaryCommand());
    }
}
