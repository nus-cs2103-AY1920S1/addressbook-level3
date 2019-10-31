package seedu.address.logic.parser.note;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.note.NoteAddCommand;
import seedu.address.logic.commands.note.NoteDeleteCommand;
import seedu.address.logic.commands.note.NoteEditCommand;
import seedu.address.logic.commands.note.NoteListCommand;
import seedu.address.logic.commands.note.NoteSortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class NoteCommandParserTest {

    public static final String VALID_NOTE = "Some note";
    public static final String VALID_DESC = "description";
    public static final String VALID_PRIORITY = "high";
    public static final String NOTE_PREFIX = " note/";
    public static final String DESC_PREFIX = " desc/";
    public static final String PRIORITY_PREFIX = " priority/";

    public static final String VALID_LIST = " list";
    public static final String VALID_SORT = " sort";
    public static final String VALID_DELETE = " delete" + 1;
    public static final String INVALID_LIST = "  list 1";
    public static final String INVALID_SORT = "  sort 1";
    public static final String INVALID_DELETE = " delete";
    public static final String VALID_COMMAND_DEFAULT = NOTE_PREFIX + VALID_NOTE + DESC_PREFIX + VALID_DESC;
    public static final String VALID_COMMAND_PRIORITIZED = VALID_COMMAND_DEFAULT + PRIORITY_PREFIX + VALID_PRIORITY;
    public static final String INVALID_SYNTAX_COMMAND = " not/" + VALID_NOTE + DESC_PREFIX + VALID_DESC;
    public static final String MISSING_FIELD_COMMAND = NOTE_PREFIX + VALID_NOTE;
    public static final String EMPTY_FIELD_COMMAND = NOTE_PREFIX + DESC_PREFIX;
    public static final String EDIT_VALID = " 1" + NOTE_PREFIX + VALID_NOTE + DESC_PREFIX + VALID_DESC;
    public static final String EDIT_EMPTY_FIELD_COMMAND = " 1" + EMPTY_FIELD_COMMAND;

    private final NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parseCommand_addValidCommand_success() throws Exception {
        Command command = parser.parse(VALID_COMMAND_DEFAULT);
        assertTrue(command instanceof NoteAddCommand);
    }

    @Test
    public void parseCommand_addValidCommandPrioritized_success() throws Exception {
        Command command = parser.parse(VALID_COMMAND_PRIORITIZED);
        assertTrue(command instanceof NoteAddCommand);
    }

    @Test
    public void parseCommand_addInvalidCommandPrioritized_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteEditCommand.MESSAGE_USAGE), () ->
                parser.parse(INVALID_SYNTAX_COMMAND));
    }

    @Test
    public void parseCommand_addMissingFieldCommandPrioritized_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE), () ->
                parser.parse(MISSING_FIELD_COMMAND));
    }

    @Test
    public void parseCommand_addEmptyCommandPrioritized_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE), () ->
                parser.parse(EMPTY_FIELD_COMMAND));
    }

    @Test
    public void parseCommand_validEdit_success() throws Exception {
        Command command = parser.parse(EDIT_VALID);
        assertTrue(command instanceof NoteEditCommand);
    }

    @Test
    public void parseCommand_editEmptyCommandPrioritized_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteEditCommand.MESSAGE_USAGE), () ->
                parser.parse(EDIT_EMPTY_FIELD_COMMAND));
    }

    @Test
    public void parseCommand_validList_success() throws Exception {
        Command command = parser.parse(VALID_LIST);
        assertTrue(command instanceof NoteListCommand);
    }

    @Test
    public void parseCommand_validSort_success() throws Exception {
        Command command = parser.parse(VALID_SORT);
        assertTrue(command instanceof NoteSortCommand);
    }

    @Test
    public void parseCommand_validDelete_success() throws Exception {
        Command command = parser.parse(VALID_DELETE);
        assertTrue(command instanceof NoteDeleteCommand);
    }

    @Test
    public void parseCommand_invalidList_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteListCommand.MESSAGE_USAGE), () ->
                parser.parse(INVALID_LIST));
    }

    @Test
    public void parseCommand_invalidSort_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteSortCommand.MESSAGE_USAGE), () ->
                parser.parse(INVALID_SORT));
    }

    @Test
    public void parseCommand_invalidDelete_throwsException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteDeleteCommand.MESSAGE_USAGE), () ->
                parser.parse(INVALID_DELETE));
    }
}
