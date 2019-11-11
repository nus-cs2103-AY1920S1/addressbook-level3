package seedu.weme.logic.parser.contextparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeArchivesCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeUnarchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeViewCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.ModelContext;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;
import seedu.weme.testutil.EditMemeDescriptorBuilder;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.MemeUtil;

public class MemeParserTest extends ApplicationTest {

    private final WemeParser parser = new MemeParser();

    @Test
    public void parseCommand_add() throws Exception {
        Meme meme = new MemeBuilder().build();
        MemeAddCommand command = (MemeAddCommand) parser.parseCommand(MemeUtil.getAddCommand(meme));
        assertEquals(new MemeAddCommand(meme), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(MemeClearCommand.COMMAND_WORD) instanceof MemeClearCommand);
        assertTrue(parser.parseCommand(MemeClearCommand.COMMAND_WORD + " 3") instanceof MemeClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        MemeDeleteCommand command = (MemeDeleteCommand) parser.parseCommand(
                MemeDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new MemeDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Meme meme = new MemeBuilder().build();
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder(meme).build();
        descriptor.setFilePath(null);
        MemeEditCommand command = (MemeEditCommand) parser.parseCommand(MemeEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + MemeUtil.getEditMemeDescriptorDetails(descriptor));
        assertEquals(new MemeEditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        MemeFindCommand command = (MemeFindCommand) parser.parseCommand(
                MemeFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new MemeFindCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(MemeListCommand.COMMAND_WORD) instanceof MemeListCommand);
        assertTrue(parser.parseCommand(MemeListCommand.COMMAND_WORD + " 3") instanceof MemeListCommand);
    }

    @Test
    public void parseCommand_tab() throws Exception {
        Command command = parser.parseCommand(
            TabCommand.COMMAND_WORD + " " + ModelContext.CONTEXT_MEMES.getContextName());
        assertTrue(command instanceof TabCommand);
        assertEquals(new TabCommand(ModelContext.CONTEXT_MEMES), command);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        MemeArchiveCommand command = (MemeArchiveCommand) parser.parseCommand(
                MemeArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new MemeArchiveCommand(INDEX_FIRST), command);

    }
    @Test
    public void parseCommand_unarchive() throws Exception {
        MemeUnarchiveCommand command = (MemeUnarchiveCommand) parser.parseCommand(
                MemeUnarchiveCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new MemeUnarchiveCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_archives() throws Exception {
        assertTrue(parser.parseCommand(MemeArchivesCommand.COMMAND_WORD) instanceof MemeArchivesCommand);
        assertTrue(parser.parseCommand(MemeArchivesCommand.COMMAND_WORD + " 3") instanceof MemeArchivesCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        MemeViewCommand command = (MemeViewCommand) parser.parseCommand(
                MemeViewCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new MemeViewCommand(INDEX_FIRST), command);
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
