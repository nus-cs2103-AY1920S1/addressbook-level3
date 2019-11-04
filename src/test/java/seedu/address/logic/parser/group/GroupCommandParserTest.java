package seedu.address.logic.parser.group;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_INPUT_FIELDS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.group.*;
import seedu.address.logic.parser.exceptions.ParseException;

public class GroupCommandParserTest {
    private final GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parseCommand_createGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" manual/ groupID/G01 studentNumber/1 2 3");
        assertTrue(command instanceof GroupCreateManuallyCommand);
    }


    @Test
    public void parseCommand_createGroupInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCreateManuallyCommand.MESSAGE_USAGE), () ->
                        parser.parse(" manual"));
    }

    @Test
    public void parseCommand_createGroupNoStudentNumberInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                        parser.parse(" manual/ groupID/G01 studentNumber/"));
    }

    @Test
    public void parseCommand_addStudentToGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" add groupID/G03 studentNumber/1 groupIndexNumber/2");
        assertTrue(command instanceof GroupAddStudentCommand);
    }

    @Test
    public void parseCommand_addStudentToGroupMissingStudentNumberInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" add groupID/G03 studentNumber/ groupIndexNumber/2"));
    }

    @Test
    public void parseCommand_addStudentToGroupMissingGroupIndexNumberInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" add groupID/G03 studentNumber/1 groupIndexNumber/"));
    }

    @Test
    public void parseCommand_addStudentToGroupMissingTwoFieldsInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" add groupID/G03 studentNumber/ groupIndexNumber/"));
    }

    @Test
    public void parseCommand_removeStudentFromGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" delete groupID/G03 groupIndexNumber/1");
        assertTrue(command instanceof GroupRemoveStudentCommand);
    }

    @Test
    public void parseCommand_removeStudentFromGroupMissingFieldsInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" delete groupID/G03 groupIndexNumber/"));
    }

    @Test
    public void parseCommand_removeStudentInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupRemoveStudentCommand.MESSAGE_USAGE), () ->
                        parser.parse(" delete"));
    }

    @Test
    public void parseCommand_getStudentsFromGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" groupID/G01");
        assertTrue(command instanceof GroupGetStudentsCommand);
    }

    @Test
    public void parseCommand_exportGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" export groupID/G01");
        assertTrue(command instanceof GroupExportCommand);
    }

}
