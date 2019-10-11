package seedu.weme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.AddCommand;
import seedu.weme.logic.commands.ClearCommand;
import seedu.weme.logic.commands.DeleteCommand;
import seedu.weme.logic.commands.EditCommand;
import seedu.weme.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.weme.logic.commands.ExitCommand;
import seedu.weme.logic.commands.FindCommand;
import seedu.weme.logic.commands.HelpCommand;
import seedu.weme.logic.commands.ListCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;
import seedu.weme.testutil.EditMemeDescriptorBuilder;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.MemeUtil;

public class MemeBookParserTest {

    private final MemeBookParser parser = new MemeBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Meme meme = new MemeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(MemeUtil.getAddCommand(meme));
        assertEquals(new AddCommand(meme), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_MEME.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_MEME), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Meme meme = new MemeBuilder().build();
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder(meme).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_MEME.getOneBased() + " " + MemeUtil.getEditMemeDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_MEME, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), command);
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
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
