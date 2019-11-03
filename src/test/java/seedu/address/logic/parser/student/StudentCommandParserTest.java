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

    @Test
    public void parseCommand_editInvalidCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentEditCommand.MESSAGE_USAGE), () ->
                        parser.parse("0 name/Ala"));
    }
}
