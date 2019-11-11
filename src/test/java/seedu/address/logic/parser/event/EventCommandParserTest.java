package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INDEX_EMPTY_EVENT_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GET_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCREENSHOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.event.EventAddCommand;
import seedu.address.logic.commands.event.EventDeleteCommand;
import seedu.address.logic.commands.event.EventEditCommand;
import seedu.address.logic.commands.event.EventExportCommand;
import seedu.address.logic.commands.event.EventIndexCommand;
import seedu.address.logic.commands.event.EventScreenshotCommand;
import seedu.address.logic.commands.event.EventViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EventCommandParserTest {
    public static final String VALID_EVENT_NAME = "my event";
    public static final String VALID_COLOR_STRING = "1";
    public static final String VALID_START_DATE_TIME_STRING = "2019-01-01T03:00";
    public static final String VALID_END_DATE_TIME_STRING = "2019-01-01T04:00";
    public static final String VALID_RECUR_TYPE_STRING = "DAILY";
    public static final String VALID_VIEW_MODE = "WEEKLY";
    public static final String VALID_TARGET_DATE = "2019-01-01T05:00";

    public static final String VALID_COMMAND_DEFAULT = " " + PREFIX_EVENT_NAME
            + VALID_EVENT_NAME + " " + PREFIX_START_DATETIME + VALID_START_DATE_TIME_STRING + " "
            + PREFIX_END_DATETIME + VALID_END_DATE_TIME_STRING + " " + PREFIX_COLOR + VALID_COLOR_STRING + " "
            + PREFIX_RECUR + VALID_RECUR_TYPE_STRING;
    public static final String INVALID_ADD_MISSING_EVENT_NAME_PREFIX = " " + " "
            + PREFIX_START_DATETIME + VALID_START_DATE_TIME_STRING + " "
            + PREFIX_END_DATETIME + VALID_END_DATE_TIME_STRING + " " + PREFIX_COLOR + VALID_COLOR_STRING + " "
            + PREFIX_RECUR + VALID_RECUR_TYPE_STRING;
    public static final String INVALID_ADD_MISSING_EVENT_NAME = " " + PREFIX_EVENT_NAME
            + " " + PREFIX_START_DATETIME + VALID_START_DATE_TIME_STRING + " "
            + PREFIX_END_DATETIME + VALID_END_DATE_TIME_STRING + " " + PREFIX_COLOR + VALID_COLOR_STRING + " "
            + PREFIX_RECUR + VALID_RECUR_TYPE_STRING;
    public static final String VALID_FULL_EDIT_COMMAND = "1" + " " + PREFIX_EVENT_NAME
            + VALID_EVENT_NAME + " " + PREFIX_START_DATETIME + VALID_START_DATE_TIME_STRING + " "
            + PREFIX_END_DATETIME + VALID_END_DATE_TIME_STRING + " " + PREFIX_COLOR + VALID_COLOR_STRING + " "
            + PREFIX_RECUR + VALID_RECUR_TYPE_STRING;
    public static final String VALID_VIEW_NO_PARAM_COMMAND = " " + PREFIX_VIEW;
    public static final String VALID_VIEW_MODE_FULL = VALID_VIEW_NO_PARAM_COMMAND + " " + VALID_VIEW_MODE + " "
            + VALID_TARGET_DATE;
    public static final String VALID_EXPORT_COMMAND = " " + PREFIX_EXPORT;
    public static final String VALID_SCREENSHOT_COMMAND = " " + PREFIX_SCREENSHOT;
    public static final String VALID_DELETE = " " + PREFIX_DELETE + "1";
    public static final String INVALID_DELETE = " " + PREFIX_DELETE + "one";
    public static final String INVALID_DELETE_ZERO_INDEX = " " + PREFIX_DELETE + "0";
    public static final String INVALID_DELETE_NEGATIVE_INDEX = " " + PREFIX_DELETE + "-1";
    public static final String INVALID_DELETE_NO_INDEX = " " + PREFIX_DELETE;
    public static final String VALID_INDEX_OF = " " + PREFIX_GET_INDEX + "my event";
    public static final String INVALID_INDEX_OF = " " + PREFIX_GET_INDEX;


    private final EventCommandParser parser = new EventCommandParser();

    /**
     * Test parsing a valid add event command
     * @throws Exception
     */
    @Test
    public void parseCommand_addValidCommand_success() throws Exception {
        Command command = parser.parse(VALID_COMMAND_DEFAULT);
        assertTrue(command instanceof EventAddCommand);
    }

    /**
     * Test parsing a add command with missing event name prefix
     */
    @Test
    public void parseCommand_addMissingEventNamePrefix_throwsException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE), () ->
                        parser.parse(INVALID_ADD_MISSING_EVENT_NAME_PREFIX));
    }

    /**
     * Test parsing a add command with missing event name
     */
    @Test
    public void parseCommand_addMissingEventName_throwsException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddCommand.MESSAGE_USAGE), () -> parser.parse(INVALID_ADD_MISSING_EVENT_NAME));
    }

    /**
     * Test parsing a valid edit event command
     * @throws Exception
     */
    @Test
    public void parseCommand_validEdit_success() throws Exception {
        Command command = parser.parse(VALID_FULL_EDIT_COMMAND);
        assertTrue(command instanceof EventEditCommand);
    }

    /**
     * Test valid event view command with no parameters
     * @throws Exception
     */
    @Test
    public void parseCommand_validViewNoParameter_success() throws Exception {
        Command command = parser.parse(VALID_VIEW_NO_PARAM_COMMAND);
        assertTrue(command instanceof EventViewCommand);
    }

    /**
     * Test valid event view command with view mode and view date parameter
     * @throws Exception
     */
    @Test
    public void parseCommand_validViewWithValidViewModeCommand_success() throws Exception {
        Command command = parser.parse(VALID_VIEW_MODE_FULL);
        assertTrue(command instanceof EventViewCommand);
    }


    /**
     * Test valid export event command
     * @throws Exception
     */
    @Test
    public void parseCommand_validExportCommand_success() throws Exception {
        Command command = parser.parse(VALID_EXPORT_COMMAND);
        assertTrue(command instanceof EventExportCommand);
    }

    /**
     * Test valid screenshot event command with bad index format passed
     * @throws Exception
     */
    @Test
    public void parseCommand_validScreenshotCommand_success() throws Exception {
        Command command = parser.parse(VALID_SCREENSHOT_COMMAND);
        assertTrue(command instanceof EventScreenshotCommand);
    }

    /**
     * Test a valid delete event command
     * @throws Exception
     */
    @Test
    public void parseCommand_validDelete_success() throws Exception {
        Command command = parser.parse(VALID_DELETE);
        assertTrue(command instanceof EventDeleteCommand);
    }

    /**
     * Test a invalid delete command
     */
    @Test
    public void parseCommand_invalidDelete_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE), () ->
                        parser.parse(INVALID_DELETE));
    }

    /**
     * Test invalid delete event command by passing zero as index
     */
    @Test
    public void parseCommand_invalidDeleteZeroIndex_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE), () ->
                        parser.parse(INVALID_DELETE_ZERO_INDEX));
    }

    /**
     * Test invalid delete event command by passing negative index value
     */
    @Test
    public void parseCommand_invalidDeleteNegativeIndex_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE), () ->
                        parser.parse(INVALID_DELETE_NEGATIVE_INDEX));
    }

    /**
     * Test invalid delete event command by not passing a index
     */
    @Test
    public void parseCommand_invalidDeleteNoIndex_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE), () ->
                        parser.parse(INVALID_DELETE_NO_INDEX));
    }

    /**
     * Test a valid event index command.
     * @throws Exception
     */
    @Test
    public void parseCommand_validIndexOf_success() throws Exception {
        Command command = parser.parse(VALID_INDEX_OF);
        assertTrue(command instanceof EventIndexCommand);
    }

    /**
     * Test a invalid event index command with empty argument
     * @throws Exception
     */
    @Test
    public void parseCommand_invalidIndexOf_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INDEX_EMPTY_EVENT_NAME, () ->
                        parser.parse(INVALID_INDEX_OF));
    }
}
