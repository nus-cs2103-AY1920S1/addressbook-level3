package seedu.mark.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.mark.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2101;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NEW_FOLDER;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.AddCommand;
import seedu.mark.logic.commands.AddFolderCommand;
import seedu.mark.logic.commands.AutotagCommand;
import seedu.mark.logic.commands.AutotagDeleteCommand;
import seedu.mark.logic.commands.CacheCommand;
import seedu.mark.logic.commands.ClearCommand;
import seedu.mark.logic.commands.CollapseCommand;
import seedu.mark.logic.commands.DeleteCacheCommand;
import seedu.mark.logic.commands.DeleteCommand;
import seedu.mark.logic.commands.DeleteFolderCommand;
import seedu.mark.logic.commands.EditCommand;
import seedu.mark.logic.commands.EditFolderCommand;
import seedu.mark.logic.commands.ExitCommand;
import seedu.mark.logic.commands.ExpandCommand;
import seedu.mark.logic.commands.ExportCommand;
import seedu.mark.logic.commands.FavoriteCommand;
import seedu.mark.logic.commands.FindCommand;
import seedu.mark.logic.commands.GotoCommand;
import seedu.mark.logic.commands.HelpCommand;
import seedu.mark.logic.commands.ImportCommand;
import seedu.mark.logic.commands.ListCommand;
import seedu.mark.logic.commands.RedoCommand;
import seedu.mark.logic.commands.UndoCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.model.predicates.BookmarkContainsKeywordsPredicate;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;
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
    public void parseCommand_addFolder() throws Exception {
        AddFolderCommand command = (AddFolderCommand) parser.parseCommand(
                AddFolderCommand.COMMAND_WORD + " " + VALID_FOLDER_CS2103T);
        assertEquals(new AddFolderCommand(new Folder(VALID_FOLDER_CS2103T), null), command);
    }

    @Test
    public void parseCommand_editFolder() throws Exception {
        EditFolderCommand command = (EditFolderCommand) parser.parseCommand(
                EditFolderCommand.COMMAND_WORD + " " + VALID_FOLDER_CS2103T + " " + PREFIX_NEW_FOLDER
                        + VALID_FOLDER_CS2101);
        assertEquals(new EditFolderCommand(new Folder(VALID_FOLDER_CS2103T), new Folder(VALID_FOLDER_CS2101)), command);
    }

    @Test
    public void parseCommand_deleteFolder() throws Exception {
        DeleteFolderCommand command = (DeleteFolderCommand) parser.parseCommand(
                DeleteFolderCommand.COMMAND_WORD + " " + VALID_FOLDER_CS2103T);
        assertEquals(new DeleteFolderCommand(new Folder(VALID_FOLDER_CS2103T)), command);
    }

    @Test
    public void parseCommand_autotag() throws Exception {
        AutotagCommand command = (AutotagCommand) parser.parseCommand(
                AutotagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + NAME_DESC_AMY);
        AutotagCommand expectedCommand = new AutotagCommand(new SelectiveBookmarkTagger(new Tag(VALID_TAG_FRIEND),
                new BookmarkPredicate().withNameKeywords(Arrays.asList(VALID_NAME_AMY))));
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_autotagDelete() throws Exception {
        AutotagDeleteCommand command = (AutotagDeleteCommand) parser.parseCommand(
                AutotagDeleteCommand.COMMAND_WORD + " myTag");
        AutotagDeleteCommand expectedCommand = new AutotagDeleteCommand("myTag");
        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_cache() throws Exception {
        assertTrue(parser.parseCommand(CacheCommand.COMMAND_WORD + " 3") instanceof CacheCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ClearCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_collapse() throws Exception {
        assertTrue(parser.parseCommand(CollapseCommand.COMMAND_WORD) instanceof CollapseCommand);
        assertTrue(parser.parseCommand(CollapseCommand.COMMAND_WORD + " 3") instanceof CollapseCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKMARK.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_BOOKMARK), command);
    }

    @Test
    public void parseCommand_deleteCache() throws Exception {
        assertTrue(parser.parseCommand(DeleteCacheCommand.COMMAND_WORD + " 3") instanceof DeleteCacheCommand);
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
        assertThrows(ParseException.class, () -> parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_expand() throws Exception {
        assertTrue(parser.parseCommand(ExpandCommand.COMMAND_WORD) instanceof ExpandCommand);
        assertTrue(parser.parseCommand(ExpandCommand.COMMAND_WORD + " 3") instanceof ExpandCommand);
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
    public void parseCommand_favorite() throws Exception {
        FavoriteCommand command = (FavoriteCommand) parser.parseCommand(
                FavoriteCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKMARK.getOneBased());
        assertEquals(new FavoriteCommand(INDEX_FIRST_BOOKMARK), command);
    }

    @Test
    public void parseCommand_favoriteAlias() throws Exception {
        FavoriteCommand command = (FavoriteCommand) parser.parseCommand(
                FavoriteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_BOOKMARK.getOneBased());
        assertEquals(new FavoriteCommand(INDEX_FIRST_BOOKMARK), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(
                keywords, Collections.emptyList(), Collections.emptyList());
        assertEquals(new FindCommand(predicate), command);
    }

    @Test
    public void parseCommand_goto() throws Exception {
        GotoCommand command = (GotoCommand) parser.parseCommand(
                GotoCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKMARK.getOneBased());
        assertEquals(new GotoCommand(INDEX_FIRST_BOOKMARK), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_import() throws Exception {
        // assumption: bookmarks are imported from the folder data/bookmarks/ (see ImportCommandParser)
        String fileName = "myBookmarks";
        ImportCommand command = (ImportCommand) parser.parseCommand(ImportCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new ImportCommand(Path.of("data", "bookmarks", fileName + ".json")), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
