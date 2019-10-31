package tagline.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tagline.logic.parser.contact.ContactCommandParser.CONTACT_CLEAR_COMMAND_CONFIRM_STRING;
import static tagline.logic.parser.contact.ContactCommandParser.CONTACT_CLEAR_CONFIRM_CHARACTER;
import static tagline.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.ClearContactCommand;
import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.commands.contact.ListContactCommand;
import tagline.logic.parser.contact.ContactCommandParser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactBuilder;
import tagline.model.contact.ContactId;
import tagline.model.contact.NameContainsKeywordsPredicate;
import tagline.testutil.contact.ContactUtil;
import tagline.testutil.contact.EditContactDescriptorBuilder;

public class ContactCommandParserTest {

    private final ContactCommandParser parser = new ContactCommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        Contact contact = new ContactBuilder().build();
        CreateContactCommand command = (CreateContactCommand) parser.parseCommand(ContactUtil.getAddCommand(contact));
        assertEquals(new CreateContactCommand(contact), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertThrows(PromptRequestException.class, () -> parser.parseCommand(ClearContactCommand.COMMAND_WORD));
        assertThrows(PromptRequestException.class, () -> parser.parseCommand(ClearContactCommand.COMMAND_WORD + " 3"));

        Prompt validFilledPrompt = new Prompt("", CONTACT_CLEAR_COMMAND_CONFIRM_STRING);
        validFilledPrompt.setPromptResponse(CONTACT_CLEAR_CONFIRM_CHARACTER);
        Prompt invalidFilledPrompt = new Prompt("", CONTACT_CLEAR_COMMAND_CONFIRM_STRING);
        invalidFilledPrompt.setPromptResponse("Z");

        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD,
                Arrays.asList(validFilledPrompt)) instanceof ClearContactCommand);
        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD + " 3",
                Arrays.asList(validFilledPrompt)) instanceof ClearContactCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ClearContactCommand.COMMAND_WORD,
                Arrays.asList(invalidFilledPrompt)));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        ContactId contactId = new ContactId(1);
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " " + contactId.toString());
        assertEquals(new DeleteContactCommand(contactId), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Contact contact = new ContactBuilder().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(EditContactCommand.COMMAND_WORD + " "
                + contact.getContactId().toString() + " " + ContactUtil.getEditContactDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(contact.getContactId(), descriptor), command);
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
