package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECORD;
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
import seedu.address.logic.commands.DeleteRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.Event;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    public static final AthletickDate VALID_DATE = new AthletickDate(25, 12, 2019, 2, "December");

    private static final String FLAG_INVALID = "-person";
    private static final String SPACE = " ";

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgsPerson_returnsDeletePersonCommand() {
        assertParseSuccess(parser, FLAG_PERSON + SPACE + VALID_INDEX, new DeletePersonCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsEvent_returnsDeleteEventCommand() {
        assertParseSuccess(parser, FLAG_EVENT + SPACE + VALID_EVENT, new DeleteEventCommand(new Event(VALID_EVENT)));
    }

    @Test
    public void parse_validArgsRecord_returnsDeleteRecordCommand() {
        String userInput = FLAG_RECORD + SPACE + VALID_INDEX + EVENT_DESC + DATE_DESC;
        assertParseSuccess(parser, userInput, new DeleteRecordCommand(INDEX_FIRST_PERSON, VALID_EVENT, VALID_DATE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no space in between flag and argument
        assertParseFailure(
            parser, FLAG_PERSON + VALID_INDEX,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(
            parser,
            FLAG_EVENT + VALID_EVENT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        // invalid flag
        assertParseFailure(
            parser,
            FLAG_INVALID + " " + VALID_INDEX,
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
        // record flag
        assertEquals(FLAG_RECORD.getFlag(), parser.getFlag("-r"));
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
        String userInput = FLAG_PERSON + SPACE + targetIndex.getOneBased();
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
        Event targetEvent = new Event(VALID_EVENT);
        String userInput = FLAG_EVENT + SPACE + VALID_EVENT;
        DeleteEventCommand expectedCommand = new DeleteEventCommand(targetEvent);
        assertEquals(expectedCommand, parser.parseEvent(userInput));
    }

    @Test
    public void parseRecord_validRecordArgs_returnsDeleteRecordCommand() throws ParseException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = FLAG_RECORD + SPACE + VALID_INDEX + EVENT_DESC + DATE_DESC;
        DeleteRecordCommand expectedCommand = new DeleteRecordCommand(targetIndex, VALID_EVENT, VALID_DATE);
        assertEquals(expectedCommand, parser.parseRecord(userInput));
    }

    @Test
    public void parseRecord_invalidArgs_throwsParseException() {
        // no argument
        assertThrows(ParseException.class, () -> parser.parseRecord(FLAG_RECORD.getFlag()));
        // missing index
        assertThrows(ParseException.class, () -> parser.parseRecord(FLAG_RECORD + EVENT_DESC + DATE_DESC));
        // missing event
        assertThrows(ParseException.class, () -> parser.parseRecord(FLAG_RECORD + SPACE + VALID_INDEX + DATE_DESC));
        // missing date
        assertThrows(ParseException.class, () -> parser.parseRecord(FLAG_RECORD + SPACE + VALID_INDEX + EVENT_DESC));
    }
}
