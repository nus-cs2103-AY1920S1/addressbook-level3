package dukecooks.logic.parser.diary;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.diary.ViewDiaryCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.testutil.TypicalIndexes;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ViewDiaryCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ViewDiaryCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ViewDiaryCommandParserTest {

    private ViewDiaryCommandParser parser = new ViewDiaryCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new ViewDiaryCommand(TypicalIndexes.INDEX_FIRST_PAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
