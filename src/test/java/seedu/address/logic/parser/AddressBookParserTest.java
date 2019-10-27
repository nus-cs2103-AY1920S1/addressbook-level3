package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AlfredParser parser = new AlfredParser();

    @Disabled
    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }
}
