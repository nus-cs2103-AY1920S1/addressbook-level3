package seedu.address.logic.parser.diary.entry;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.diary.entry.ShowTextEditorCommand;

/**
 * Simple unit tests of {@link ShowTextEditorParser}.
 */
class ShowTextEditorParserTest {
    @Test
    void parse_inputNull_returnsShowTextEditorCommand() {
        assertParseSuccess(new ShowTextEditorParser(), null, new ShowTextEditorCommand());
    }

    @Test
    void parse_inputNotNull_returnsShowTextEditorCommand() {
        assertParseSuccess(new ShowTextEditorParser(), "abcdefghijk", new ShowTextEditorCommand());
    }
}
