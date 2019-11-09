package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static io.xpire.commons.core.Messages.MESSAGE_XPIRE_COMMAND_ONLY;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_DRINK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.CheckCommand;
import io.xpire.logic.commands.ClearCommand;
import io.xpire.logic.commands.DeleteCommand;
import io.xpire.logic.commands.ExitCommand;
import io.xpire.logic.commands.HelpCommand;
import io.xpire.logic.commands.SearchCommand;
import io.xpire.logic.commands.SetReminderCommand;
import io.xpire.logic.commands.SortCommand;
import io.xpire.logic.commands.TagCommand;
import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ContainsKeywordsPredicate;

//@@author febee99

public class ReplenishParserTest {

    private final ReplenishParser parser = new ReplenishParser();

    @Test
    public void parse_clear() throws Exception {
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parse(ClearCommand.COMMAND_WORD + "|3") instanceof ClearCommand);
    }

    @Test
    public void parse_delete() throws Exception {
        //delete item
        DeleteCommand deleteItemCommand = (DeleteCommand) parser.parse(
                DeleteCommand.COMMAND_WORD + "|" + INDEX_FIRST_ITEM.getOneBased());
        assertEquals(new DeleteCommand(XPIRE, INDEX_FIRST_ITEM), deleteItemCommand);
    }

    @Test
    public void parse_exit() throws Exception {
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD + "|3") instanceof ExitCommand);
    }

    @Test
    public void parse_help() throws Exception {
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD + "|3") instanceof HelpCommand);
    }

    @Test
    public void parse_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parse(
                SearchCommand.COMMAND_WORD + "|" + String.join("|", keywords));
        assertEquals(new SearchCommand(XPIRE, new ContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parse_tag() throws Exception {
        assertTrue(parser.parse(TagCommand.COMMAND_WORD) instanceof TagCommand);
        assertTrue(parser.parse(TagCommand.COMMAND_WORD + "|1|#"
                + VALID_TAG_DRINK) instanceof TagCommand);
    }

    @Test
    public void parse_view() throws Exception {
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD) instanceof ViewCommand);
        assertTrue(parser.parse(ViewCommand.COMMAND_WORD + "|replenish") instanceof ViewCommand);
    }

    //---------------- Tests for Forbidden commands --------------------------------------------------------------
    @Test
    public void parse_add_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_XPIRE_COMMAND_ONLY, () ->
            parser.parse(AddCommand.COMMAND_WORD + "|" + VALID_NAME_APPLE + "|" + VALID_EXPIRY_DATE_APPLE));
    }

    @Test
    public void parse_check_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_XPIRE_COMMAND_ONLY, () ->
            parser.parse(CheckCommand.COMMAND_WORD));
        assertThrows(ParseException.class, MESSAGE_XPIRE_COMMAND_ONLY, () ->
            parser.parse(CheckCommand.COMMAND_WORD + "|5"));
    }

    @Test
    public void parse_setReminder_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_XPIRE_COMMAND_ONLY, () ->
            parser.parse(SetReminderCommand.COMMAND_WORD + "|" + INDEX_FIRST_ITEM.getOneBased() + "|10"));
    }

    @Test
    public void parse_sort_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_XPIRE_COMMAND_ONLY, () ->
            parser.parse(SortCommand.COMMAND_WORD + "|name"));
        assertThrows(ParseException.class, MESSAGE_XPIRE_COMMAND_ONLY, () ->
            parser.parse(SortCommand.COMMAND_WORD + "|date"));
    }

    @Test
    public void parse_deleteQuantity_throwsParseException() {
        //delete quantity
        assertThrows(ParseException.class, DeleteCommandParser.MESSAGE_DELETE_QUANTITY_INVALID_USAGE, () ->
            parser.parse(DeleteCommand.COMMAND_WORD + "|" + INDEX_FIRST_ITEM.getOneBased() + "|1"));
    }

    //---------------- Tests for unrecognised input --------------------------------------------------------------
    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parse(""));
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parse("unknownCommand"));
    }

}
