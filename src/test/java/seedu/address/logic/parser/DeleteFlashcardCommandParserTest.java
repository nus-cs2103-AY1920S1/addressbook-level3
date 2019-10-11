package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteFlashcardCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteFlashcardCommandParserTest {

//    private DeleteFlashcardCommandParser parser = new DeleteFlashcardCommandParser();
//
//    @Test
//    public void parse_validArgs_returnsDeleteCommand() {
////        assertParseSuccess(parser, "1", new DeleteFlashcardCommand(INDEX_FIRST_PERSON));
//    }
//
//    @Test
//    public void parse_invalidArgs_throwsParseException() {
//        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
//    }
}
