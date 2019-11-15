package dukecooks.logic.parser.diary;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.diary.DeletePageCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.diary.TypicalDiaries;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePageCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePageCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePageCommandParserTest {

    private DeletePageCommandParser parser = new DeletePageCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1 n/ Meat Lovers",
                new DeletePageCommand(TypicalIndexes.INDEX_FIRST_PAGE, TypicalDiaries.ALL_MEAT.getDiaryName()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePageCommand.MESSAGE_USAGE));
    }
}
