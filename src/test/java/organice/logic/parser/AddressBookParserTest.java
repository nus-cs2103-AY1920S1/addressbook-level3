package organice.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import organice.logic.commands.AddCommand;
import organice.logic.commands.ClearCommand;
import organice.logic.commands.EditCommand;
import organice.logic.commands.EditCommand.EditPersonDescriptor;
import organice.logic.commands.ExactFindCommand;
import organice.logic.commands.ExitCommand;
import organice.logic.commands.HelpCommand;
import organice.logic.commands.ListCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Person;
import organice.model.person.PersonContainsPrefixesPredicate;
import organice.model.person.Type;
import organice.testutil.EditPersonDescriptorBuilder;
import organice.testutil.PersonBuilder;
import organice.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + person.getNric().value + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(person.getNric(), descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exactFind() throws Exception {
        String searchParams = ExactFindCommand.COMMAND_WORD + " n/Alice ic/S9058768D t/doctor";
        ExactFindCommand command = (ExactFindCommand) parser.parseCommand(searchParams);
        //TODO: Replace ArgumentTokenizer with stub
        assertEquals(new ExactFindCommand(new PersonContainsPrefixesPredicate(ArgumentTokenizer
                .tokenize(" n/Alice ic/S9058768D t/doctor", PREFIX_NAME, PREFIX_NRIC, PREFIX_TYPE))), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        Type type = new Type(Type.PATIENT);
        ListCommand command = (ListCommand) parser.parseCommand(ListCommand.COMMAND_WORD + " t/patient");
        assertEquals(new ListCommand(type), command);

        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND, ListCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(ListCommand.COMMAND_WORD + " unknownParameter"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(
                ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
