package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.DeleteCommand;

import java.util.ArrayList;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<Index> indexToDelete = new ArrayList<>();
        indexToDelete.add(INDEX_FIRST_ANSWERABLE);
        assertParseSuccess(parser, "1", new DeleteCommand(indexToDelete));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
