package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.model.performance.Event;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();
    private static final String eventName = "freestyle 50m";
    private static final String index = "1";

    @Test
    public void parse_validArgsPerson_returnsDeletePersonCommand() {
        assertParseSuccess(parser, FLAG_PERSON + " " + index, new DeletePersonCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsEvent_returnsDeleteEventCommand() {
        assertParseSuccess(parser, FLAG_EVENT + " " + eventName, new DeleteEventCommand(new Event(eventName)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no space in between flag and argument
        assertParseFailure(
            parser, FLAG_PERSON + index, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(
            parser,
            FLAG_EVENT + eventName, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        // invalid flag
        String invalidFlag = "-flag";
        assertParseFailure(
            parser,
            invalidFlag + " " + index,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(
            parser,
            invalidFlag + " " + eventName,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }


}
