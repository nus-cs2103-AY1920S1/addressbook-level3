package unrealunity.visit.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrealunity.visit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_VISIT;
import static unrealunity.visit.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.AddCommand;
import unrealunity.visit.logic.commands.AddVisitCommand;
import unrealunity.visit.logic.commands.ClearCommand;
import unrealunity.visit.logic.commands.DeleteCommand;
import unrealunity.visit.logic.commands.EditCommand;
import unrealunity.visit.logic.commands.EditCommand.EditPersonDescriptor;
import unrealunity.visit.logic.commands.ExitCommand;
import unrealunity.visit.logic.commands.FindCommand;
import unrealunity.visit.logic.commands.HelpCommand;
import unrealunity.visit.logic.commands.ListCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;
import unrealunity.visit.model.person.NameContainsKeywordsPredicate;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.testutil.EditPersonDescriptorBuilder;
import unrealunity.visit.testutil.PersonBuilder;
import unrealunity.visit.testutil.PersonUtil;
import unrealunity.visit.testutil.TypicalIndexes;

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
        assertThrows(ParseException.class, () -> {
            parser.parseCommand(ClearCommand.COMMAND_WORD + " 3");
        });
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased()
            + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertThrows(ParseException.class, () -> {
            parser.parseCommand(ExitCommand.COMMAND_WORD + " 3");
        });
    }

    @Test
    public void parseCommand_find() throws Exception {
        String keywords = "foo bar baz";
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertThrows(ParseException.class, () -> {
            parser.parseCommand(HelpCommand.COMMAND_WORD + " 3");
        });
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertThrows(ParseException.class, () -> {
            parser.parseCommand(ListCommand.COMMAND_WORD + " 3");
        });
    }

    @Test
    public void parseCommand_addVisit() throws Exception {
        final String date = "12/12/2012";
        AddVisitCommand command = (AddVisitCommand) parser.parseCommand(AddVisitCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_VISIT + date);
        assertEquals(new AddVisitCommand(TypicalIndexes.INDEX_FIRST_PERSON, date), command);
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
