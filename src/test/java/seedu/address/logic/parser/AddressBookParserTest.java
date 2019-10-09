package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.commands.ClearNoteCommand;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindNoteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Note note = new PersonBuilder().build();
        AddNoteCommand command = (AddNoteCommand) parser.parseCommand(PersonUtil.getAddCommand(note));
        assertEquals(new AddNoteCommand(note), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearNoteCommand.COMMAND_WORD) instanceof ClearNoteCommand);
        assertTrue(parser.parseCommand(ClearNoteCommand.COMMAND_WORD + " 3") instanceof ClearNoteCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteNoteCommand command = (DeleteNoteCommand) parser.parseCommand(
                DeleteNoteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteNoteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Note note = new PersonBuilder().build();
        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(note).build();
        EditNoteCommand command = (EditNoteCommand) parser.parseCommand(EditNoteCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditNoteCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindNoteCommand command = (FindNoteCommand) parser.parseCommand(
                FindNoteCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindNoteCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListNoteCommand.COMMAND_WORD) instanceof ListNoteCommand);
        assertTrue(parser.parseCommand(ListNoteCommand.COMMAND_WORD + " 3") instanceof ListNoteCommand);
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
