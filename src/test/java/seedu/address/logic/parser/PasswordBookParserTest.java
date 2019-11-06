package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PASSWORD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPasswordCommand;
import seedu.address.logic.commands.DeletePasswordCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.password.Password;
import seedu.address.testutil.PasswordBuilder;
import seedu.address.testutil.PasswordUtil;

public class PasswordBookParserTest {
    private final PasswordBookParser parser = new PasswordBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Password person = new PasswordBuilder().build();
        AddPasswordCommand command = (AddPasswordCommand) parser.parseCommand(PasswordUtil.getAddCommand(person));
        assertEquals(new AddPasswordCommand(person), command);
    }


    @Test
    public void parseCommand_delete() throws Exception {
        DeletePasswordCommand command = (DeletePasswordCommand) parser.parseCommand(
                DeletePasswordCommand.COMMAND_WORD + " " + INDEX_FIRST_PASSWORD.getOneBased());
        assertEquals(new DeletePasswordCommand(INDEX_FIRST_PASSWORD), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListPasswordCommand.COMMAND_WORD) instanceof ListPasswordCommand);
        assertTrue(parser.parseCommand(ListPasswordCommand.COMMAND_WORD + " 3") instanceof ListPasswordCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
