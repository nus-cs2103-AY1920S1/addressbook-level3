package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_EATBOOK;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EATBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_EATBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EATBOOK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EATERY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddFeedCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CloseCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteFeedCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ModeCommand;
import seedu.address.logic.commands.SaveTodoCommand;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.NameContainsKeywordsPredicate;
import seedu.address.model.feed.Feed;
import seedu.address.testutil.EateryBuilder;
import seedu.address.testutil.EateryUtil;
import seedu.address.testutil.EditEateryDescriptorBuilder;
import seedu.address.testutil.FeedBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Eatery eatery = new EateryBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(EateryUtil.getAddCommand(eatery), true);
        assertEquals(new AddCommand(eatery), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, true) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", true) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_EATERY.getOneBased(), true);
        assertEquals(new DeleteCommand(INDEX_FIRST_EATERY), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Eatery eatery = new EateryBuilder().build();
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder(eatery).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EATERY.getOneBased() + " " + EateryUtil.getEditEateryDescriptorDetails(descriptor), true);
        assertEquals(new EditCommand(INDEX_FIRST_EATERY, descriptor), command);
    }

    @Test
    public void parseCommand_close() throws Exception {
        CloseCommand command = (CloseCommand) parser.parseCommand(
                CloseCommand.COMMAND_WORD + " " + INDEX_FIRST_EATERY.getOneBased(), true);
        assertEquals(new CloseCommand(INDEX_FIRST_EATERY), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, true) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", true) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")), true);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, true) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", true) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, true) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", true) instanceof ListCommand);
    }

    @Test
    public void parseCommand_mode() throws Exception {
        assertTrue(parser.parseCommand(ModeCommand.COMMAND_WORD, true) instanceof ModeCommand);
        assertTrue(parser.parseCommand(ModeCommand.COMMAND_WORD + " 3", true) instanceof ModeCommand);
    }

    @Test
    public void parseCommand_saveTodo() throws Exception {
        SaveTodoCommand command = (SaveTodoCommand) parser.parseCommand(
            SaveTodoCommand.COMMAND_WORD + " " + INDEX_FIRST_EATERY.getOneBased(), false);
        assertEquals(new SaveTodoCommand(INDEX_FIRST_EATERY), command);
    }

    @Test
    public void parseCommand_addfeed() throws Exception {
        Feed feed = new FeedBuilder().withName(VALID_NAME_EATBOOK).withAddress(VALID_ADDRESS_EATBOOK).build();
        String input = AddFeedCommand.COMMAND_WORD + NAME_DESC_EATBOOK + ADDRESS_DESC_EATBOOK;

        AddFeedCommand expectedCommand = new AddFeedCommand(feed);
        AddFeedCommand command = (AddFeedCommand) parser.parseCommand(input, true);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_deletefeed() throws Exception {
        DeleteFeedCommand command = (DeleteFeedCommand) parser.parseCommand(
                DeleteFeedCommand.COMMAND_WORD + NAME_DESC_EATBOOK, true);
        assertEquals(new DeleteFeedCommand(VALID_NAME_EATBOOK), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", true));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", true));
    }
}
