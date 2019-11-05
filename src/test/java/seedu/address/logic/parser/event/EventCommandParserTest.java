package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCREENSHOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.event.EventAddCommand;
import seedu.address.logic.commands.event.EventCommand;
import seedu.address.logic.commands.event.EventEditCommand;
import seedu.address.logic.commands.event.EventExportCommand;
import seedu.address.logic.commands.event.EventViewCommand;
import seedu.address.logic.parser.Prefix;
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
    public static final String INVALID_ADD_MISSING_EVENT_NAME =  " " + PREFIX_EVENT_NAME
            + " " + PREFIX_START_DATETIME + VALID_START_DATE_TIME_STRING + " "
            + PREFIX_END_DATETIME + VALID_END_DATE_TIME_STRING + " " + PREFIX_COLOR + VALID_COLOR_STRING + " "
            + PREFIX_RECUR + VALID_RECUR_TYPE_STRING;
    public static final String VALID_FULL_EDIT_COMMAND = "1" + " " + PREFIX_EVENT_NAME
            + VALID_EVENT_NAME + " " + PREFIX_START_DATETIME + VALID_START_DATE_TIME_STRING + " "
            + PREFIX_END_DATETIME + VALID_END_DATE_TIME_STRING + " " + PREFIX_COLOR + VALID_COLOR_STRING + " "
            + PREFIX_RECUR + VALID_RECUR_TYPE_STRING;
    public static final String EDIT_EMPTY_FIELD_COMMAND = "1" + " " + PREFIX_EVENT_NAME
            + " " + PREFIX_START_DATETIME + " "
            + PREFIX_END_DATETIME +  " " + PREFIX_COLOR +  " "
            + PREFIX_RECUR;
    public static final String VALID_VIEW_NO_PARAM_COMMAND = " " + PREFIX_VIEW;
    public static final String VALID_VIEW_MODE_FULL = VALID_VIEW_NO_PARAM_COMMAND + " " + VALID_VIEW_MODE + " "
            + VALID_TARGET_DATE;
    public static final String VALID_EXPORT_COMMAND = " " + PREFIX_EXPORT;



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
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE),
                () -> parser.parse(INVALID_ADD_MISSING_EVENT_NAME));
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
     * Test parsing a invalid edit command with no parameters passed. Should throw exception.
     */
    @Test
    public void parseCommand_editEmptyCommandPrioritized_throwsException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE), () ->
                        parser.parse(EDIT_EMPTY_FIELD_COMMAND));
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
     * Test valid screenshot event command
     * @throws Exception
     */
    @Test
    public void parseCommand_validScreenshotCommand_success() throws Exception {
        Command command = parser.parse(VALID_EXPORT_COMMAND);
        assertTrue(command instanceof EventExportCommand);
    }

//
//    @Test
//    public void parseCommand_validSort_success() throws Exception {
//        Command command = parser.parse(VALID_SORT);
//        assertTrue(command instanceof NoteSortCommand);
//    }
//
//    @Test
//    public void parseCommand_validDelete_success() throws Exception {
//        Command command = parser.parse(VALID_DELETE);
//        assertTrue(command instanceof NoteDeleteCommand);
//    }
//
//    @Test
//    public void parseCommand_invalidList_throwsException() {
//        assertThrows(ParseException.class,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteListCommand.MESSAGE_USAGE), () ->
//                        parser.parse(INVALID_LIST));
//    }
//
//    @Test
//    public void parseCommand_invalidSort_throwsException() {
//        assertThrows(ParseException.class,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteSortCommand.MESSAGE_USAGE), () ->
//                        parser.parse(INVALID_SORT));
//    }
//
//    @Test
//    public void parseCommand_invalidDelete_throwsException() {
//        assertThrows(ParseException.class,
//                Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX + "\n" + NoteDeleteCommand.MESSAGE_USAGE, () ->
//                        parser.parse(INVALID_DELETE));
//    }
}
