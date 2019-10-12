package seedu.jarvis.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.ExitCommand;
import seedu.jarvis.logic.commands.HelpCommand;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.logic.commands.address.EditAddressCommand;
import seedu.jarvis.logic.commands.address.EditAddressCommand.EditPersonDescriptor;
import seedu.jarvis.logic.commands.address.FindAddressCommand;
import seedu.jarvis.logic.commands.address.ListAddressCommand;
import seedu.jarvis.logic.commands.course.LookUpCommand;
import seedu.jarvis.logic.commands.history.RedoCommand;
import seedu.jarvis.logic.commands.history.UndoCommand;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.person.NameContainsKeywordsPredicate;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.testutil.EditPersonDescriptorBuilder;
import seedu.jarvis.testutil.PersonBuilder;
import seedu.jarvis.testutil.PersonUtil;

public class JarvisParserTest {

    private final JarvisParser parser = new JarvisParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddAddressCommand command = (AddAddressCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddAddressCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearAddressCommand.COMMAND_WORD) instanceof ClearAddressCommand);
        assertTrue(parser.parseCommand(ClearAddressCommand.COMMAND_WORD + " 3") instanceof ClearAddressCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteAddressCommand command = (DeleteAddressCommand) parser.parseCommand(
                DeleteAddressCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteAddressCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditAddressCommand command = (EditAddressCommand) parser.parseCommand(EditAddressCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditAddressCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindAddressCommand command = (FindAddressCommand) parser.parseCommand(
                FindAddressCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindAddressCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListAddressCommand.COMMAND_WORD) instanceof ListAddressCommand);
        assertTrue(parser.parseCommand(ListAddressCommand.COMMAND_WORD + " 3") instanceof ListAddressCommand);
    }

    /**
     * Verifies that parsing undo commands work as intended.
     */
    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5")
                instanceof UndoCommand);
    }

    /**
     * Verifies that parsing redo commands work as intended.
     */
    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5")
                instanceof RedoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_lookUp() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(LookUpCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(LookUpCommand.COMMAND_WORD + " " + PREFIX_COURSE + "cs2102")
                instanceof LookUpCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
