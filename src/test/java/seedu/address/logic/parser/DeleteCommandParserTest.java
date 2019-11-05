package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CliSyntax.FLAG_TRAINING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
    private static final String FLAG_INVALID = "-person";

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
        assertParseFailure(
            parser,
            FLAG_INVALID + " " + index,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void getFlag_invalidArgs_throwsParseException() {
        // null argument
        assertThrows(NullPointerException.class, () -> parser.getFlag(null));
        // short argument
        assertThrows(ParseException.class, () -> parser.getFlag("p"));
        assertThrows(ParseException.class, () -> parser.getFlag("-"));
        // invalid flag
        assertThrows(ParseException.class, () -> parser.getFlag(FLAG_INVALID));
    }

    @Test
    public void getFlag_validArgs_returnsTrimmedFlag() throws ParseException {
        // person flag
        assertEquals(FLAG_PERSON.getFlag(), parser.getFlag("-p"));
        // event flag
        assertEquals(FLAG_EVENT.getFlag(), parser.getFlag("-e"));
        // training flag
        assertEquals(FLAG_TRAINING.getFlag(), parser.getFlag("-t"));
    }

    @Test
    public void parsePerson_invalidArgs_throwsParseException() {
        // no argument
        assertThrows(ParseException.class, () -> parser.parsePerson(FLAG_PERSON.getFlag()));
        // non-numeric argument
        assertThrows(ParseException.class, () -> parser.parsePerson(FLAG_PERSON.getFlag() + " John"));
        // invalid index
        assertThrows(ParseException.class, () -> parser.parsePerson(FLAG_PERSON.getFlag() + " 0"));
        assertThrows(ParseException.class, () -> parser.parsePerson(FLAG_PERSON.getFlag() + " -1"));
    }

    @Test
    public void parsePerson_validPersonArgs_returnsDeletePersonCommand() throws ParseException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = Integer.toString(targetIndex.getOneBased());
        DeletePersonCommand expectedCommand = new DeletePersonCommand(targetIndex);
        assertEquals(expectedCommand, parser.parsePerson(userInput));
    }

    @Test
    public void parseEvent_invalidArgs_throwsParseException() {
        // no argument
        assertThrows(ParseException.class, () -> parser.parseEvent(FLAG_EVENT.getFlag()));
    }

    @Test
    public void parseEvent_validEventArgs_returnsDeleteEventCommand() throws ParseException {
        Event targetEvent = new Event(eventName);
        String userInput = eventName;
        DeleteEventCommand expectedCommand = new DeleteEventCommand(targetEvent);
        assertEquals(expectedCommand, parser.parseEvent(userInput));

    }
}
