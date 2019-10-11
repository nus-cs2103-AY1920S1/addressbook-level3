package seedu.mark.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.AddCommand;
import seedu.mark.logic.commands.ClearCommand;
import seedu.mark.logic.commands.DeleteCommand;
import seedu.mark.logic.commands.EditCommand;
import seedu.mark.logic.commands.ExitCommand;
import seedu.mark.logic.commands.ExportCommand;
import seedu.mark.logic.commands.FindCommand;
import seedu.mark.logic.commands.HelpCommand;
import seedu.mark.logic.commands.ImportCommand;
import seedu.mark.logic.commands.ListCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.IdentifiersContainKeywordsPredicate;
import seedu.mark.testutil.BookmarkBuilder;
import seedu.mark.testutil.BookmarkUtil;
import seedu.mark.testutil.EditBookmarkDescriptorBuilder;

public class MarkParserTest {

    private final MarkParser parser = new MarkParser();

    @Test
    public void parseCommand_add() throws Exception {
        Bookmark bookmark = new BookmarkBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(BookmarkUtil.getAddCommand(bookmark));
        assertEquals(new AddCommand(bookmark), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKMARK.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_BOOKMARK), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Bookmark bookmark = new BookmarkBuilder().build();
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder(bookmark).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_BOOKMARK.getOneBased() + " " + BookmarkUtil.getEditBookmarkDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_BOOKMARK, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_export() throws Exception {
        // assumption: bookmarks are exported to the folder data/bookmarks/ (see ExportCommandParser)
        String fileName = "myBookmarks";
        ExportCommand command = (ExportCommand) parser.parseCommand(ExportCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new ExportCommand(Path.of("data", "bookmarks", fileName + ".json")), command);
        // TODO: method to generate export/import filePath
    }

    @Test
    public void parseCommand_import() throws Exception {
        // assumption: bookmarks are imported from the folder data/bookmarks/ (see ImportCommandParser)
        String fileName = "myBookmarks";
        ImportCommand command = (ImportCommand) parser.parseCommand(ImportCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new ImportCommand(Path.of("data", "bookmarks", fileName + ".json")), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new IdentifiersContainKeywordsPredicate(keywords)), command);
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
