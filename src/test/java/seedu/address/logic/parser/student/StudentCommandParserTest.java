package seedu.address.logic.parser.student;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.student.StudentAddCommand;
import seedu.address.logic.commands.student.StudentDeleteCommand;
import seedu.address.logic.commands.student.StudentEditCommand;
import seedu.address.logic.commands.student.StudentListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.tag.Tag;

public class StudentCommandParserTest {

    private final StudentCommandParser parser = new StudentCommandParser();

    @Test
    public void parseCommand_editValidCommand_success() throws Exception {
        Command command = parser.parse("1 name/NameName");
        assertTrue(command instanceof StudentEditCommand);
    }

    @Test
    public void parseCommand_editInvalidCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentEditCommand.MESSAGE_USAGE), () ->
                        parser.parse("0  name/Ala"));
    }

    @Test
    public void parseCommand_listValidCommand_success() throws Exception {
        Command command = parser.parse(" list");
        assertTrue(command instanceof StudentListCommand);
    }

    @Test
    public void parseCommand_deleteValidCommand_success() throws Exception {
        Command command = parser.parse(" delete 1");
        assertTrue(command instanceof StudentDeleteCommand);
    }

    @Test
    public void parseCommand_deleteInvalidCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentDeleteCommand.MESSAGE_USAGE), () ->
                        parser.parse(" delete"));
    }

    @Test
    public void parseCommand_addValidCommandNoSpace_success() throws Exception {
        Command command = parser.parse(" name/testStudent");
        assertTrue(command instanceof StudentAddCommand);
    }

    @Test
    public void parseCommand_addValidCommandWithSpace_success() throws Exception {
        Command command = parser.parse(" name/test Student");
        assertTrue(command instanceof StudentAddCommand);
    }


    @Test
    public void parseCommand_addValidCommandWithTags_success() throws Exception {
        Command command = parser.parse(" name/test Student tag/test");
        assertTrue(command instanceof StudentAddCommand);
    }

    @Test
    public void parseCommand_addInvalidCommandNoTag_success() throws Exception {
        assertThrows(ParseException.class, Tag.MESSAGE_CONSTRAINTS, () ->
                parser.parse(" name/teststudent tag/"));
    }

    @Test
    public void parseCommand_addInvalidCommandNoName_success() throws Exception {
        assertThrows(ParseException.class, Name.MESSAGE_CONSTRAINTS, () ->
                parser.parse(" name/"));
    }

    @Test
    public void parseCommand_addInvalid_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentAddCommand.MESSAGE_USAGE), () ->
                        parser.parse(" "));
    }
}
