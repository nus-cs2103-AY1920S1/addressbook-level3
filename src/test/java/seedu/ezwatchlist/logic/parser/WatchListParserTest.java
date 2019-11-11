package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.logic.commands.ClearCommand;
import seedu.ezwatchlist.logic.commands.DeleteCommand;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.logic.commands.ExitCommand;
import seedu.ezwatchlist.logic.commands.GoToCommand;
import seedu.ezwatchlist.logic.commands.HelpCommand;
import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.commands.SyncCommand;
import seedu.ezwatchlist.logic.commands.WatchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;
import seedu.ezwatchlist.testutil.ShowBuilder;
import seedu.ezwatchlist.testutil.ShowUtil;
import seedu.ezwatchlist.testutil.TypicalShows;
import seedu.ezwatchlist.testutil.WatchShowDescriptorBuilder;

public class WatchListParserTest {

    private final WatchListParser parser = new WatchListParser();
    private final String currentTab = "watchlist";

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, currentTab) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", currentTab) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_SHOW.getOneBased(), currentTab);
        assertEquals(new DeleteCommand(INDEX_FIRST_SHOW), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Show show = new ShowBuilder().build();
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder(show).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SHOW.getOneBased() + " " + ShowUtil.getEditShowDescriptorDetails(descriptor), currentTab);
        //assertEquals(new EditCommand(INDEX_FIRST_SHOW, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, currentTab) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", currentTab) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_watch() throws Exception {
        Show show = TypicalShows.THEOFFICE;
        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(show).build();
        WatchCommand command = (WatchCommand) parser.parseCommand(WatchCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SHOW.getOneBased() + " e/3", currentTab);
        assertEquals(
                new WatchCommand(INDEX_FIRST_SHOW, descriptor, false, true), command);
    }

    @Test
    public void parseCommand_search() throws Exception {
        /*List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new NameContainsKeywordsPredicate(keywords)), command);*/
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, currentTab) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", currentTab) instanceof HelpCommand);
    }
    /*
    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, currentTab) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", currentTab) instanceof ListCommand);
    }
    */
    @Test
    public void parseCommand_watchCommand() throws Exception {
        assertTrue(parser.parseCommand(WatchCommand.COMMAND_WORD + " 3", currentTab) instanceof WatchCommand);
    }
    @Test
    public void parseCommand_searchCommand() throws Exception {
        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " n/Potter", currentTab) instanceof SearchCommand);
    }
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", currentTab));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", currentTab));
    }
    @Test
    public void parseCommand_syncCommand() throws Exception {
        assertTrue(parser.parseCommand(SyncCommand.COMMAND_WORD + " 3", currentTab) instanceof SyncCommand);
    }
    @Test
    public void parseCommand_shortCutKey() throws Exception {
        assertTrue(parser.parseCommand("1", currentTab) instanceof GoToCommand);
    }
    @Test
    public void parseCommand_addCommand() throws Exception {
        assertTrue(parser.parseCommand(AddCommand.COMMAND_WORD + " n/Cars t/movie", currentTab) instanceof AddCommand);
    }
}
