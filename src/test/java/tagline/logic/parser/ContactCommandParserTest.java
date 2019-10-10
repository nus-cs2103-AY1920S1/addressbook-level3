package tagline.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.ClearContactCommand;
import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.EditContactCommand.EditPersonDescriptor;
import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.commands.contact.ListContactCommand;
import tagline.logic.parser.contact.ContactCommandParser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.person.NameContainsKeywordsPredicate;
import tagline.model.person.Person;
import tagline.testutil.EditPersonDescriptorBuilder;
import tagline.testutil.PersonBuilder;
import tagline.testutil.PersonUtil;

public class ContactCommandParserTest {

    private final ContactCommandParser parser = new ContactCommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        CreateContactCommand command = (CreateContactCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new CreateContactCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD) instanceof ClearContactCommand);
        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD + " 3") instanceof ClearContactCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteContactCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(EditContactCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindContactCommand command = (FindContactCommand) parser.parseCommand(
                FindContactCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindContactCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListContactCommand.COMMAND_WORD) instanceof ListContactCommand);
        assertTrue(parser.parseCommand(ListContactCommand.COMMAND_WORD + " 3") instanceof ListContactCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
