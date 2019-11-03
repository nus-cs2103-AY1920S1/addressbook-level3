package organice.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import organice.logic.commands.ExitCommand;
import organice.logic.commands.FindCommand;
import organice.logic.commands.HelpCommand;
import organice.logic.commands.ListCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Person;
import organice.model.person.PersonContainsPrefixesPredicate;
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
    public void parseCommand_find() throws Exception {
        String searchParams = FindCommand.COMMAND_WORD + " n/Alice ic/S1111111A t/doctor";
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + searchParams);
        //TODO: Replace ArgumentTokenizer with stub
        assertEquals(new FindCommand(new PersonContainsPrefixesPredicate(ArgumentTokenizer
                .tokenize(FindCommand.COMMAND_WORD
                        + " n/Alice ic/S1111111A t/doctor", PREFIX_NAME, PREFIX_NRIC, PREFIX_TYPE))), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
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
