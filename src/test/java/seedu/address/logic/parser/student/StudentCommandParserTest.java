package seedu.address.logic.parser.student;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.student.StudentEditCommand;
import seedu.address.logic.commands.student.StudentListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

public class StudentCommandParserTest {

    private final StudentCommandParser parser = new StudentCommandParser();

    @Test
    public void parseCommand_editValidCommand_success() throws Exception {
        Command command = parser.parse("1 name/NameName");
        assertTrue(command instanceof StudentEditCommand);
    }

    /**
     * @Test public void parseCommand_listValidCommand_success() throws Exception {
     * Command command = parser.parse("list");
     * assertTrue(command instanceof StudentListCommand);
     * }
     */

    @Test
    public void parseCommand_editInvalidCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentEditCommand.MESSAGE_USAGE), () ->
                        parser.parse("0 name/Ala"));
    }


    /**
     @Test public void parseCommand_deleteValidCommand_success() throws Exception {
     Command command = parser.parse("delete 1");
     assertTrue(command instanceof StudentDeleteCommand);
     }

     @Test public void parseCommand_deleteInvalidCommand_success() throws Exception {
     assertThrows(ParseException.class,
     String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentDeleteCommand.MESSAGE_USAGE), () ->
     parser.parse("delete 0"));
     }
     */


/**
 @Test public void parseCommand_addMissingFieldCommandPrioritized_throwsException() {
 assertThrows(ParseException.class,
 String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE), () ->
 parser.parse(MISSING_FIELD_COMMAND));
 }

 @Test public void parseCommand_addEmptyCommandPrioritized_throwsException() {
 assertThrows(ParseException.class,
 String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE), () ->
 parser.parse(EMPTY_FIELD_COMMAND));
 }

 @Test public void parseCommand_validEdit_success() throws Exception {
 Command command = parser.parse(EDIT_VALID);
 assertTrue(command instanceof NoteEditCommand);
 }

 @Test public void parseCommand_editEmptyCommandPrioritized_throwsException() {
 assertThrows(ParseException.class,
 String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteEditCommand.MESSAGE_USAGE), () ->
 parser.parse(EDIT_EMPTY_FIELD_COMMAND));
 }

 @Test public void parseCommand_validList_success() throws Exception {
 Command command = parser.parse(VALID_LIST);
 assertTrue(command instanceof NoteListCommand);
 }

 @Test public void parseCommand_validSort_success() throws Exception {
 Command command = parser.parse(VALID_SORT);
 assertTrue(command instanceof NoteSortCommand);
 }

 @Test public void parseCommand_validDelete_success() throws Exception {
 Command command = parser.parse(VALID_DELETE);
 assertTrue(command instanceof NoteDeleteCommand);
 }

 @Test public void parseCommand_invalidList_throwsException() {
 assertThrows(ParseException.class,
 String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteListCommand.MESSAGE_USAGE), () ->
 parser.parse(INVALID_LIST));
 }

 @Test public void parseCommand_invalidSort_throwsException() {
 assertThrows(ParseException.class,
 String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteSortCommand.MESSAGE_USAGE), () ->
 parser.parse(INVALID_SORT));
 }

 @Test public void parseCommand_invalidDelete_throwsException() {
 assertThrows(ParseException.class,
 String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteDeleteCommand.MESSAGE_USAGE), () ->
 parser.parse(INVALID_DELETE));
 }
 */
}
