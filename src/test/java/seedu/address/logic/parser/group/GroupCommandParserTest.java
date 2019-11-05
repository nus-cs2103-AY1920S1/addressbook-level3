package seedu.address.logic.parser.group;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_INPUT_FIELDS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.group.GroupAddStudentCommand;
import seedu.address.logic.commands.group.GroupCreateManuallyCommand;
import seedu.address.logic.commands.group.GroupExportCommand;
import seedu.address.logic.commands.group.GroupGetStudentsCommand;
import seedu.address.logic.commands.group.GroupRemoveStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Test for GroupCommandParser.
 */
public class GroupCommandParserTest {
    private final GroupCommandParser parser = new GroupCommandParser();

    /**
     * Tests for successfully creating a GroupCreateManuallyCommand.
     */
    @Test
    public void parseCommand_createGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" manual/ groupID/G01 studentNumber/1 2 3");
        assertTrue(command instanceof GroupCreateManuallyCommand);
    }

    /**
     * Tests for unsuccessfully creating a GroupCreateManuallyCommand due to incomplete command.
     */
    @Test
    public void parseCommand_createGroupInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCreateManuallyCommand.MESSAGE_USAGE), () ->
                        parser.parse(" manual"));
    }

    /**
     * Tests for unsuccessfully creating a GroupCreateManuallyCommand due to invalid student Number.
     */
    @Test
    public void parseCommand_createGroupNoStudentNumberInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                        parser.parse(" manual/ groupID/G01 studentNumber/"));
    }

    /**
     * Tests for successfully creating a GroupAddStudentCommand.
     */
    @Test
    public void parseCommand_addStudentToGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" add groupID/G03 studentNumber/1 groupIndexNumber/2");
        assertTrue(command instanceof GroupAddStudentCommand);
    }

    /**
     * Tests for unsuccessfully creating a GroupAddStudentCommand due to missing student number.
     */
    @Test
    public void parseCommand_addStudentToGroupMissingStudentNumberInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" add groupID/G03 studentNumber/ groupIndexNumber/2"));
    }

    /**
     * Tests for unsuccessfully creating a GroupAddStudentCommand due to missing group index number.
     */
    @Test
    public void parseCommand_addStudentToGroupMissingGroupIndexNumberInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" add groupID/G03 studentNumber/1 groupIndexNumber/"));
    }

    /**
     * Tests for unsuccessfully creating a GroupAddStudentCommand due to missing student number and group index number.
     */
    @Test
    public void parseCommand_addStudentToGroupMissingTwoFieldsInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" add groupID/G03 studentNumber/ groupIndexNumber/"));
    }

    /**
     * Tests for successfully creating a GroupRemoveStudentCommand.
     */
    @Test
    public void parseCommand_removeStudentFromGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" delete groupID/G03 groupIndexNumber/1");
        assertTrue(command instanceof GroupRemoveStudentCommand);
    }

    /**
     * Tests for unsuccessfully creating a GroupRemoveStudentCommand due to missing group index number.
     */
    @Test
    public void parseCommand_removeStudentFromGroupMissingFieldsInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class, MESSAGE_MISSING_INPUT_FIELDS, () ->
                parser.parse(" delete groupID/G03 groupIndexNumber/"));
    }

    /**
     * Tests for unsuccessfully creating a GroupRemoveStudentCommand due to incomplete command.
     */
    @Test
    public void parseCommand_removeStudentInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupRemoveStudentCommand.MESSAGE_USAGE), () ->
                        parser.parse(" delete"));
    }

    /**
     * Tests for successfully creating a GroupGetStudentsCommand.
     */
    @Test
    public void parseCommand_getStudentsFromGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" groupID/G01");
        assertTrue(command instanceof GroupGetStudentsCommand);
    }

    /**
     * Tests for successfully creating a GroupExportCommand.
     */
    @Test
    public void parseCommand_exportGroupValidCommand_success() throws Exception {
        Command command = parser.parse(" export groupID/G01");
        assertTrue(command instanceof GroupExportCommand);
    }

}
